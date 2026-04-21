# 개발환경 기본 설정

---

## 1) WSL 환경 구축 (Windows 사용자)

### 설치 목적
- Windows에서도 Linux 기반 개발 환경을 사용하기 위함
- Docker, Python, Node.js, Java CLI 도구를 일관된 방식으로 사용 가능

### 설치 순서
1. 관리자 권한 PowerShell 실행
2. 아래 명령어 실행

```powershell
wsl --install
```

3. 재부팅 후 Ubuntu 배포판 계정 생성
4. WSL 버전 확인

```powershell
wsl --status
wsl -l -v
```

### 공식 사이트
- https://learn.microsoft.com/windows/wsl/install

---

## 2) Python 다운로드 및 환경 구축

### 설치 순서 (Windows)
1. 공식 다운로드 페이지 접속
2. 최신 Python 3 설치 파일 다운로드
3. 설치 시 **Add Python to PATH** 체크 후 설치
4. 버전 확인

```bash
python --version
pip --version
```

### 동작 확인 (Hello World)
```bash
python -c "print('Hello, World!')"
```

예상 출력:
```
Hello, World!
```

### 권장 가상환경 생성
```bash
python -m venv .venv
# PowerShell
.\.venv\Scripts\Activate.ps1
# macOS/Linux
source .venv/bin/activate
```

### 공식 사이트
- https://www.python.org/downloads/

---

## 3) Node.js 다운로드 및 환경 구축

### 설치 순서
1. 공식 페이지에서 LTS 버전 다운로드
2. 기본 옵션으로 설치
3. 버전 확인

```bash
node --version
npm --version
```

### 동작 확인 (Hello World)
```bash
node -e "console.log('Hello, World!')"
```

예상 출력:
```
Hello, World!
```

### npm 초기 설정 예시
```bash
npm config set init-author-name "your-name"
npm config set init-license "MIT"
```

### 공식 사이트
- https://nodejs.org/en/download

---

## 4) Docker 환경 구축

### 설치 순서 (Docker Desktop 기준)
1. Docker Desktop 다운로드 및 설치
2. 설치 중 WSL2 연동 옵션 활성화
3. 설치 후 Docker Desktop 실행
4. 버전 확인

```bash
docker --version
docker compose version
```

### 동작 확인
```bash
docker run hello-world
```

### 공식 사이트
- https://www.docker.com/products/docker-desktop/

---

## 5) Java (OpenJDK) 설치 및 환경 변수 설정 (Windows)

### 설치 목적
- Java 애플리케이션 빌드·실행을 위한 JDK 설치
- `JAVA_HOME` 환경 변수 등록으로 Maven, IDE 등 다른 도구에서 JDK를 자동 인식

### 5-1) jdk.java.net 접속 및 다운로드

1. 브라우저에서 아래 주소 접속
   - https://jdk.java.net/17/
2. **RI Binary** 섹션의 **Windows 11 x64 Java Development Kit** 링크 클릭하여 `.zip` 파일 다운로드

### 공식 사이트
- https://jdk.java.net/ (OpenJDK Reference Implementation)

![jdk.java.net Java SE 17 다운로드 페이지](images/image.png)

---

### 5-2) zip 파일 압축 해제

1. 다운로드된 OpenJDK 17 zip 파일(예: `openjdk-17.x.x_windows-x64_bin.zip`)을 압축 해제 도구로 열기
2. 내부 `jdk-17.x.x` 폴더 확인 (bin, conf, include, jmods, legal, lib, release 포함)

![OpenJDK zip 압축 해제](images/image-1.png)

---

### 5-3) JDK 폴더를 C:\jdk17 로 이동

1. 압축 해제된 JDK 폴더를 `C:\jdk17` 이름으로 이동 또는 복사
2. Windows 탐색기에서 `C:\jdk17` 폴더가 생성된 것 확인

![C:\jdk17 폴더 생성 확인](images/image-2.png)

---

### 5-4) 시스템 > 정보 > 고급 시스템 설정 열기

1. **Windows 설정** → **시스템** → **정보** 이동
2. 우측 하단 **고급 시스템 설정** 링크 클릭

![Windows 시스템 정보 화면](images/image-3.png)

---

### 5-5) 환경 변수(JAVA_HOME) 설정

1. **시스템 속성** 창 → **고급** 탭 → **환경 변수(N)...** 버튼 클릭
2. **시스템 변수** 영역에서 **새로 만들기(W)...** 클릭
   - 변수 이름: `JAVA_HOME`
   - 변수 값: `C:\jdk17`
3. 기존 `Path` 시스템 변수를 선택 → **편집(I)...** 클릭 → `%JAVA_HOME%\bin` 추가
4. **확인** 클릭하여 저장

![환경 변수 설정 창 - 사용자 변수 JAVA_HOME](images/image-4.png)

---

### 5-6) 사용자 변수에서 JAVA_HOME 확인

환경 변수 창에서 **사용자 변수(U)** 목록에 `JAVA_HOME = C:\jdk17` (또는 설정한 경로)가 등록되었는지 확인합니다.

![사용자 변수 JAVA_HOME 확인](images/image-5.png)

---

### 5-7) 시스템 변수에서 JAVA_HOME 확인

**시스템 변수(S)** 목록에도 `JAVA_HOME` 이 등록되어 있는지 확인합니다.

![시스템 변수 JAVA_HOME 확인](images/image-6.png)

---

### 5-8) 설치 확인

새 PowerShell 또는 명령 프롬프트를 열어 아래 명령어로 버전을 확인합니다.

```bash
java --version
javac --version
```

---

## 6) Git for Windows 설치

### 설치 목적
- 소스 코드 버전 관리 및 GitHub 연동

### 설치 순서
1. 아래 공식 사이트에서 최신 버전 다운로드
   - https://git-scm.com/download/win
