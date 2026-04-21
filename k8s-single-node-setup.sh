#!/usr/bin/env bash
# ==============================================================================
# k8s-single-node-setup.sh
# Ubuntu 22.04 / 24.04 기반 단일 노드 Kubernetes 설치 스크립트
# (VMware VM 환경 기준)
#
# 포함 구성 요소:
#   - containerd          (컨테이너 런타임)
#   - kubeadm / kubelet / kubectl  (Kubernetes 1.29)
#   - Calico CNI          (Pod 네트워크, Flannel 대체)
#   - Metrics Server      (CPU/메모리 사용량 수집)
#   - Headlamp            (최신 Kubernetes 웹 대시보드)
#   - MetalLB             (LoadBalancer IP 할당)
#   - NGINX Ingress Controller
#
# 사용법:
#   chmod +x k8s-single-node-setup.sh
#   sudo ./k8s-single-node-setup.sh
#
# 설치 단계 요약:
#    1/10  시스템 기본 설정   (swap 비활성화, 커널 모듈, sysctl)
#    2/10  containerd 설치
#    3/10  kubeadm / kubelet / kubectl 설치
#    4/10  kubeadm init  (단일 노드 클러스터 초기화)
#    5/10  Calico CNI 설치
#    6/10  Metrics Server 설치
#    7/10  Headlamp 대시보드 설치
#    8/10  MetalLB 설치 및 IPAddressPool 구성
#    9/10  NGINX Ingress Controller 설치
#   10/10  설치 결과 출력
# ==============================================================================

set -euo pipefail

# --- 색상 출력 헬퍼 ---
info()  { echo -e "\e[32m[INFO]\e[0m  $*"; }
warn()  { echo -e "\e[33m[WARN]\e[0m  $*"; }
error() { echo -e "\e[31m[ERROR]\e[0m $*" >&2; exit 1; }
step()  { echo -e "\e[36m[STEP]\e[0m  $*"; }

# --- 루트 확인 ---
[[ $EUID -ne 0 ]] && error "이 스크립트는 root(sudo) 권한이 필요합니다."

# ==============================================================================
# 0. 변수 설정
# ==============================================================================
K8S_VERSION="1.29"

# Pod 네트워크 CIDR — Calico 에서도 임의 CIDR 사용 가능
# VMware NAT 대역(192.168.x.x)과 충돌하지 않도록 10.244.0.0/16 유지
POD_CIDR="10.244.0.0/16"

CALICO_VERSION="v3.28.0"           # Calico (Tigera Operator 방식)
METALLB_VERSION="v0.14.5"
INGRESS_NGINX_VERSION="v1.10.1"
HEADLAMP_VERSION="0.24.1"          # Helm Chart 버전

# MetalLB 가 사용할 IP 풀 범위 (VM 의 실제 네트워크 대역에 맞게 수정)
# 예) VMnet8(NAT) 192.168.253.0/24 → 200~220 을 LoadBalancer 용으로 예약
METALLB_IP_RANGE="192.168.253.200-192.168.253.220"

# ==============================================================================
# 1. 시스템 기본 설정
# ==============================================================================
step "1/10 시스템 기본 설정 시작"
info "  ▶ swap 비활성화, 커널 모듈 로드, sysctl 파라미터 설정"

# swap 비활성화 (Kubernetes 요구 사항)
swapoff -a
sed -i '/\bswap\b/s/^/#/' /etc/fstab

# 커널 모듈 로드
cat <<EOF | tee /etc/modules-load.d/k8s.conf
overlay
br_netfilter
EOF
modprobe overlay
modprobe br_netfilter

# sysctl 설정
cat <<EOF | tee /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-iptables  = 1
net.bridge.bridge-nf-call-ip6tables = 1
net.ipv4.ip_forward                 = 1
EOF
sysctl --system
info "  ✔ 시스템 기본 설정 완료"

# ==============================================================================
# 2. containerd 설치
# ==============================================================================
step "2/10 containerd 설치"
info "  ▶ Docker 공식 apt 저장소에서 containerd.io 설치"

