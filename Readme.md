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