2. **Click here to download the latest x64 version** 링크 클릭

![Git for Windows 다운로드 페이지](images/image-7.png)

3. 다운로드된 `.exe` 파일 실행 → UAC(사용자 계정 컨트롤) 허용 클릭

![Git 설치 UAC 허용](images/image-8.png)

4. 설치 마법사 기본 옵션 유지하며 진행
5. 설치 완료 후 버전 확인

```bash
git --version
```

---

## 7) Visual Studio Code (VS Code) 설치

### 설치 목적
- Java, Python, Node.js 등 다양한 언어를 지원하는 경량 IDE

### 설치 순서
1. 아래 공식 사이트 접속
   - https://code.visualstudio.com/download
2. 운영체제에 맞는 버전 선택 (Windows: **User Installer** 권장)

![VS Code 다운로드 페이지](images/image-9.png)

3. 다운로드된 설치 파일 실행 → 기본 옵션으로 설치
4. VS Code 실행 후 Java Extension Pack 설치 권장

### Java 프로젝트 실행 예시

VS Code 탐색기(Explorer) 패널에서 Java 소스 파일 구조를 확인하고 실행할 수 있습니다.

![VS Code Java 프로젝트 탐색기](images/image-10.png)

---

## 9) VirtualBox 설치 및 VM 네트워크 설정 (Windows)

### 설치 목적
- Windows PC에서 가상 머신(VM)을 실행하여 Linux 등 다양한 OS를 실습
- NAT 및 호스트 전용 어댑터(Host-Only Adapter) 구성을 통해 인터넷 및 내부 네트워크 사용

### 9-1) VirtualBox 다운로드 및 설치
1. 아래 공식 사이트에서 Windows용 설치 파일 다운로드
   - https://www.virtualbox.org/wiki/Downloads
2. 다운로드된 `.exe` 파일 실행 → 설치 마법사 기본 옵션 유지하며 진행
3. 설치 완료 후 **VirtualBox** 바탕화면 아이콘 또는 시작 메뉴에서 실행

### 9-2) VirtualBox 실행 및 VM 생성
1. VirtualBox Manager 화면에서 **새로 만들기(New)** 클릭
2. VM 이름, 운영체제 종류, 버전 선택 후 메모리·디스크 용량 설정
3. ISO 이미지를 마운트하여 OS 설치 완료

### 9-3) NAT 네트워크 설정
> NAT(Network Address Translation)를 사용하면 VM이 호스트 PC의 인터넷 연결을 공유합니다.

1. VM 목록에서 해당 VM 선택 → **설정(Settings)** → **네트워크(Network)**
2. **어댑터 1(Adapter 1)** 탭 선택
3. **다음에 연결됨(Attached to)** 드롭다운을 **NAT** 로 선택
4. **확인(OK)** 저장 후 VM 시작

| 항목 | 값 |
|------|-----|
| 연결 유형 | NAT |
| 어댑터 종류 | Intel PRO/1000 MT Desktop (기본값) |
| 프로미스큐어스 모드 | 거부 (기본값) |

### 9-4) 인터넷 사용 NIC 설정 확인 (VM 내부)
VM을 부팅한 후 VM 내부 터미널에서 네트워크 인터페이스를 확인합니다.

```bash
# IP 주소 확인 (Linux VM 내부)
ip addr show
# 또는
ifconfig
```

NAT 구성 시 VM은 일반적으로 `10.0.2.15` 대역의 IP를 받으며, 게이트웨이는 `10.0.2.2` 입니다.

### 9-5) Windows 호스트 PC에서 ipconfig 확인
VirtualBox를 설치하면 Windows 호스트에 **VirtualBox Host-Only Ethernet Adapter** 가상 NIC가 추가됩니다.

```powershell
ipconfig /all
```

아래와 유사한 항목이 새로 추가된 것을 확인할 수 있습니다.

```
VirtualBox Host-Only Network:
   Connection-specific DNS Suffix  . :
   IPv4 Address. . . . . . . . . . . : 192.168.56.1
   Subnet Mask . . . . . . . . . . . : 255.255.255.0
   Default Gateway . . . . . . . . . :
```

---

## 10) VMware Workstation Player 설치 및 VM 네트워크 설정 (Windows)

### 설치 목적
- Windows PC에서 VMware 기반 가상 머신을 실행하여 Linux 등 다양한 OS 실습
- NAT 설정을 통해 VM에서 인터넷 접속 가능

### 10-1) VMware Workstation Player 다운로드 및 설치
1. 아래 공식 사이트에서 Windows용 무료 버전(Workstation Player) 다운로드
   - https://www.vmware.com/products/workstation-player.html
   - 또는 Broadcom Developer Portal: https://developer.broadcom.com/tools/vmware-workstation-player
2. 다운로드된 `.exe` 파일 실행 → 설치 마법사 기본 옵션 유지하며 진행
3. 설치 완료 후 **VMware Workstation Player** 실행

### 10-2) VMware 실행 및 VM 생성
1. VMware 메인 화면에서 **Create a New Virtual Machine** 클릭
2. ISO 이미지 선택 또는 **나중에 OS 설치(Install operating system later)** 선택
3. 게스트 OS 종류, 이름, 디스크 크기 설정 후 **Finish**

### 10-3) NAT 네트워크 설정
> VMware의 NAT 설정은 `VMnet8` 가상 스위치를 통해 VM이 호스트 PC의 인터넷을 공유합니다.

1. VM 목록에서 해당 VM 선택 → **Edit virtual machine settings** 클릭
2. **Network Adapter** 선택
3. **Network connection** 항목에서 **NAT: Used to share the host's IP address** 선택
4. **OK** 저장 후 VM 시작