apt-get update -y
apt-get install -y ca-certificates curl gnupg lsb-release apt-transport-https

install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg \
  | gpg --dearmor -o /etc/apt/keyrings/docker.gpg
chmod a+r /etc/apt/keyrings/docker.gpg

echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" \
  | tee /etc/apt/sources.list.d/docker.list > /dev/null

apt-get update -y
apt-get install -y containerd.io

# containerd 기본 설정 생성 및 SystemdCgroup 활성화
containerd config default | tee /etc/containerd/config.toml > /dev/null
sed -i 's/SystemdCgroup = false/SystemdCgroup = true/' /etc/containerd/config.toml
systemctl restart containerd
systemctl enable containerd
info "  ✔ containerd 설치 및 설정 완료 (SystemdCgroup=true)"

# ==============================================================================
# 3. kubeadm / kubelet / kubectl 설치
# ==============================================================================
step "3/10 kubeadm / kubelet / kubectl 설치 (v${K8S_VERSION})"
info "  ▶ pkgs.k8s.io 공식 저장소 등록 후 패키지 설치"

curl -fsSL "https://pkgs.k8s.io/core:/stable:/v${K8S_VERSION}/deb/Release.key" \
  | gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg

echo "deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] \
https://pkgs.k8s.io/core:/stable:/v${K8S_VERSION}/deb/ /" \
  | tee /etc/apt/sources.list.d/kubernetes.list

apt-get update -y
apt-get install -y kubelet kubeadm kubectl
apt-mark hold kubelet kubeadm kubectl
systemctl enable kubelet
info "  ✔ kubeadm / kubelet / kubectl 설치 완료 (버전 고정)"

# ==============================================================================
# 4. 단일 노드 클러스터 초기화
# ==============================================================================
step "4/10 kubeadm init (Pod CIDR: ${POD_CIDR})"
info "  ▶ VM 의 기본 NIC IP 자동 감지 후 컨트롤 플레인 초기화"

# VM 의 기본 NIC IP 자동 감지
NODE_IP=$(ip -4 route get 8.8.8.8 2>/dev/null | grep -oP 'src \K\S+' || hostname -I | awk '{print $1}')
info "  감지된 노드 IP: ${NODE_IP}"

kubeadm init \
  --pod-network-cidr="${POD_CIDR}" \
  --apiserver-advertise-address="${NODE_IP}" \
  --ignore-preflight-errors=NumCPU,Mem

# kubectl 설정 (현재 사용자 및 root)
mkdir -p "$HOME/.kube"
cp /etc/kubernetes/admin.conf "$HOME/.kube/config"
chown "$(id -u):$(id -g)" "$HOME/.kube/config"

# 일반 사용자용 설정 (sudo 실행 원본 사용자)
if [[ -n "${SUDO_USER:-}" ]]; then
  USER_HOME=$(getent passwd "$SUDO_USER" | cut -d: -f6)
  mkdir -p "${USER_HOME}/.kube"
  cp /etc/kubernetes/admin.conf "${USER_HOME}/.kube/config"
  chown "${SUDO_USER}:${SUDO_USER}" "${USER_HOME}/.kube/config"
fi

# 단일 노드에서 Pod 스케줄링 허용 (control-plane taint 제거)
export KUBECONFIG=/etc/kubernetes/admin.conf
if kubectl get nodes -o jsonpath='{.items[*].spec.taints[*].key}' | grep -q 'node-role.kubernetes.io/control-plane'; then
  kubectl taint nodes --all node-role.kubernetes.io/control-plane-
else
  info "  control-plane taint 없음 — 스킵"
fi
info "  ✔ 클러스터 초기화 완료 (단일 노드 스케줄링 활성화)"

# ==============================================================================
# 5. Calico CNI 설치 (Tigera Operator 방식)
# ==============================================================================
step "5/10 Calico CNI ${CALICO_VERSION} 설치"
info "  ▶ Flannel 대체 — Tigera Operator를 통한 Calico 설치"
info "  ▶ Pod CIDR: ${POD_CIDR} (VXLANCrossSubnet 캡슐화)"

