#!/usr/bin/env bash
# ==============================================================================
# k8s-single-node-setup.sh
# Ubuntu 22.04 / 24.04 기반 단일 노드 Kubernetes 설치 스크립트
# (VMware VM 환경 기준)
#
# 포함 구성 요소:
#   - containerd (컨테이너 런타임)
#   - kubeadm / kubelet / kubectl (Kubernetes 1.29)
#   - Flannel (Pod 네트워크 CNI)
#   - MetalLB (LoadBalancer IP 할당)
#   - NGINX Ingress Controller
#
# 사용법:
#   chmod +x k8s-single-node-setup.sh
#   sudo ./k8s-single-node-setup.sh
# ==============================================================================

set -euo pipefail

# --- 색상 출력 헬퍼 ---
info()  { echo -e "\e[32m[INFO]\e[0m  $*"; }
warn()  { echo -e "\e[33m[WARN]\e[0m  $*"; }
error() { echo -e "\e[31m[ERROR]\e[0m $*" >&2; exit 1; }

# --- 루트 확인 ---
[[ $EUID -ne 0 ]] && error "이 스크립트는 root(sudo) 권한이 필요합니다."

# ==============================================================================
# 0. 변수 설정
# ==============================================================================
K8S_VERSION="1.29"
POD_CIDR="10.244.0.0/16"          # Flannel 기본값
METALLB_VERSION="v0.14.5"
INGRESS_NGINX_VERSION="v1.10.1"

# MetalLB 가 사용할 IP 풀 범위 (VM 의 실제 네트워크 대역에 맞게 수정)
# 예) VMnet8(NAT) 192.168.253.0/24 → 200~220 을 LoadBalancer 용으로 예약
METALLB_IP_RANGE="192.168.253.200-192.168.253.220"

# ==============================================================================
# 1. 시스템 기본 설정
# ==============================================================================
info "1/8 시스템 기본 설정 시작"

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

# ==============================================================================
# 2. containerd 설치
# ==============================================================================
info "2/8 containerd 설치"

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

# ==============================================================================
# 3. kubeadm / kubelet / kubectl 설치
# ==============================================================================
info "3/8 kubeadm / kubelet / kubectl 설치 (v${K8S_VERSION})"

curl -fsSL "https://pkgs.k8s.io/core:/stable:/v${K8S_VERSION}/deb/Release.key" \
  | gpg --dearmor -o /etc/apt/keyrings/kubernetes-apt-keyring.gpg

echo "deb [signed-by=/etc/apt/keyrings/kubernetes-apt-keyring.gpg] \
https://pkgs.k8s.io/core:/stable:/v${K8S_VERSION}/deb/ /" \
  | tee /etc/apt/sources.list.d/kubernetes.list

apt-get update -y
apt-get install -y kubelet kubeadm kubectl
apt-mark hold kubelet kubeadm kubectl
systemctl enable kubelet

# ==============================================================================
# 4. 단일 노드 클러스터 초기화
# ==============================================================================
info "4/8 kubeadm init (Pod CIDR: ${POD_CIDR})"

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

# ==============================================================================
# 5. Flannel CNI 설치
# ==============================================================================
info "5/8 Flannel CNI 설치"

kubectl apply -f \
  https://github.com/flannel-io/flannel/releases/latest/download/kube-flannel.yml

info "  Flannel DaemonSet 준비 대기 중 (최대 120초)..."
kubectl -n kube-flannel rollout status daemonset/kube-flannel-ds --timeout=120s

# ==============================================================================
# 6. MetalLB 설치
# ==============================================================================
info "6/8 MetalLB ${METALLB_VERSION} 설치"

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

# ==============================================================================
# 7. NGINX Ingress Controller 설치
# ==============================================================================
info "7/8 NGINX Ingress Controller ${INGRESS_NGINX_VERSION} 설치"

kubectl apply -f \
  "https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-${INGRESS_NGINX_VERSION}/deploy/static/provider/cloud/deploy.yaml"

info "  NGINX Ingress Controller 준비 대기 중 (최대 120초)..."
kubectl -n ingress-nginx rollout status deployment/ingress-nginx-controller --timeout=120s

# ==============================================================================
# 8. 설치 결과 출력
# ==============================================================================
info "8/8 설치 완료 — 클러스터 상태 확인"
echo ""
kubectl get nodes -o wide
echo ""
kubectl get svc -n ingress-nginx
echo ""

LB_IP=$(kubectl get svc ingress-nginx-controller -n ingress-nginx \
  -o jsonpath='{.status.loadBalancer.ingress[0].ip}' 2>/dev/null || echo "pending")

cat <<EOF

==============================================================================
  ✅ 단일 노드 Kubernetes 클러스터 설치 완료
==============================================================================

  노드 IP         : ${NODE_IP}
  Pod CIDR        : ${POD_CIDR}
  MetalLB IP 풀   : ${METALLB_IP_RANGE}
  Ingress LB IP   : ${LB_IP}

  Windows hosts 파일 예시 (C:\Windows\System32\drivers\etc\hosts):
  ---------------------------------------------------------------
  ${LB_IP}  web.test.com
  ${LB_IP}  jup.test.com
  ${LB_IP}  harbor.test.com
  ${LB_IP}  nexus.test.com
  ---------------------------------------------------------------

  kubectl 사용 예시:
    kubectl get nodes
    kubectl get pods -A
    kubectl get svc -A

EOF