| 항목 | 값 |
|------|-----|
| 연결 유형 | NAT (VMnet8) |
| 기본 서브넷 | 192.168.157.0/24 (VMware 설치 시 자동 할당) |
| 게이트웨이 IP | 192.168.157.2 |

### 10-4) 인터넷 사용 NIC 설정 확인 (VM 내부)
VM을 부팅한 후 VM 내부 터미널에서 네트워크 인터페이스를 확인합니다.

```bash
# IP 주소 확인 (Linux VM 내부)
ip addr show
# 또는
ifconfig

# 인터넷 연결 확인
ping -c 4 8.8.8.8
```

NAT 구성 시 VM은 VMnet8 서브넷 대역(예: `192.168.157.x`)의 IP를 DHCP로 할당받습니다.

### 10-5) Windows 호스트 PC에서 ipconfig 확인
VMware를 설치하면 Windows 호스트에 **VMware Network Adapter VMnet1** (Host-Only) 및 **VMware Network Adapter VMnet8** (NAT) 가상 NIC 2개가 추가됩니다.

```powershell
ipconfig /all
```

아래와 유사한 항목 2개가 새로 추가된 것을 확인할 수 있습니다.

```
VMware Network Adapter VMnet1 (Host-Only):
   IPv4 Address. . . . . . . . . . . : 192.168.116.1
   Subnet Mask . . . . . . . . . . . : 255.255.255.0
   Default Gateway . . . . . . . . . :

VMware Network Adapter VMnet8 (NAT):
   IPv4 Address. . . . . . . . . . . : 192.168.157.1
   Subnet Mask . . . . . . . . . . . : 255.255.255.0
   Default Gateway . . . . . . . . . :
```

---

## 11) VirtualBox vs VMware: Windows ipconfig 비교

VirtualBox와 VMware를 모두 설치하면 Windows의 `ipconfig` 결과에 아래와 같은 가상 NIC들이 추가됩니다.

| 가상화 소프트웨어 | 추가되는 NIC 이름 | 용도 | 기본 IP 대역 |
|---|---|---|---|
| VirtualBox | VirtualBox Host-Only Ethernet Adapter | 호스트 ↔ VM 통신 | 192.168.56.0/24 |
| VMware | VMware Network Adapter VMnet1 | Host-Only (호스트 ↔ VM) | 192.168.116.0/24 |
| VMware | VMware Network Adapter VMnet8 | NAT (VM 인터넷 공유) | 192.168.157.0/24 |

### ipconfig 실행 예시

```powershell
# Windows PowerShell 또는 명령 프롬프트(cmd)에서 실행
ipconfig /all
```

```
이더넷 어댑터 VirtualBox Host-Only Network:
   연결별 DNS 접미사 . . . . :
   IPv4 주소 . . . . . . . . . . . . : 192.168.56.1
   서브넷 마스크 . . . . . . . . . . : 255.255.255.0
   기본 게이트웨이 . . . . . . . . . :

이더넷 어댑터 VMware Network Adapter VMnet1:
   연결별 DNS 접미사 . . . . :
   IPv4 주소 . . . . . . . . . . . . : 192.168.116.1
   서브넷 마스크 . . . . . . . . . . : 255.255.255.0
   기본 게이트웨이 . . . . . . . . . :

이더넷 어댑터 VMware Network Adapter VMnet8:
   연결별 DNS 접미사 . . . . :
   IPv4 주소 . . . . . . . . . . . . : 192.168.157.1
   서브넷 마스크 . . . . . . . . . . : 255.255.255.0
   기본 게이트웨이 . . . . . . . . . :
```

> **참고**: 위 IP 주소는 소프트웨어 설치 시 자동으로 할당되며 환경에 따라 다를 수 있습니다.

---

## 이 저장소 실행 방법

### 1. 저장소 클론
```bash
git clone https://github.com/edumgt/java-education-001.git
cd java-education-001
```

### 2. Java 버전 확인
```bash
java --version
```

### 3. Maven 빌드
```bash
mvn clean package
```

### 기술 스택
- **Language**: Java 17+
- **Build Tool**: Maven
- **Logging**: Log4j2
- **IDE/Editor**: VS Code
- **Version Control**: Git + GitHub

---

## 25. 최종 추천 아키텍처

### 목표 환경

| 항목 | 구성 |
|------|------|
| 호스트 OS | Windows PC |
| 가상화 | VMware Workstation (Player/Pro) |
| 게스트 OS | Ubuntu 22.04 / 24.04 |
| Kubernetes | 단일 노드 (kubeadm) |
| 운영 서비스 | Harbor · Nexus · Jupyter |
| 외부 노출 | MetalLB + NGINX Ingress |

### 개발/테스트 환경 권장 구성

| 항목 | 권장 값 |
|------|---------|
| VMware 네트워크 | Bridged (권장) 또는 NAT |
| Ingress Controller | NGINX Ingress |
| LoadBalancer 구현 | MetalLB |
| 이름 해석 | Windows hosts 파일 |

### 운영/사내망 환경 권장 구성

| 항목 | 권장 값 |
|------|---------|
| 가상화 플랫폼 | VMware 또는 KVM/Proxmox |
| Ingress Controller | NGINX Ingress |
| LoadBalancer 구현 | MetalLB |
| 이름 해석 | 사내 DNS |

### 접속 흐름

```
사용자 브라우저
  ↓
web.test.com / jup.test.com / harbor.test.com / nexus.test.com
  ↓
hosts 파일 또는 사내 DNS
  ↓
MetalLB IP  (예: 192.168.253.200)
  ↓
NGINX Ingress Controller
  ↓
ClusterIP Service
  ↓
Pod
```

### 핵심 구성 요소 요약