# Tigera Operator 배포 (CRD 및 Operator Deployment 포함)
kubectl create -f \
  "https://raw.githubusercontent.com/projectcalico/calico/${CALICO_VERSION}/manifests/tigera-operator.yaml"

info "  Tigera Operator CRD 준비 대기 중 (15초)..."
sleep 15

# Installation CR — POD_CIDR 을 그대로 사용하도록 ipPool 지정
cat <<EOF | kubectl apply -f -
apiVersion: operator.tigera.io/v1
kind: Installation
metadata:
  name: default
spec:
  # Calico 네트워크 설정
  calicoNetwork:
    ipPools:
    - blockSize: 26
      cidr: ${POD_CIDR}
      encapsulation: VXLANCrossSubnet   # 서브넷 내: 캡슐화 없음, 서브넷 간: VXLAN
      natOutgoing: Enabled
      nodeSelector: all()
---
apiVersion: operator.tigera.io/v1
kind: APIServer
metadata:
  name: default
spec: {}
EOF

info "  Calico 노드 DaemonSet 준비 대기 중 (최대 180초)..."
kubectl -n calico-system rollout status daemonset/calico-node --timeout=180s
info "  ✔ Calico CNI 설치 완료"

# ==============================================================================
# 6. Metrics Server 설치
# ==============================================================================
step "6/10 Metrics Server 설치"
info "  ▶ kubectl top node/pod 명령 및 HPA(Horizontal Pod Autoscaler) 지원"

kubectl apply -f \
  https://github.com/kubernetes-sigs/metrics-server/releases/latest/download/components.yaml

# 단일 노드 / 자체 서명 인증서 환경: --kubelet-insecure-tls 패치 필요
info "  단일 노드용 kubelet-insecure-tls 패치 적용..."
kubectl patch deployment metrics-server -n kube-system \
  --type='json' \
  -p='[{
    "op": "add",
    "path": "/spec/template/spec/containers/0/args/-",
    "value": "--kubelet-insecure-tls"
  }]'

info "  Metrics Server 준비 대기 중 (최대 90초)..."
kubectl -n kube-system rollout status deployment/metrics-server --timeout=90s
info "  ✔ Metrics Server 설치 완료"

# ==============================================================================
# 7. Headlamp 대시보드 설치 (Helm)
# ==============================================================================
step "7/10 Headlamp 대시보드 v${HEADLAMP_VERSION} 설치"
info "  ▶ CNCF 프로젝트 — 최신 Kubernetes 웹 UI (Helm Chart)"

# Helm 미설치 시 자동 설치
if ! command -v helm &>/dev/null; then
  info "  Helm 미감지 — 공식 스크립트로 설치 진행..."
  curl -fsSL https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
fi

helm repo add headlamp https://headlamp-k8s.github.io/headlamp/ 2>/dev/null || true
helm repo update

helm upgrade --install headlamp headlamp/headlamp \
  --namespace headlamp \
  --create-namespace \
  --version "${HEADLAMP_VERSION}" \
  --set service.type=LoadBalancer \
  --wait --timeout=120s

HEADLAMP_IP=$(kubectl get svc headlamp -n headlamp \
  -o jsonpath='{.status.loadBalancer.ingress[0].ip}' 2>/dev/null || echo "pending")
info "  ✔ Headlamp 설치 완료  →  http://${HEADLAMP_IP}  (LoadBalancer IP)"

# ==============================================================================
# 8. MetalLB 설치
# ==============================================================================
step "8/10 MetalLB ${METALLB_VERSION} 설치"
info "  ▶ 베어메탈/VM 환경용 LoadBalancer IP 할당기"
info "  ▶ IP 풀 범위: ${METALLB_IP_RANGE}"

kubectl apply -f \
  "https://raw.githubusercontent.com/metallb/metallb/${METALLB_VERSION}/config/manifests/metallb-native.yaml"