| 구성 요소 | 역할 |
|-----------|------|
| VMnet1 | Host-only — 내부 테스트 전용 |
| VMnet8 | NAT — 인터넷/개발용 |
| Bridged | 실제 네트워크 연결 — 운영형에 적합 |
| Pod IP (`10.244.x.x`) | Kubernetes 내부 전용 |
| ClusterIP (`10.96.x.x`) | 클러스터 내부 서비스 통신용 |
| MetalLB IP | 외부 접속용 가상 LoadBalancer IP |
| Ingress | 도메인 기반 라우팅 (가상 호스트) |
| Windows hosts | 개발·테스트용 이름 해석 |
| 사내 DNS | 운영용 이름 해석 |

---

## 26. 실전 예시: VMware NAT 환경에서 Kubernetes 외부 접속

### 현재 VMware 네트워크 예시

```
VMnet1 (Host-only) : 192.168.111.1/24
VMnet8 (NAT)       : 192.168.253.1/24
```

VM이 NAT(VMnet8)에 연결된 경우, 게스트 VM 안에서 아래와 같은 주소를 받습니다.

```
게스트 VM IP       : 192.168.253.128  (DHCP 자동 할당)
```

### Kubernetes 내부 주소 구조

```
Pod IP         = 10.244.x.x     ← 클러스터 내부 전용
Service IP     = 10.96.x.x      ← 클러스터 내부 전용
MetalLB IP     = 192.168.253.200 ← 외부(Windows)에서 접근 가능
```

### Windows hosts 파일 설정 예시

파일 경로: `C:\Windows\System32\drivers\etc\hosts`

```
# Kubernetes 서비스 도메인 (MetalLB Ingress IP)
192.168.253.200  web.test.com
192.168.253.200  jup.test.com
192.168.253.200  harbor.test.com
192.168.253.200  nexus.test.com
```

> **참고**: hosts 파일 편집은 메모장을 **관리자 권한으로 실행** 한 뒤 위 경로를 열어야 합니다.

### 결론

브라우저에서 사용자가 실제로 접속하는 주소는 Pod IP나 ClusterIP가 아니라,
**MetalLB IP에 연결된 Ingress 도메인**입니다.

---

## 27. Ubuntu VM 단일 노드 Kubernetes 자동 설치

### 설치 스크립트 개요

이 저장소에는 VMware VM(Ubuntu 22.04/24.04) 위에 단일 노드 Kubernetes 클러스터를
자동으로 구성하는 셸 스크립트 `k8s-single-node-setup.sh` 가 포함되어 있습니다.

**설치 구성 요소**

| 구성 요소 | 버전/비고 |
|-----------|-----------|
| containerd | 최신 안정 버전 (Docker apt repo) |
| kubeadm / kubelet / kubectl | Kubernetes 1.29 |
| **Calico CNI** | v3.28.0 (Flannel 대체, Tigera Operator 방식) |
| **Metrics Server** | 최신 안정 버전 (단일 노드 insecure-tls 패치 포함) |
| **Headlamp** | v0.24.1 (CNCF 인큐베이팅, Helm Chart, LoadBalancer 노출) |
| MetalLB | v0.14.5 |
| NGINX Ingress Controller | v1.10.1 |

### Calico vs Flannel 비교

| 항목 | Flannel | Calico |
|------|---------|--------|
| 설치 복잡도 | 단순 (단일 YAML) | Tigera Operator 방식 |
| 네트워크 정책 | ❌ 미지원 | ✅ NetworkPolicy 완전 지원 |
| 캡슐화 | VXLAN | VXLAN / BGP / IPinIP 선택 가능 |
| 성능 | 보통 | 높음 (BGP 모드 시 오버헤드 없음) |
| 멀티 클러스터 | 제한적 | ✅ BGP 피어링 지원 |
| 운영 환경 채택 | 개발/학습 | 프로덕션 표준 |

> **이 스크립트에서는 VXLANCrossSubnet 캡슐화를 사용합니다.**  
> (동일 서브넷 내 Pod 간 통신은 오버헤드 없이 직접 라우팅, 서브넷 간에는 VXLAN 사용)

### Headlamp 대시보드 소개

Headlamp는 CNCF Incubating 프로젝트로, 기존 Kubernetes Dashboard의 단점을 개선한
최신 웹 기반 관리 UI 입니다.

| 항목 | 설명 |
|------|------|
| 설치 방식 | Helm Chart (LoadBalancer 타입으로 노출) |
| 인증 | Service Account 토큰 기반 |
| 기능 | 리소스 조회·수정, 로그 확인, Shell 접속, 플러그인 시스템 |
| 접속 URL | `http://<MetalLB-IP>` (MetalLB IP 풀에서 자동 할당) |

**접속 토큰 생성:**

```bash
# 관리자 ServiceAccount 생성
kubectl create serviceaccount headlamp-admin -n headlamp
kubectl create clusterrolebinding headlamp-admin \
  --clusterrole=cluster-admin \
  --serviceaccount=headlamp:headlamp-admin

# 토큰 발급 (브라우저 로그인 시 사용)
kubectl create token headlamp-admin -n headlamp
```

### Metrics Server 소개

Metrics Server는 `kubectl top` 명령어와 HPA(Horizontal Pod Autoscaler) 동작에 필요한
CPU / 메모리 사용량 데이터를 kubelet 에서 수집합니다.

```bash
# 노드 리소스 사용량 확인
kubectl top nodes

# Pod 리소스 사용량 확인
kubectl top pods -A
```

> **단일 노드 주의사항**: 자체 서명 인증서 환경에서 kubelet 메트릭을 수집하기 위해  
> `--kubelet-insecure-tls` 플래그가 자동으로 패치됩니다.

### 설치 단계 상세

```
 1/10  시스템 기본 설정  (swap 비활성화, 커널 모듈, sysctl)
 2/10  containerd 설치  (Docker apt repo, SystemdCgroup=true)
 3/10  kubeadm / kubelet / kubectl 설치 (pkgs.k8s.io, 버전 고정)
 4/10  kubeadm init  (단일 노드 클러스터 초기화, control-plane taint 제거)
 5/10  Calico CNI 설치  (Tigera Operator + Installation CR)
 6/10  Metrics Server 설치  (kubelet-insecure-tls 패치)
 7/10  Headlamp 대시보드 설치  (Helm, LoadBalancer 타입)
 8/10  MetalLB 설치 및 IPAddressPool 구성
 9/10  NGINX Ingress Controller 설치
10/10  설치 결과 출력
```

### 사전 요구 사항

| 항목 | 최소 사양 |
|------|----------|
| OS | Ubuntu 22.04 LTS 또는 24.04 LTS |
| CPU | 2코어 이상 |
| RAM | 4 GB 이상 (8 GB 권장) |
| Disk | 30 GB 이상 |
| 네트워크 | 인터넷 접속 가능 (패키지 다운로드) |
| 권한 | sudo 권한 필요 |

### 설치 방법

#### 1단계: 스크립트 다운로드 (또는 저장소 클론)

```bash
git clone https://github.com/edumgt/Dev-Lab-Install.git
cd Dev-Lab-Install
```

#### 2단계: MetalLB IP 범위 확인 및 수정 (선택)

VM 이 사용하는 네트워크 대역에 맞게 스크립트 상단의 변수를 수정합니다.

```bash
# k8s-single-node-setup.sh 상단 변수 (기본값 예시)
METALLB_IP_RANGE="192.168.253.200-192.168.253.220"
```

VM 의 실제 IP 대역 확인:

```bash
ip addr show        # VM 내부에서 실행
```

#### 3단계: 스크립트 실행

```bash
chmod +x k8s-single-node-setup.sh
sudo ./k8s-single-node-setup.sh
```

스크립트는 아래 10단계를 순서대로 자동 진행합니다.

#### 4단계: 설치 완료 확인

```bash
kubectl get nodes -o wide
kubectl get pods -A
kubectl get svc -n ingress-nginx
kubectl top nodes          # Metrics Server 동작 확인
```

정상 설치 시 출력 예시:

```
NAME       STATUS   ROLES           AGE   VERSION   INTERNAL-IP       ...
ubuntu-vm  Ready    control-plane   2m    v1.29.x   192.168.253.128   ...
```

### Windows hosts 파일 설정

설치 완료 후 스크립트가 출력하는 `Ingress LB IP` 값을 사용하여
Windows hosts 파일에 도메인을 등록합니다.

**hosts 파일 경로**: `C:\Windows\System32\drivers\etc\hosts`

```
# ── Kubernetes Ingress (MetalLB) ──────────────────────────
192.168.253.200  web.test.com
192.168.253.200  jup.test.com
192.168.253.200  harbor.test.com
192.168.253.200  nexus.test.com
192.168.253.200  gitlab.test.com
# ─────────────────────────────────────────────────────────
```

> **편집 방법**: 메모장(Notepad)을 **관리자 권한으로 실행** → 파일 열기 → 위 경로 입력 → 추가 후 저장

### 최종 요약

```
한 줄 요약
──────────────────────────────────────────────────────────────
VMware 환경에서 Kubernetes 서비스를 외부에서 쓰려면,
VM의 실제 네트워크 대역을 기준으로 MetalLB IP를 만들고,
그 IP에 Ingress 도메인을 연결하는 방식이 가장 깔끔합니다.
──────────────────────────────────────────────────────────────
```

---

## 28. VMware VM에 Nexus Repository Manager 설치

### 설치 목적
- Maven / npm / Docker / PyPI / Helm 패키지를 사내에서 캐싱·배포하는 범용 아티팩트 저장소
- 외부 인터넷 없이도 패키지를 프록시·호스팅하여 개발 환경 독립성 확보

### VM 사양 권장

| 항목 | 권장값 |
|------|-------|
| OS | Ubuntu 22.04 LTS |
| CPU | 4코어 이상 |
| RAM | 8 GB 이상 (JVM 기반) |
| Disk | 100 GB 이상 (저장소 데이터 포함) |
| 네트워크 | VMware NAT 또는 Bridged |

### 28-1) Java 17 설치 (Nexus 실행 필수)

```bash
sudo apt-get update -y
sudo apt-get install -y openjdk-17-jdk

# 버전 확인
java -version
```

### 28-2) Nexus 다운로드 및 설치

```bash
# 최신 버전 확인: https://help.sonatype.com/en/download.html
NEXUS_VERSION="3.68.0-04"
cd /opt

sudo wget -q "https://download.sonatype.com/nexus/3/nexus-${NEXUS_VERSION}-unix.tar.gz"
sudo tar -zxf "nexus-${NEXUS_VERSION}-unix.tar.gz"
sudo mv nexus-${NEXUS_VERSION} nexus
sudo rm -f "nexus-${NEXUS_VERSION}-unix.tar.gz"

# nexus 전용 사용자 생성
sudo useradd -r -d /opt/nexus -s /bin/false nexus
sudo chown -R nexus:nexus /opt/nexus /opt/sonatype-work
```

### 28-3) Nexus systemd 서비스 등록

```bash
sudo tee /etc/systemd/system/nexus.service > /dev/null <<'EOF'
[Unit]
Description=Sonatype Nexus Repository Manager
After=network.target

[Service]
Type=forking
User=nexus
ExecStart=/opt/nexus/bin/nexus start
ExecStop=/opt/nexus/bin/nexus stop
Restart=on-failure
LimitNOFILE=65536

[Install]
WantedBy=multi-user.target
EOF

sudo systemctl daemon-reload
sudo systemctl enable nexus
sudo systemctl start nexus
```

### 28-4) 초기 접속 및 설정

```bash
# 서비스 기동 대기 (약 1~2분 소요)
sudo journalctl -u nexus -f

# 초기 관리자 비밀번호 확인
sudo cat /opt/sonatype-work/nexus3/admin.password
```