info "  MetalLB 컨트롤러 준비 대기 중 (최대 120초)..."
kubectl -n metallb-system rollout status deployment/controller --timeout=120s

# IPAddressPool 및 L2Advertisement 생성
cat <<EOF | kubectl apply -f -
apiVersion: metallb.io/v1beta1
kind: IPAddressPool
metadata:
  name: default-pool
  namespace: metallb-system
spec:
  addresses:
  - ${METALLB_IP_RANGE}
---
apiVersion: metallb.io/v1beta1
kind: L2Advertisement
metadata:
  name: default-l2advert
  namespace: metallb-system
EOF
info "  ✔ MetalLB 설치 완료 (IPAddressPool: ${METALLB_IP_RANGE})"

# ==============================================================================
# 9. NGINX Ingress Controller 설치
# ==============================================================================
step "9/10 NGINX Ingress Controller ${INGRESS_NGINX_VERSION} 설치"
info "  ▶ 도메인 기반 HTTP/HTTPS 라우팅 (가상 호스트)"

kubectl apply -f \
  "https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-${INGRESS_NGINX_VERSION}/deploy/static/provider/cloud/deploy.yaml"

info "  NGINX Ingress Controller 준비 대기 중 (최대 120초)..."
kubectl -n ingress-nginx rollout status deployment/ingress-nginx-controller --timeout=120s
info "  ✔ NGINX Ingress Controller 설치 완료"

# ==============================================================================
# 10. 설치 결과 출력
# ==============================================================================
step "10/10 설치 완료 — 클러스터 상태 확인"
echo ""
kubectl get nodes -o wide
echo ""
echo "--- 전체 Pod 상태 ---"
kubectl get pods -A
echo ""
echo "--- Ingress NGINX 서비스 ---"
kubectl get svc -n ingress-nginx
echo ""
echo "--- Metrics Server ---"
kubectl get svc -n kube-system | grep metrics-server || true
echo ""
echo "--- Headlamp 대시보드 ---"
kubectl get svc -n headlamp || true
echo ""

LB_IP=$(kubectl get svc ingress-nginx-controller -n ingress-nginx \
  -o jsonpath='{.status.loadBalancer.ingress[0].ip}' 2>/dev/null || echo "pending")
HEADLAMP_IP=$(kubectl get svc headlamp -n headlamp \
  -o jsonpath='{.status.loadBalancer.ingress[0].ip}' 2>/dev/null || echo "pending")

cat <<EOF

==============================================================================
  ✅ 단일 노드 Kubernetes 클러스터 설치 완료
==============================================================================

  노드 IP           : ${NODE_IP}
  Pod CIDR          : ${POD_CIDR}  (Calico CNI)
  MetalLB IP 풀     : ${METALLB_IP_RANGE}
  Ingress LB IP     : ${LB_IP}
  Headlamp 대시보드 : http://${HEADLAMP_IP}

  ── Headlamp 접속 토큰 생성 방법 ─────────────────────────────────────────
  kubectl create serviceaccount headlamp-admin -n headlamp
  kubectl create clusterrolebinding headlamp-admin \\
    --clusterrole=cluster-admin \\
    --serviceaccount=headlamp:headlamp-admin
  kubectl create token headlamp-admin -n headlamp

  ── 리소스 사용량 확인 (Metrics Server) ─────────────────────────────────
  kubectl top nodes
  kubectl top pods -A

  ── Windows hosts 파일 예시 (C:\\Windows\\System32\\drivers\\etc\\hosts) ──
  ${LB_IP}  web.test.com
  ${LB_IP}  jup.test.com
  ${LB_IP}  harbor.test.com
  ${LB_IP}  nexus.test.com
  ${LB_IP}  gitlab.test.com
  ─────────────────────────────────────────────────────────────────────────

  kubectl 사용 예시:
    kubectl get nodes
    kubectl get pods -A
    kubectl get svc -A

==============================================================================
EOF