브라우저에서 접속:
```
http://<VM-IP>:8081
```

| 항목 | 값 |
|------|-----|
| 기본 포트 | 8081 |
| 초기 계정 | admin |
| 초기 비밀번호 | `/opt/sonatype-work/nexus3/admin.password` 파일 내용 |

### 28-5) Maven 프록시 저장소 생성 예시

Nexus UI → **Administration** → **Repositories** → **Create repository**:

| 저장소 유형 | 이름 (예시) | Remote URL |
|------------|------------|------------|
| maven2 (proxy) | maven-central | `https://repo1.maven.org/maven2/` |
| npm (proxy) | npm-proxy | `https://registry.npmjs.org` |
| docker (hosted) | docker-hosted | — (내부 Push용) |
| helm (proxy) | helm-proxy | `https://charts.helm.sh/stable` |

### 28-6) Maven settings.xml 설정 예시

```xml
<!-- ~/.m2/settings.xml -->
<settings>
  <mirrors>
    <mirror>
      <id>nexus</id>
      <mirrorOf>*</mirrorOf>
      <url>http://<VM-IP>:8081/repository/maven-central/</url>
    </mirror>
  </mirrors>
  <servers>
    <server>
      <id>nexus</id>
      <username>admin</username>
      <password>your-password</password>
    </server>
  </servers>
</settings>
```

---

## 29. VMware VM에 Harbor Container Registry 설치

### 설치 목적
- Docker 이미지를 사내에 저장·관리하는 엔터프라이즈급 컨테이너 이미지 레지스트리
- 이미지 취약점 스캔(Trivy), RBAC, 복제(Replication) 기능 제공

### VM 사양 권장

| 항목 | 권장값 |
|------|-------|
| OS | Ubuntu 22.04 LTS |
| CPU | 2코어 이상 |
| RAM | 4 GB 이상 |
| Disk | 40 GB 이상 |
| 네트워크 | VMware NAT 또는 Bridged |

### 29-1) Docker 및 Docker Compose 설치

```bash
sudo apt-get update -y
sudo apt-get install -y ca-certificates curl gnupg

install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg \
  | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" \
  | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update -y
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin

# 현재 사용자를 docker 그룹에 추가
sudo usermod -aG docker $USER
newgrp docker

docker --version
docker compose version
```

### 29-2) Harbor 설치 파일 다운로드

```bash
# 최신 버전 확인: https://github.com/goharbor/harbor/releases
HARBOR_VERSION="v2.11.0"
cd /opt

sudo wget -q "https://github.com/goharbor/harbor/releases/download/${HARBOR_VERSION}/harbor-online-installer-${HARBOR_VERSION}.tgz"
sudo tar -zxf "harbor-online-installer-${HARBOR_VERSION}.tgz"
cd harbor
```

### 29-3) Harbor 설정 파일 수정

```bash
cp harbor.yml.tmpl harbor.yml
```

`harbor.yml` 주요 설정 항목:

```yaml
# harbor.yml

# VM 의 실제 IP 또는 도메인으로 변경
hostname: 192.168.253.100

# HTTP 포트 (HTTPS 사용 시 인증서 설정 필요)
http:
  port: 80

# HTTPS 비활성화 (테스트 환경 — 주석 처리)
# https:
#   port: 443
#   certificate: /your/certificate/path
#   private_key: /your/private/key/path

# 관리자 초기 비밀번호
harbor_admin_password: Harbor12345

# 데이터 저장 경로
data_volume: /data/harbor
```

### 29-4) Harbor 설치 및 시작

```bash
sudo mkdir -p /data/harbor
sudo ./install.sh --with-trivy   # 취약점 스캐너 Trivy 포함

# 서비스 상태 확인
docker compose ps
```

### 29-5) 초기 접속

브라우저에서 접속:
```
http://<VM-IP>
```

| 항목 | 값 |
|------|-----|
| 기본 포트 | 80 |
| 초기 계정 | admin |
| 초기 비밀번호 | Harbor12345 (harbor.yml 에서 변경 가능) |

### 29-6) Docker 클라이언트에서 Harbor 사용

```bash
# insecure-registries 설정 (HTTP 사용 시)
# /etc/docker/daemon.json
sudo tee /etc/docker/daemon.json > /dev/null <<'EOF'
{
  "insecure-registries": ["192.168.253.100"]
}
EOF
sudo systemctl restart docker

# Harbor 로그인
docker login 192.168.253.100
# Username: admin
# Password: Harbor12345

# 이미지 Push 예시
docker tag myapp:latest 192.168.253.100/library/myapp:latest
docker push 192.168.253.100/library/myapp:latest

# 이미지 Pull 예시
docker pull 192.168.253.100/library/myapp:latest
```

### 29-7) Kubernetes에서 Harbor 이미지 사용

```bash
# Harbor 인증 정보를 Kubernetes Secret으로 등록
kubectl create secret docker-registry harbor-secret \
  --docker-server=192.168.253.100 \
  --docker-username=admin \
  --docker-password=Harbor12345 \
  -n default
```

Deployment에 `imagePullSecrets` 추가:

```yaml
spec:
  template:
    spec:
      imagePullSecrets:
      - name: harbor-secret
      containers:
      - name: myapp
        image: 192.168.253.100/library/myapp:latest
```

---

## 30. VMware VM에 GitLab CE 설치

### 설치 목적
- 사내 Git 저장소, CI/CD 파이프라인(GitLab CI), 이슈 트래커를 한 곳에서 운영
- 코드 검토, 병합 요청(Merge Request), 컨테이너 레지스트리 내장

### VM 사양 권장

| 항목 | 권장값 |
|------|-------|
| OS | Ubuntu 22.04 LTS |
| CPU | 4코어 이상 |
| RAM | 8 GB 이상 (GitLab 최소 4 GB, 권장 8 GB) |
| Disk | 50 GB 이상 |
| 네트워크 | VMware NAT 또는 Bridged |

### 30-1) 의존 패키지 설치

```bash
sudo apt-get update -y
sudo apt-get install -y curl openssh-server ca-certificates tzdata perl postfix

# postfix 설치 시 "Internet Site" 선택, 시스템 메일 이름 입력
```

### 30-2) GitLab 패키지 저장소 등록 및 설치

```bash
# GitLab CE 공식 설치 스크립트
curl -fsSL https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.deb.sh \
  | sudo bash

# external_url 을 VM 의 실제 IP 또는 도메인으로 지정하여 설치
sudo EXTERNAL_URL="http://192.168.253.101" apt-get install -y gitlab-ce
```

> 설치에 약 5~10분 소요됩니다.

### 30-3) GitLab 설정 적용

```bash
# 설정 변경 후 항상 실행
sudo gitlab-ctl reconfigure

# 서비스 상태 확인
sudo gitlab-ctl status
```

### 30-4) 초기 접속 및 비밀번호 확인

```bash
# 초기 root 비밀번호 확인 (설치 후 24시간 내 파일 자동 삭제됨)
sudo cat /etc/gitlab/initial_root_password
```

브라우저에서 접속:
```
http://192.168.253.101
```

| 항목 | 값 |
|------|-----|
| 기본 포트 | 80 (HTTP) |
| 초기 계정 | root |
| 초기 비밀번호 | `/etc/gitlab/initial_root_password` 파일 내용 |

### 30-5) 주요 gitlab.rb 설정

```bash
sudo vi /etc/gitlab/gitlab.rb
```

```ruby
# 외부 접속 URL
external_url 'http://192.168.253.101'

# 내장 NGINX 포트 변경 (다른 서비스와 충돌 시)
nginx['listen_port'] = 80

# 시간대 설정
gitlab_rails['time_zone'] = 'Asia/Seoul'

# 이메일 발신자 설정 (선택)
gitlab_rails['gitlab_email_from'] = 'gitlab@example.com'

# 레지스트리 포트 설정 (내장 컨테이너 레지스트리 사용 시)
registry_external_url 'http://192.168.253.101:5050'
```

설정 변경 후:
```bash
sudo gitlab-ctl reconfigure
sudo gitlab-ctl restart
```

### 30-6) GitLab Runner 설치 (CI/CD 실행 에이전트)

```bash
# GitLab Runner 패키지 저장소 등록
curl -fsSL https://packages.gitlab.com/install/repositories/runner/gitlab-runner/script.deb.sh \
  | sudo bash

sudo apt-get install -y gitlab-runner

# Runner 등록 (GitLab UI 에서 토큰 확인: Settings → CI/CD → Runners)
sudo gitlab-runner register \
  --url "http://192.168.253.101" \
  --registration-token "<YOUR_TOKEN>" \
  --executor "shell" \
  --description "local-shell-runner" \
  --tag-list "shell,local"

# Runner 서비스 시작
sudo systemctl enable gitlab-runner
sudo systemctl start gitlab-runner
```

### 30-7) GitLab CI/CD 파이프라인 예시 (.gitlab-ci.yml)

```yaml
# .gitlab-ci.yml
stages:
  - build
  - test
  - deploy

variables:
  IMAGE: 192.168.253.100/library/myapp  # Harbor 레지스트리 경로

build:
  stage: build
  script:
    - docker build -t $IMAGE:$CI_COMMIT_SHA .
    - docker push $IMAGE:$CI_COMMIT_SHA

test:
  stage: test
  script:
    - docker run --rm $IMAGE:$CI_COMMIT_SHA ./run-tests.sh

deploy:
  stage: deploy
  script:
    - kubectl set image deployment/myapp myapp=$IMAGE:$CI_COMMIT_SHA
  only:
    - main
```

---

## 31. VMware VM에 NAS (OpenMediaVault) 설치

### 설치 목적
- 사내 파일 공유 서버(NFS / SMB / SFTP)를 VM 위에 구성
- 개발 환경에서 공유 볼륨, 백업 저장소, ISO 파일 서버로 활용
- OpenMediaVault(OMV)는 Debian 기반 NAS 전용 OS/소프트웨어

### VM 사양 권장

| 항목 | 권장값 |
|------|-------|
| OS | Debian 12 (Bookworm) 또는 Ubuntu 22.04 |
| CPU | 2코어 이상 |
| RAM | 2 GB 이상 |
| OS Disk | 20 GB (시스템용) |
| 데이터 Disk | 추가 가상 디스크 (50 GB 이상 권장) |
| 네트워크 | VMware NAT 또는 Bridged |

### 31-1) Debian 기반 VM에 OMV 설치 스크립트

```bash
# Debian 12 기준 설치 (root 권한 필요)
sudo apt-get update -y
sudo apt-get install -y gnupg curl

# OMV 공식 설치 스크립트 실행
curl -fsSL https://github.com/OpenMediaVault-Plugin-Developers/installScript/raw/master/install \
  | sudo bash
```

> 설치에 약 10~20분 소요되며 완료 후 자동 재부팅됩니다.

### 31-2) Ubuntu 22.04 기반 VM에 OMV 6/7 설치

Ubuntu를 사용하는 경우에도 동일한 스크립트로 설치 가능합니다:

```bash
sudo apt-get update -y && sudo apt-get upgrade -y
sudo apt-get install -y curl gnupg

# OMV 설치 스크립트 (Ubuntu/Debian 공용)
curl -fsSL https://github.com/OpenMediaVault-Plugin-Developers/installScript/raw/master/install \
  | sudo bash

# 설치 중 네트워크 설정 변경 주의: SSH 접속이 끊길 수 있음
# 재부팅 후 VM 콘솔에서 IP 확인
```

### 31-3) 초기 접속

브라우저에서 접속 (기본 포트: 80):
```
http://<VM-IP>
```

| 항목 | 값 |
|------|-----|
| 기본 포트 | 80 |
| 초기 계정 | admin |
| 초기 비밀번호 | openmediavault |

> **최초 로그인 후 반드시 비밀번호를 변경하세요.**

### 31-4) 데이터 디스크 마운트 및 공유 설정

**① VMware에서 추가 가상 디스크 연결**
1. VM 설정 → **Add** → **Hard Disk** → 새 가상 디스크 추가 (예: 100 GB)
2. VM 재부팅 후 OMV 웹 UI에서 확인

**② OMV 웹 UI에서 파일 시스템 설정**
```
Storage → Disks        → 추가된 디스크 확인 (예: /dev/sdb)
Storage → File Systems → Create → 포맷 선택 (ext4 권장) → Mount
```

**③ 공유 폴더 생성**
```
Storage → Shared Folders → Add
  Name: shared-data
  File system: 위에서 마운트한 파일 시스템 선택
  Path: /shared-data
  Permissions: Everyone: read/write
```

### 31-5) SMB/CIFS 공유 설정 (Windows 파일 공유)

```
Services → SMB/CIFS → Settings → Enable: ON → Save → Apply

Services → SMB/CIFS → Shares → Add
  Shared folder: shared-data
  Name: shared-data
  Enable: ON
  Browseable: ON
  Read only: OFF
```

Windows 탐색기에서 접속:
```
\\<VM-IP>\shared-data
```

### 31-6) NFS 공유 설정 (Linux/Kubernetes 연동)

```
Services → NFS → Settings → Enable: ON → Save → Apply

Services → NFS → Shares → Add
  Shared folder: shared-data
  Client: 192.168.253.0/24   (접근 허용 네트워크 대역)
  Privilege: Read/Write
  Squash: No root squash
```

Linux 클라이언트에서 마운트:
```bash
sudo mount -t nfs 192.168.253.102:/shared-data /mnt/nas
```

### 31-7) Kubernetes PersistentVolume으로 NAS NFS 연동

```yaml
# nas-pv.yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: nas-nfs-pv
spec:
  capacity:
    storage: 50Gi
  accessModes:
    - ReadWriteMany
  nfs:
    server: 192.168.253.102    # NAS VM IP
    path: /shared-data
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: nas-nfs-pvc
spec:
  accessModes:
    - ReadWriteMany
  resources:
    requests:
      storage: 50Gi
  volumeName: nas-nfs-pv
```

```bash
kubectl apply -f nas-pv.yaml
kubectl get pv,pvc
```

---

## 32. VMware 개발 랩 전체 구성 요약

### 권장 VM 구성

| VM 역할 | OS | IP 예시 | 주요 서비스 | RAM |
|---------|-----|---------|------------|-----|
| k8s-node | Ubuntu 22.04 | 192.168.253.128 | Kubernetes (kubeadm) | 8 GB |
| nexus | Ubuntu 22.04 | 192.168.253.100 | Nexus Repository | 8 GB |
| harbor | Ubuntu 22.04 | 192.168.253.101 | Harbor Registry | 4 GB |
| gitlab | Ubuntu 22.04 | 192.168.253.102 | GitLab CE | 8 GB |
| nas | Debian 12 | 192.168.253.103 | OpenMediaVault NAS | 2 GB |

### 서비스 접속 포트 요약

| 서비스 | 접속 URL | 기본 계정 |
|-------|---------|----------|
| Kubernetes (Headlamp) | `http://192.168.253.200` | 토큰 기반 |
| Nexus | `http://192.168.253.100:8081` | admin / (초기 파일 확인) |
| Harbor | `http://192.168.253.101` | admin / Harbor12345 |
| GitLab | `http://192.168.253.102` | root / (초기 파일 확인) |
| NAS (OMV) | `http://192.168.253.103` | admin / openmediavault |

### Windows hosts 파일 전체 예시

`C:\Windows\System32\drivers\etc\hosts`:

```
# ── Kubernetes Ingress (MetalLB) ──────────────────────────
192.168.253.200  web.test.com
192.168.253.200  harbor.test.com
192.168.253.200  nexus.test.com
192.168.253.200  gitlab.test.com

# ── 직접 접속 서비스 ──────────────────────────────────────
192.168.253.100  nexus.lab
192.168.253.101  harbor.lab
192.168.253.102  gitlab.lab
192.168.253.103  nas.lab
# ──────────────────────────────────────────────────────────
```

### 전체 아키텍처 흐름

```
개발자 PC (Windows)
  │
  ├── VMware Workstation
  │     │
  │     ├── k8s-node   (192.168.253.128)
  │     │     ├── Kubernetes 클러스터
  │     │     │     ├── Calico CNI
  │     │     │     ├── Metrics Server
  │     │     │     ├── Headlamp Dashboard
  │     │     │     ├── MetalLB (192.168.253.200~220)
  │     │     │     └── NGINX Ingress
  │     │     └── 앱 Pod (spring-hello, node-hello, ...)
  │     │
  │     ├── nexus      (192.168.253.100:8081)  → Maven/npm/Docker 프록시
  │     ├── harbor     (192.168.253.101:80)    → 컨테이너 이미지 레지스트리
  │     ├── gitlab     (192.168.253.102:80)    → Git + CI/CD
  │     └── nas        (192.168.253.103:80)    → NFS/SMB 파일 공유
  │
  └── 브라우저 접속
        ├── http://nexus.lab:8081
        ├── http://harbor.lab
        ├── http://gitlab.lab
        ├── http://nas.lab
        └── http://192.168.253.200  (Headlamp)
```


