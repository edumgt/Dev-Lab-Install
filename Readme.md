# 개발환경 기본 설정

## 시작 전 개인 준비 요약 (필독)

이 저장소는 **Python(FastAPI) · Node.js(Express) · Java(Spring Boot) · Docker · Kubernetes**를 순차적으로 실습하는 구조입니다.  
아래 항목을 먼저 개인 기준으로 채워두면 이후 실습 진행 속도가 크게 빨라집니다.

### 1) 개인별 습득 기술 스택 (필수/권장)

| 구분 | 필수 수준 | 저장소 기준 학습 포인트 |
|------|-----------|--------------------------|
| Python 3.11+ | 기초 문법 + 패키지 설치 가능 | `lab01-python-app`에서 FastAPI/uvicorn 실행, `requirements.txt` 이해 |
| Node.js 20+ | npm, 모듈 실행 가능 | `lab02-node-app`에서 Express 서버 실행 및 헬스체크 확인 |
| Java 17 + Maven | 빌드/테스트 실행 가능 | `lab03-java-app`에서 `mvn test`, Spring Boot 기동 |
| Docker | 이미지 빌드/컨테이너 실행 가능 | 각 lab의 `Dockerfile` 빌드 및 실행 |
| Kubernetes 기초 | Deployment/Service YAML 이해 | `lab04-k8s/*.yaml` 배포 구조 이해 |
| Git/GitHub | clone/branch/commit/PR | 팀 협업 및 과제 제출 기본 흐름 수행 |
| VS Code | 확장 설치, 터미널 사용 | 언어별 실습 코드 실행/디버깅 |

### 2) 본인 PC 사양 점검 기준 (개인 기록용)

| 항목 | 최소 권장 | 팀 프로젝트 권장 | 내 PC 실측값(작성) |
|------|-----------|------------------|--------------------|
| CPU | 4코어 8스레드 이상 | 8코어 16스레드 이상 | |
| RAM | 16GB | 32GB 이상 | |
| 저장공간 | 여유 50GB 이상(SSD 권장) | 여유 100GB 이상(NVMe 권장) | |
| 운영체제 | Windows 10/11 + WSL2 | Windows 11 + WSL2 최신 | |
| 네트워크 | 유선 또는 안정적인 Wi-Fi | 유선 LAN 우선 | |

- 아래 PC/네트워크 점검 섹션의 명령어로 실측값을 먼저 채우세요.
- Docker/VM/K8s 실습 시 메모리 부족이 가장 큰 병목이므로, 여유 RAM 확인을 최우선으로 권장합니다.

### 3) 가입해야 할 플랫폼 (개인 체크리스트)

실습 진행 전 아래 계정을 동일 이메일 기준으로 준비하세요.

- [ ] Gmail (기준 이메일/복구 수단)
- [ ] GitHub (2FA 활성화)
- [ ] Slack (팀 커뮤니케이션)
- [ ] Docker Hub (이미지 푸시/풀)
- [ ] AWS (MFA + 예산 알림 설정)

> 상세 가입 절차는 아래 **필수 플랫폼 계정 준비** 섹션을 그대로 따르면 됩니다.

### 4) 예상 비용(카드 청구 예상금액)

아래 금액은 개인 실습 기준의 **보수적 예상 범위**입니다. (환율/사용량에 따라 변동)

| 항목 | 필수 여부 | 월 예상 비용(USD) | 월 예상 비용(KRW, 환율에 따라 변동) | 비고 |
|------|-----------|-------------------|-----------------------------------------|------|
| Gmail | 필수 | 0 | 0원 | 개인 무료 |
| GitHub | 필수(Free 사용) | 0 | 0원 | 유료 플랜은 선택 |
| Slack | 필수(Free 사용) | 0 | 0원 | 팀 정책에 따라 유료 가능 |
| Docker Hub | 필수(Free 사용) | 0 | 0원 | 사용량 초과 시 유료 전환 가능 |
| AWS 실습 | 필수(가입) | 0 ~ 20 | 0 ~ 약 27,000원 | 프리티어 내 사용 권장, 초과 시 과금 |
| 선택: GitHub Copilot Individual | 선택 | 10 | 약 13,500원 | 학생/오픈소스 기여자는 무료 가능 |
| 선택: OpenAI/Claude/Gemini API | 선택 | 5 ~ 30+ | 약 6,750 ~ 40,500원+ | 호출량 기반 종량제 |

- **최소 시나리오(필수만, 프리티어 준수)**: 월 **0 ~ 약 27,000원**
- **권장 시나리오(필수 + Copilot)**: 월 **약 13,500 ~ 40,500원**
- AWS는 가입 직후 결제 수단 검증용 소액 승인(임시 결제/환불)이 발생할 수 있으므로 카드 알림을 켜두세요.

---

## AI 기반 비용 체크는 매일 오전 오후 체크해야 합니다.

- https://github.com/settings/billing/usage
- https://platform.openai.com/usage
- https://claude.ai/settings/usage

## 0) PC 기본 정보 및 네트워크 환경 확인 (PowerShell)

> 팀 프로젝트를 시작하기 전, 본인 PC의 하드웨어 사양과 네트워크 환경을 파악해두면 개발 환경 구성 및 팀원 간 자원 공유가 훨씬 수월해집니다.  
> 아래 명령어는 모두 **Windows PowerShell** (또는 **PowerShell 7 이상**) 에서 실행합니다.

---

### 0-1) CPU 정보 확인

```powershell
# CPU 이름, 코어 수, 논리 프로세서(스레드) 수
Get-CimInstance -ClassName Win32_Processor |
    Select-Object Name, NumberOfCores, NumberOfLogicalProcessors, MaxClockSpeed

# 간단 요약 (한 줄)
(Get-CimInstance Win32_Processor).Name
```

예상 출력 예시:
```
Name                                       NumberOfCores NumberOfLogicalProcessors MaxClockSpeed
----                                       ------------- ------------------------- -------------
Intel(R) Core(TM) i7-12700H CPU @ 2.30GHz            14                        20          2304
```

---

### 0-2) 메모리(RAM) 정보 확인

```powershell
# 전체 물리 메모리 (GB 단위)
$mem = Get-CimInstance Win32_ComputerSystem
[math]::Round($mem.TotalPhysicalMemory / 1GB, 2)

# 현재 사용 중인 메모리와 여유 메모리
$os = Get-CimInstance Win32_OperatingSystem
[PSCustomObject]@{
    "전체(GB)"  = [math]::Round($os.TotalVisibleMemorySize / 1MB, 2)
    "사용중(GB)" = [math]::Round(($os.TotalVisibleMemorySize - $os.FreePhysicalMemory) / 1MB, 2)
    "여유(GB)"  = [math]::Round($os.FreePhysicalMemory / 1MB, 2)
}

# 메모리 슬롯별 세부 정보 (용량, 속도, 제조사)
Get-CimInstance Win32_PhysicalMemory |
    Select-Object BankLabel, Capacity, Speed, Manufacturer |
    Format-Table -AutoSize
```

---

### 0-3) 디스크 용량 확인

```powershell
# 드라이브별 전체/사용/여유 용량 (GB)
Get-PSDrive -PSProvider FileSystem |
    Select-Object Name,
        @{N="전체(GB)"; E={[math]::Round($_.Used/1GB + $_.Free/1GB, 2)}},
        @{N="사용(GB)"; E={[math]::Round($_.Used/1GB, 2)}},
        @{N="여유(GB)"; E={[math]::Round($_.Free/1GB, 2)}} |
    Format-Table -AutoSize

# 물리 디스크 모델·타입 확인 (HDD/SSD 구분)
Get-PhysicalDisk |
    Select-Object FriendlyName, MediaType, Size, HealthStatus |
    Format-Table -AutoSize

# 디스크 파티션 상세 정보
Get-CimInstance Win32_LogicalDisk |
    Where-Object { $_.DriveType -eq 3 } |
    Select-Object DeviceID,
        @{N="전체(GB)"; E={[math]::Round($_.Size/1GB, 2)}},
        @{N="여유(GB)"; E={[math]::Round($_.FreeSpace/1GB, 2)}},
        @{N="사용률(%)"; E={[math]::Round((1 - $_.FreeSpace/$_.Size)*100, 1)}} |
    Format-Table -AutoSize
```

---

### 0-4) 시스템 전체 요약 한 번에 보기

```powershell
# OS, CPU, 메모리, 호스트명 한 번에 확인
Get-ComputerInfo |
    Select-Object CsName, OsName, OsVersion, OsArchitecture,
                  CsProcessors, CsNumberOfLogicalProcessors,
                  @{N="RAM(GB)"; E={[math]::Round($_.CsTotalPhysicalMemory/1GB,2)}}

# Windows 시스템 정보 (전통적인 방법)
systeminfo | Select-String "Host Name|OS|Processor|Total Physical"
```

---

### 0-5) 네트워크 어댑터 목록 확인

```powershell
# 활성화된 네트워크 어댑터 전체 목록
Get-NetAdapter | Select-Object Name, InterfaceDescription, Status, MacAddress, LinkSpeed

# 연결된(Up 상태) 어댑터만 필터링
Get-NetAdapter | Where-Object { $_.Status -eq "Up" }
```

---

### 0-6) 유선(Ethernet) 네트워크 설정 확인

```powershell
# 유선 어댑터의 IP, 서브넷, 게이트웨이, DNS 확인
Get-NetAdapter | Where-Object { $_.InterfaceDescription -match "Ethernet|Realtek|Intel.*Ethernet" -and $_.Status -eq "Up" } |
    ForEach-Object {
        $adapter = $_
        $ip = Get-NetIPAddress -InterfaceIndex $adapter.InterfaceIndex -AddressFamily IPv4 -ErrorAction SilentlyContinue
        $gw = Get-NetRoute -InterfaceIndex $adapter.InterfaceIndex -DestinationPrefix "0.0.0.0/0" -ErrorAction SilentlyContinue
        $dns = Get-DnsClientServerAddress -InterfaceIndex $adapter.InterfaceIndex -AddressFamily IPv4 -ErrorAction SilentlyContinue
        [PSCustomObject]@{
            어댑터      = $adapter.Name
            IP주소      = $ip.IPAddress
            서브넷길이  = $ip.PrefixLength
            게이트웨이  = $gw.NextHop
            DNS서버     = ($dns.ServerAddresses -join ", ")
            MAC주소     = $adapter.MacAddress
            링크속도    = $adapter.LinkSpeed
        }
    } | Format-List

# DHCP 또는 고정 IP 여부 확인
Get-NetIPConfiguration | Where-Object { $_.InterfaceAlias -match "Ethernet" } |
    Select-Object InterfaceAlias, IPv4Address, IPv4DefaultGateway, DNSServer
```

---

### 0-7) 무선(Wi-Fi) 네트워크 설정 확인

```powershell
# Wi-Fi 어댑터 정보 및 현재 연결 상태
Get-NetAdapter | Where-Object { $_.InterfaceDescription -match "Wi-Fi|Wireless|802.11" }

# 현재 연결된 Wi-Fi SSID 및 신호 세기
netsh wlan show interfaces

# 저장된 Wi-Fi 프로필 목록 조회
netsh wlan show profiles

# 특정 Wi-Fi 프로필의 비밀번호 확인 (관리자 권한 필요)
# netsh wlan show profile name="<SSID이름>" key=clear

# Wi-Fi 어댑터의 IP 설정 확인
Get-NetIPConfiguration | Where-Object { $_.InterfaceAlias -match "Wi-Fi|WiFi|Wireless" } |
    Select-Object InterfaceAlias, IPv4Address, IPv4DefaultGateway, DNSServer

# 주변 Wi-Fi 네트워크 스캔
netsh wlan show networks mode=bssid
```

---

### 0-8) IP 주소 및 연결 상태 종합 확인

```powershell
# 모든 어댑터의 IP 주소 (IPv4/IPv6) 확인
Get-NetIPAddress | Where-Object { $_.AddressFamily -eq "IPv4" -and $_.IPAddress -ne "127.0.0.1" } |
    Select-Object InterfaceAlias, IPAddress, PrefixLength, PrefixOrigin |
    Format-Table -AutoSize

# 라우팅 테이블 확인
Get-NetRoute -AddressFamily IPv4 | Where-Object { $_.NextHop -ne "0.0.0.0" } |
    Select-Object DestinationPrefix, NextHop, InterfaceAlias, RouteMetric |
    Format-Table -AutoSize

# 인터넷 연결 테스트 (Google DNS ping)
Test-Connection -ComputerName 8.8.8.8 -Count 4

# 특정 도메인 DNS 조회
Resolve-DnsName google.com

# 열려 있는 포트와 연결 상태 확인
Get-NetTCPConnection | Where-Object { $_.State -eq "Established" } |
    Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, State |
    Format-Table -AutoSize
```

---

### 0-9) 팀 단위 네트워크 공유 설정

팀원들이 같은 LAN(로컬 네트워크) 환경에서 파일·폴더를 공유하거나 서버에 접근할 수 있도록 설정하는 방법입니다.

#### ① 폴더 공유 (SMB/Windows 파일 공유)

```powershell
# 공유할 폴더 생성 (예: C:\TeamShare)
New-Item -ItemType Directory -Path "C:\TeamShare" -Force

# SMB 공유 생성 (팀원 전체에게 읽기/쓰기 권한)
New-SmbShare -Name "TeamShare" -Path "C:\TeamShare" -FullAccess "Everyone"

# 현재 공유 폴더 목록 확인
Get-SmbShare

# 공유에 접속 중인 세션 확인
Get-SmbSession

# 공유 폴더에 접근하는 방법 (다른 팀원 PC에서 실행)
# \\<공유하는PC의IP>\TeamShare  ← 파일 탐색기 주소창에 입력
# 또는 PowerShell에서:
# net use Z: \\192.168.1.100\TeamShare
```

#### ② 공유 폴더 접근 (팀원 PC에서 네트워크 드라이브 연결)

```powershell
# 네트워크 드라이브 연결 (Z: 드라이브로 매핑)
New-PSDrive -Name "Z" -PSProvider FileSystem -Root "\\192.168.1.100\TeamShare" -Persist

# 또는 net use 명령어로 연결
net use Z: \\192.168.1.100\TeamShare /persistent:yes

# 연결된 네트워크 드라이브 확인
Get-PSDrive -PSProvider FileSystem | Where-Object { $_.DisplayRoot -like "\\*" }
net use

# 네트워크 드라이브 연결 해제
Remove-PSDrive -Name "Z"
net use Z: /delete
```

#### ③ 팀원 PC 검색 및 연결 가능 여부 확인

```powershell
# 같은 네트워크의 PC 목록 스캔 (네트워크 대역 192.168.1.0/24 예시)
1..254 | ForEach-Object {
    $ip = "192.168.1.$_"
    if (Test-Connection -ComputerName $ip -Count 1 -Quiet -TimeoutSeconds 1) {
        [PSCustomObject]@{ IP = $ip; Status = "응답함" }
    }
} | Format-Table -AutoSize

# 특정 팀원 PC IP로 Ping 테스트
Test-Connection -ComputerName 192.168.1.100 -Count 4

# 특정 포트 열려 있는지 확인 (예: 8080 포트)
Test-NetConnection -ComputerName 192.168.1.100 -Port 8080

# 호스트명으로 IP 조회
[System.Net.Dns]::GetHostAddresses("팀원PC이름")

# ARP 테이블에서 같은 네트워크 장비 목록 확인
arp -a
```

#### ④ Windows 방화벽 설정 (공유 허용)

```powershell
# 파일 및 프린터 공유 방화벽 규칙 활성화 (공유 서버 역할 PC에서 실행)
Enable-NetFirewallRule -DisplayGroup "파일 및 프린터 공유"

# 또는 특정 포트 허용 (예: 개발 서버 포트 8080 개방)
New-NetFirewallRule -DisplayName "DevServer 8080" `
    -Direction Inbound -Protocol TCP -LocalPort 8080 -Action Allow

# 현재 활성화된 방화벽 규칙 확인
Get-NetFirewallRule | Where-Object { $_.Enabled -eq "True" -and $_.Direction -eq "Inbound" } |
    Select-Object DisplayName, Profile, Action |
    Format-Table -AutoSize

# 방화벽 규칙 삭제 (불필요할 때)
Remove-NetFirewallRule -DisplayName "DevServer 8080"
```

#### ⑤ 개발 서버 공유 (팀원이 내 PC의 개발 서버에 접속)

```powershell
# 내 PC의 현재 IP 주소 확인 (팀원에게 알려줄 IP)
(Get-NetIPAddress -AddressFamily IPv4 |
    Where-Object { $_.IPAddress -notmatch "^127\." -and $_.PrefixOrigin -ne "WellKnown" }
).IPAddress

# Python 개발 서버를 팀 전체에 오픈 (0.0.0.0 바인딩)
# python -m uvicorn main:app --host 0.0.0.0 --port 8000
# 팀원은 http://<위에서 확인한 내 IP>:8000 으로 접속

# Node.js 개발 서버 예시
# npx serve -l 3000 --host 0.0.0.0
```

#### ⑥ 네트워크 공유 설정 요약표

| 목적 | 방법 | 명령어 예시 |
|------|------|------------|
| 폴더 공유 (서버 측) | SMB 공유 생성 | `New-SmbShare -Name "TeamShare" -Path "C:\TeamShare" -FullAccess "Everyone"` |
| 폴더 접근 (클라이언트) | 네트워크 드라이브 매핑 | `net use Z: \\192.168.1.100\TeamShare` |
| 팀원 PC 검색 | Ping 스캔 | `Test-Connection -ComputerName 192.168.1.X -Count 1` |
| 포트 접근 확인 | TCP 연결 테스트 | `Test-NetConnection -ComputerName 192.168.1.100 -Port 8080` |
| 방화벽 허용 | 인바운드 규칙 추가 | `New-NetFirewallRule -LocalPort 8080 -Action Allow` |
| 개발 서버 공유 | 0.0.0.0 바인딩 | `uvicorn main:app --host 0.0.0.0 --port 8000` |

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

Python 프로젝트마다 독립적인 패키지 환경을 유지하기 위해 가상환경을 사용합니다.

#### venv (내장 모듈 — 가장 기본)
```bash
python -m venv .venv

# Windows PowerShell
.\.venv\Scripts\Activate.ps1

# macOS / Linux / WSL
source .venv/bin/activate

# 비활성화
deactivate
```

#### pyenv — 여러 Python 버전 관리 (macOS/Linux/WSL 권장)

pyenv 를 사용하면 프로젝트별로 다른 Python 버전을 사용할 수 있습니다.

```bash
# pyenv 설치 (Linux/WSL/macOS)
curl https://pyenv.run | bash

# 셸 설정 추가 (~/.bashrc 또는 ~/.zshrc)
echo 'export PYENV_ROOT="$HOME/.pyenv"' >> ~/.bashrc
echo 'command -v pyenv >/dev/null || export PATH="$PYENV_ROOT/bin:$PATH"' >> ~/.bashrc
echo 'eval "$(pyenv init -)"' >> ~/.bashrc
source ~/.bashrc

# 특정 Python 버전 설치
pyenv install 3.12.4
pyenv install 3.11.9

# 전역 버전 설정
pyenv global 3.12.4

# 특정 프로젝트 디렉터리에만 버전 고정
cd my-project
pyenv local 3.11.9

# 설치된 버전 목록 확인
pyenv versions
```

#### conda / Miniconda — 데이터 분석·ML 환경에 적합

```bash
# Miniconda 설치 (Linux/WSL)
wget https://repo.anaconda.com/miniconda/Miniconda3-latest-Linux-x86_64.sh
bash Miniconda3-latest-Linux-x86_64.sh -b -p $HOME/miniconda3
eval "$($HOME/miniconda3/bin/conda shell.bash hook)"
conda init

# 환경 생성 및 활성화
conda create -n myenv python=3.12
conda activate myenv

# 환경 목록 확인
conda env list

# 환경 삭제
conda remove -n myenv --all
```

---

### pip 기본 설정 및 패키지 관리

```bash
# pip 최신화 (항상 먼저 실행 권장)
pip install --upgrade pip

# 사내 또는 사설 PyPI 미러 설정 (Nexus 연동 등)
pip config set global.index-url http://<NEXUS-IP>:8081/repository/pypi-proxy/simple/
pip config set global.trusted-host <NEXUS-IP>

# 설치된 패키지 목록 저장 / 복원
pip freeze > requirements.txt
pip install -r requirements.txt

# 특정 패키지 설치 / 삭제
pip install requests fastapi uvicorn
pip uninstall requests

# 패키지 정보 확인
pip show requests
pip list --outdated
```

#### pyproject.toml 기반 현대적 프로젝트 구성 (PEP 518)

```bash
# pip 대신 uv (Rust 기반 초고속 패키지 관리자) 사용 권장
pip install uv

# 새 프로젝트 초기화
uv init my-project
cd my-project

# 패키지 추가 / 삭제
uv add fastapi uvicorn
uv remove requests

# 가상환경 생성 및 활성화
uv venv
source .venv/bin/activate

# pyproject.toml 기반 동기화
uv sync
```

---

### 코드 품질 도구 설치 및 설정

```bash
# 포매터 (코드 스타일 자동 정렬)
pip install black isort

# 린터 (코드 오류·스타일 검사)
pip install flake8 pylint

# 타입 검사
pip install mypy

# 통합 린터 (black + isort + flake8 대체)
pip install ruff

# 한 번에 설치
pip install black isort flake8 mypy ruff
```

**ruff 설정 예시** (`pyproject.toml`):

```toml
[tool.ruff]
line-length = 88
select = ["E", "F", "I", "N", "W"]
ignore = ["E501"]

[tool.black]
line-length = 88

[tool.isort]
profile = "black"
```

**실행 예시**:
```bash
# 코드 포매팅
black .
isort .

# 린트 검사
ruff check .
mypy src/

# 자동 수정
ruff check --fix .
```

---

### 테스트 환경 구성

```bash
# pytest 설치
pip install pytest pytest-cov

# 테스트 실행
pytest

# 커버리지 포함 실행
pytest --cov=src --cov-report=html

# 특정 파일/함수만 실행
pytest tests/test_main.py::test_hello -v
```

**테스트 파일 예시** (`tests/test_main.py`):

```python
def add(a: int, b: int) -> int:
    return a + b

def test_add():
    assert add(1, 2) == 3
    assert add(-1, 1) == 0
```

---

### VS Code Python 개발 환경 설정

**필수 확장(Extension) 설치**:

| 확장 이름 | 용도 |
|-----------|------|
| Python (Microsoft) | 기본 Python 지원 |
| Pylance | 타입 검사, IntelliSense |
| Black Formatter | 저장 시 자동 포매팅 |
| Ruff | 빠른 린터 통합 |
| Python Test Explorer | pytest GUI 실행 |

**`.vscode/settings.json` 권장 설정**:

```json
{
  "python.defaultInterpreterPath": "${workspaceFolder}/.venv/bin/python",
  "editor.formatOnSave": true,
  "[python]": {
    "editor.defaultFormatter": "ms-python.black-formatter"
  },
  "python.linting.enabled": true,
  "python.linting.ruffEnabled": true,
  "python.testing.pytestEnabled": true,
  "python.testing.pytestArgs": ["tests"]
}
```

---

### Python 개발 환경 구성 요약표

| 구성 요소 | 도구 | 역할 |
|-----------|------|------|
| 버전 관리 | pyenv | 여러 Python 버전 전환 |
| 가상환경 | venv / conda | 프로젝트별 패키지 격리 |
| 패키지 관리 | pip / uv | 패키지 설치·삭제·동기화 |
| 포매터 | black / ruff | 코드 스타일 자동 정렬 |
| 린터 | ruff / flake8 | 코드 오류 검사 |
| 타입 검사 | mypy | 정적 타입 분석 |
| 테스트 | pytest | 단위·통합 테스트 |
| IDE | VS Code + Pylance | 통합 개발 환경 |

### 공식 사이트
- https://www.python.org/downloads/
- https://github.com/pyenv/pyenv
- https://docs.conda.io/en/latest/miniconda.html
- https://github.com/astral-sh/uv
- https://github.com/astral-sh/ruff

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

Docker 설치/호환성/장애조치 내용은 별도 폴더 문서로 분리했습니다.

- 문서 경로: [`docker-setup/README.md`](docker-setup/README.md)

### 빠른 설치 순서 (Docker Desktop 기준)
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

## 8) 필수 플랫폼 계정 준비 (Gmail · GitHub · Slack · Docker Hub · AWS)

### 8-1) Gmail 계정 가입 및 보안 설정
1. https://accounts.google.com/signup 접속 → Google 계정 생성
2. 휴대전화/복구 이메일 등록으로 계정 복구 수단 설정
3. Google 계정 관리 → **보안** → **2단계 인증** 활성화
4. 보안 알림/로그인 기기 내역을 주기적으로 확인
5. 교육용 서비스 가입 시 동일 Gmail 주소를 기준 계정으로 통일 권장

### 8-2) GitHub 가입 및 2FA 설정
1. https://github.com 접속 → **Sign up**으로 계정 생성
2. 이메일 인증 완료 후 프로필 기본 설정
3. 우측 상단 프로필 → **Settings** → **Password and authentication**
4. **Two-factor authentication** 활성화
   - 권장: Authentication app(TOTP)
   - 대안: Security key (FIDO2)
5. 복구 코드(Recovery codes) 안전한 위치에 백업

### 8-3) Slack 가입 및 워크스페이스 사용
1. https://slack.com/get-started 접속 → 계정 생성(또는 Google 계정으로 계속)
2. 초대 링크 또는 이메일 도메인으로 팀 워크스페이스 참여
3. 프로필 이름/사진/표시명 설정 후 본인 소개 업데이트
4. 필수 채널(`announcements`, `general`, `qna` 등) 우선 참여
5. 기본 사용법
   - 스레드(Thread)로 답글 작성해 대화 맥락 유지
   - 코드/로그는 코드 블록(백틱 3개)으로 공유
   - 멘션(`@here`, `@channel`)은 필요한 경우에만 사용
6. 알림 설정 최적화
   - 모바일/데스크톱 알림 시간대 설정
   - 중요 채널은 알림 유지, 기타 채널은 음소거 권장

### 8-4) Docker Hub 가입 및 2FA 설정
1. https://hub.docker.com 접속 → **Sign Up**으로 계정 생성
2. 이메일 인증 후 로그인
3. 우측 상단 계정 메뉴 → **Account Settings** → **Security**
4. **Two-factor authentication** 활성화 (Authenticator 앱 권장)
5. CLI 사용 시 토큰 기반 인증 권장

```bash
docker login -u <dockerhub-username>
```

### 8-5) AWS 가입 및 MFA 설정
1. https://portal.aws.amazon.com/billing/signup 접속 → **Create an AWS Account**
2. 루트 계정 생성(결제 정보/전화 인증 포함)
3. AWS Console 로그인 후 우측 상단 계정 메뉴 → **Security credentials**
4. **Multi-factor authentication (MFA)** 에서 MFA 디바이스 등록
   - 권장: 가상 MFA 앱(TOTP) 또는 하드웨어 보안키
5. 루트 계정은 일상 작업에 사용하지 말고, 관리자 IAM 사용자 별도 생성 후 사용

### 8-6) 사전 점검 체크리스트
- [ ] Gmail 계정 생성 및 2단계 인증 활성화
- [ ] GitHub 계정 생성 및 2FA 활성화
- [ ] Slack 계정 생성 및 팀 워크스페이스 참여
- [ ] Docker Hub 계정 생성 및 2FA 활성화
- [ ] AWS 계정 생성 및 루트 MFA 활성화
- [ ] 복구 코드/백업 코드 오프라인 보관

### 8-7) Git · GitHub 실무 사용 가이드 (브랜치 · 머지 · PR · Fork · 공개범위 · Settings)

#### A. 기본 Git 초기 설정
```bash
git config --global user.name "Your Name"
git config --global user.email "you@example.com"
git config --global init.defaultBranch main
git config --global core.autocrlf true   # Windows 권장
```

```bash
# 현재 설정 확인
git config --list
```

#### B. 브랜치 전략 (권장)
- `main`: 배포 가능한 안정 브랜치
- `develop`(선택): 통합 개발 브랜치
- `feature/*`: 기능 개발
- `fix/*` 또는 `hotfix/*`: 버그 수정
- `docs/*`: 문서 변경

브랜치 생성/이동:
```bash
git checkout -b feature/login-api
# 또는
git switch -c feature/login-api
```

작업 후 원격 반영:
```bash
git add .
git commit -m "feat: add login api"
git push -u origin feature/login-api
```

#### C. Merge 방법 정리
GitHub PR 머지 방식은 보통 3가지입니다.

1. **Create a merge commit**
   - 모든 커밋 이력을 그대로 유지
   - 협업 이력 추적이 쉬움
2. **Squash and merge**
   - 여러 커밋을 1개로 합쳐 `main` 히스토리를 깔끔하게 유지
   - 작은 단위 커밋이 많은 팀에 권장
3. **Rebase and merge**
   - 머지 커밋 없이 직선 히스토리 유지
   - 커밋 순서/의미를 유지하고 싶은 경우 사용

로컬에서 충돌 해결 + 병합 예시:
```bash
git checkout main
git pull origin main
git checkout feature/login-api
git merge main
# 충돌 해결 후
git add .
git commit
git push
```

#### D. Pull Request(PR) 표준 절차
1. 작업 브랜치에서 기능 구현 + 테스트 완료
2. 최신 `main` 반영(merge 또는 rebase) 후 충돌 해결
3. 원격 브랜치 push
4. GitHub에서 **Compare & pull request** 클릭
5. PR 템플릿 기준으로 목적/변경점/테스트 결과 작성
6. 리뷰어 지정(Assignees/Reviewers), 라벨(Label), 이슈 연결
7. CI 통과 + 리뷰 승인 후 머지
8. 머지 후 작업 브랜치 삭제

PR 제목 예시:
- `feat: 사용자 로그인 API 추가`
- `fix: 주문 조회 시 NPE 오류 수정`
- `docs: GitHub 운영 가이드 보강`

#### E. GitHub Forking 워크플로우 (오픈소스 기여 시 필수)
1. 원본 저장소(Upstream)를 GitHub에서 **Fork**
2. 내 계정의 Fork 저장소를 로컬에 clone
3. `upstream` 원격 추가

```bash
git clone https://github.com/<my-id>/<fork-repo>.git
cd <fork-repo>
git remote add upstream https://github.com/<org-or-owner>/<original-repo>.git
git remote -v
```

4. 기능 브랜치 생성 후 작업/커밋/push
5. 내 Fork 저장소에서 원본 저장소로 PR 생성

Upstream 동기화:
```bash
git fetch upstream
git checkout main
git merge upstream/main
git push origin main
```

#### F. Public / Private 저장소 사용 기준
| 구분 | Public 저장소 | Private 저장소 |
|------|---------------|----------------|
| 가시성 | 누구나 코드 열람 가능 | 권한 있는 사용자만 접근 |
| 권장 용도 | 오픈소스, 포트폴리오, 공개 학습 자료 | 사내 코드, 과제/시험, 고객 데이터 포함 프로젝트 |
| 주의사항 | 비밀정보 절대 커밋 금지 | 멤버 권한 최소화 원칙 적용 |

공통 보안 원칙:
- 토큰/비밀번호/API 키는 코드에 직접 저장하지 않기
- `.env`, 인증서, 키 파일은 `.gitignore`에 등록
- Secret Scanning / Dependabot / Code Scanning 활성화

#### G. GitHub Settings 핵심 설정 체크리스트
**(1) 개인 계정 Settings**
- **Password and authentication**: 2FA 필수
- **SSH and GPG keys**: SSH 키 등록 후 비밀번호 없는 안전한 push
- **Notifications**: 메일/웹 알림 최적화
- **Developer settings → Personal access tokens**: 최소 권한 토큰 발급

**(2) 저장소 Settings**
- **General**
  - 기본 브랜치(`main`) 확인
  - 템플릿(이슈/PR) 적용
- **Collaborators and teams**
  - 필요한 사용자만 최소 권한 부여
- **Branches / Rulesets**
  - `main` 보호 규칙 설정:
    - PR 필수
    - 최소 1명 이상 리뷰 승인
    - 상태 체크(CI) 통과 필수
    - 강제 푸시 금지
    - 브랜치 삭제/직접 푸시 제한
- **Security**
  - Dependabot alerts / updates 활성화
  - Secret scanning 활성화
  - Code scanning(CodeQL 등) 활성화
- **Actions**
  - 허용 액션 범위(Organization/Marketplace/Local) 정책 설정
  - 외부 PR에 대한 워크플로우 실행 정책 점검

**(3) 조직(Organization) Settings**
- SSO, 멤버십 승인 정책, 저장소 생성 권한 제한
- 팀 단위 권한 관리(Read/Triage/Write/Maintain/Admin)
- 감사 로그(Audit log) 주기적 점검

#### H. 팀 운영 시 권장 규칙 요약
- 기능 개발은 항상 브랜치에서 진행 (`main` 직접 작업 금지)
- PR 단위로 코드 리뷰 후 머지
- 머지 방식은 팀 규칙으로 통일 (예: 기본 `Squash and merge`)
- 이슈 번호를 커밋/PR에 연결 (`fixes #123`)
- 월 1회 이상 Settings/권한/보안 경고 점검

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



---

## AI Agent 개발 환경 구성 가이드

> AI Agent(LLM 기반 코딩 보조 도구)를 개발 워크플로우에 통합하면 코드 작성, 리뷰, 테스트,
> 문서화 속도를 크게 높일 수 있습니다. 아래 각 섹션에서 도구별 API 키 발급·설치·사용 방법을
> 상세히 안내합니다.

---

### A1. Grok (xAI)

#### 개요
Elon Musk 가 설립한 xAI 에서 개발한 LLM. 실시간 X(Twitter) 데이터 접근 및 코드 생성에 강점.

#### API 키 발급
1. https://console.x.ai 접속 → 계정 생성 또는 로그인
2. **API Keys** → **Create API Key** 클릭
3. 생성된 키를 안전하게 복사

#### 환경 변수 설정
```bash
# Linux / macOS / WSL
echo 'export XAI_API_KEY="xai-your-key-here"' >> ~/.bashrc
source ~/.bashrc

# Windows PowerShell (영구 설정)
[System.Environment]::SetEnvironmentVariable("XAI_API_KEY", "xai-your-key-here", "User")
```

#### Python SDK 설치 및 사용

Grok API 는 OpenAI SDK 와 호환되는 인터페이스를 제공합니다.

```bash
pip install openai
```

```python
from openai import OpenAI

client = OpenAI(
    api_key=os.environ["XAI_API_KEY"],
    base_url="https://api.x.ai/v1",
)

response = client.chat.completions.create(
    model="grok-3",
    messages=[
        {"role": "system", "content": "You are a helpful coding assistant."},
        {"role": "user", "content": "Python 에서 REST API 를 호출하는 간단한 예시를 작성해줘."},
    ],
)
print(response.choices[0].message.content)
```

#### VS Code 연동
- 확장 마켓플레이스에서 **Continue** 확장 설치
- Continue 설정 파일(`~/.continue/config.json`)에 Grok 추가:

```json
{
  "models": [
    {
      "title": "Grok 3",
      "provider": "openai",
      "model": "grok-3",
      "apiBase": "https://api.x.ai/v1",
      "apiKey": "xai-your-key-here"
    }
  ]
}
```

---

### A2. Claude (Anthropic)

#### 개요
Anthropic 이 개발한 LLM. 안전성과 긴 컨텍스트(200K 토큰)에 강점. 코드 분석·리팩터링에 우수.

#### API 키 발급
1. https://console.anthropic.com 접속 → 계정 생성
2. **API Keys** → **Create Key** 클릭
3. 생성된 `sk-ant-...` 키 복사

#### 환경 변수 설정
```bash
echo 'export ANTHROPIC_API_KEY="sk-ant-your-key-here"' >> ~/.bashrc
source ~/.bashrc
```

#### Python SDK 설치 및 사용

```bash
pip install anthropic
```

```python
import anthropic
import os

client = anthropic.Anthropic(api_key=os.environ["ANTHROPIC_API_KEY"])

message = client.messages.create(
    model="claude-opus-4-5",
    max_tokens=1024,
    messages=[
        {
            "role": "user",
            "content": "아래 Python 코드를 리뷰하고 개선점을 알려줘:\n\ndef add(a,b):\n    return a+b",
        }
    ],
)
print(message.content[0].text)
```

#### Claude CLI (claude-code)

```bash
# Node.js 필요 (npm 환경)
npm install -g @anthropic-ai/claude-code

# 프로젝트 디렉터리에서 실행
cd my-project
claude

# 특정 작업 수행 (비대화형)
claude -p "이 프로젝트의 README 를 작성해줘"
```

#### MCP (Model Context Protocol) 서버 연동

Claude 는 MCP 를 통해 로컬 파일 시스템, DB, 외부 API 를 컨텍스트로 활용할 수 있습니다.

```bash
pip install mcp
```

`mcp_config.json` 예시:
```json
{
  "mcpServers": {
    "filesystem": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-filesystem", "/home/user/projects"]
    },
    "github": {
      "command": "npx",
      "args": ["-y", "@modelcontextprotocol/server-github"],
      "env": { "GITHUB_PERSONAL_ACCESS_TOKEN": "ghp_your_token" }
    }
  }
}
```

#### VS Code 연동
- **Continue** 확장 설정:

```json
{
  "models": [
    {
      "title": "Claude Opus 4.5",
      "provider": "anthropic",
      "model": "claude-opus-4-5",
      "apiKey": "sk-ant-your-key-here"
    }
  ]
}
```

---

### A3. ChatGPT / OpenAI API

#### 개요
OpenAI 가 개발한 GPT 시리즈. 범용 언어 이해·생성, 코드 생성, 이미지 분석(GPT-4o) 지원.

#### API 키 발급
1. https://platform.openai.com 접속 → 계정 생성
2. **API Keys** → **Create new secret key** 클릭
3. 생성된 `sk-...` 키 복사 (한 번만 표시되므로 즉시 저장)
4. **Billing** 에서 사용량 한도(Usage limit) 설정 권장

#### 환경 변수 설정
```bash
echo 'export OPENAI_API_KEY="sk-your-key-here"' >> ~/.bashrc
source ~/.bashrc
```

#### Python SDK 설치 및 사용

```bash
pip install openai
```

```python
from openai import OpenAI
import os

client = OpenAI(api_key=os.environ["OPENAI_API_KEY"])

# 텍스트 생성
response = client.chat.completions.create(
    model="gpt-4o",
    messages=[
        {"role": "system", "content": "You are a senior Python developer."},
        {"role": "user", "content": "FastAPI 로 CRUD API 를 작성하는 기본 구조를 알려줘."},
    ],
    temperature=0.2,
)
print(response.choices[0].message.content)

# 스트리밍 출력
stream = client.chat.completions.create(
    model="gpt-4o",
    messages=[{"role": "user", "content": "파이썬 제너레이터를 설명해줘."}],
    stream=True,
)
for chunk in stream:
    if chunk.choices[0].delta.content:
        print(chunk.choices[0].delta.content, end="", flush=True)
```

#### 비용 관리 팁
| 모델 | 용도 | 비용 수준 |
|------|------|----------|
| gpt-4o-mini | 일반 코드 보조, 간단한 질의 | 낮음 |
| gpt-4o | 복잡한 코드 분석·생성 | 중간 |
| o3 / o4-mini | 수학·알고리즘·추론 | 높음 |

```bash
# 사용량 모니터링 (Usage API)
curl https://api.openai.com/v1/usage \
  -H "Authorization: Bearer $OPENAI_API_KEY"
```

---

### A4. OpenAI Codex CLI

#### 개요
OpenAI Codex CLI 는 터미널에서 자연어로 코드 작업을 수행하는 AI 코딩 에이전트.
파일 읽기·수정, 명령 실행, 테스트 실행을 자율적으로 처리.

#### 설치

```bash
# Node.js 18+ 필요
npm install -g @openai/codex

# 버전 확인
codex --version
```

#### API 키 설정

```bash
export OPENAI_API_KEY="sk-your-key-here"
```

#### 기본 사용법

```bash
# 대화형 실행 (기본 sandbox 모드 — 파일 수정·명령 실행 전 승인 요청)
codex

# 특정 작업 실행
codex "이 프로젝트에 pytest 테스트를 추가해줘"

# 전체 자동 승인 모드 (주의: 파일이 자동 수정됨)
codex --approval-mode full-auto "requirements.txt 기반으로 Docker 이미지를 최적화해줘"

# 특정 모델 사용
codex --model o4-mini "버그를 찾아서 수정해줘"
```

#### 승인 모드 옵션

| 모드 | 설명 |
|------|------|
| `suggest` (기본) | 모든 변경 전 확인 요청 |
| `auto-edit` | 파일 수정은 자동, 명령 실행은 확인 |
| `full-auto` | 모든 작업 자동 처리 (신중하게 사용) |

#### 프로젝트별 지시사항 (`AGENTS.md`)

프로젝트 루트에 `AGENTS.md` 파일을 생성하면 Codex 가 자동으로 읽습니다:

```markdown
# Project Guidelines for AI Agents

## 코드 스타일
- Python: black 포매터, ruff 린터 사용
- 타입 힌트 필수 (mypy strict 모드)

## 테스트
- 모든 함수에 pytest 테스트 작성
- `pytest --cov=src` 로 커버리지 확인

## 금지 사항
- 프로덕션 DB에 직접 연결하지 말 것
- `.env` 파일 절대 수정 금지
```

---

### A5. GitHub Copilot

#### 개요
GitHub + OpenAI 가 공동 개발한 AI 코딩 보조 도구. VS Code, JetBrains IDE, Neovim, CLI 지원.
개인/팀/기업 플랜 제공 (학생·오픈소스 기여자 무료).

#### 구독 설정
1. https://github.com/settings/copilot 접속
2. **Enable GitHub Copilot** 클릭 (무료 체험 또는 구독)
3. 결제 정보 입력 (Individual: $10/월, Business: $19/월·사용자)

#### VS Code 확장 설치

```bash
# VS Code 명령 팔레트 (Ctrl+Shift+P)
# "Extensions: Install Extensions" → "GitHub Copilot" 검색 및 설치
# 또는 CLI 로 설치
code --install-extension GitHub.copilot
code --install-extension GitHub.copilot-chat
```

**로그인 방법**:
- VS Code 하단 상태바 Copilot 아이콘 클릭 → **Sign In to GitHub** → 브라우저에서 인증

#### VS Code 에서 Copilot 기능 활용

| 기능 | 단축키 / 사용법 |
|------|----------------|
| 코드 자동 완성 | 코드 작성 중 자동 표시 → `Tab` 수락 |
| 인라인 코드 생성 | `Ctrl+I` → 자연어로 코드 요청 |
| Copilot Chat | `Ctrl+Alt+I` 또는 사이드바 Chat 패널 |
| 코드 설명 | 코드 선택 후 우클릭 → **Copilot: Explain This** |
| 코드 수정 | 코드 선택 후 `Ctrl+I` → "/fix 오류 수정해줘" |
| 테스트 생성 | 함수 선택 후 Chat에서 `/tests` 입력 |
| 문서화 | 함수 선택 후 `/doc` 입력 |

#### Copilot Chat 슬래시 명령어

```
/explain  — 선택 코드 설명
/fix      — 버그 수정 제안
/tests    — 테스트 코드 생성
/doc      — 문서(docstring) 생성
/simplify — 코드 단순화
/new      — 새 파일/프로젝트 스캐폴딩
@workspace — 전체 프로젝트 컨텍스트 참조
@terminal  — 터미널 명령어 도움
```

#### GitHub Copilot CLI

```bash
# 설치
npm install -g @githubnext/github-copilot-cli

# GitHub 계정 인증
github-copilot-cli auth

# 사용법
github-copilot-cli what-the-shell "현재 디렉터리의 Python 파일을 모두 찾아줘"
github-copilot-cli git-assist "최근 3개 커밋을 하나로 합쳐줘"
github-copilot-cli explain "docker run -it --rm -v $(pwd):/app python:3.12"
```

#### `.github/copilot-instructions.md` — 리포지터리별 Copilot 지시사항

프로젝트 내 `.github/copilot-instructions.md` 파일을 생성하면 Copilot 이 자동으로 컨텍스트로 활용합니다:

```markdown
# Copilot Instructions

이 프로젝트는 FastAPI 기반 REST API 서버입니다.

## 코딩 규칙
- Python 3.12+ 문법 사용
- 모든 함수에 타입 힌트 및 docstring 작성
- 비동기(async/await) 패턴 우선 사용
- 예외 처리는 커스텀 예외 클래스 사용

## 테스트
- pytest + httpx 로 API 테스트 작성
- 모든 엔드포인트에 최소 1개 이상 테스트 필수
```

#### GitHub Copilot Agent (Coding Agent)

GitHub 이 제공하는 자율 코딩 에이전트. GitHub 이슈를 할당하면 자동으로 코드를 작성하고 PR 을 생성합니다.

**사용 방법**:
1. GitHub 이슈 생성 (상세한 요구사항 작성)
2. Assignee 를 **Copilot** 으로 설정
3. 에이전트가 브랜치 생성 → 코드 구현 → PR 생성
4. PR 에서 결과 검토 및 리뷰 코멘트 추가
5. 에이전트가 피드백을 반영하여 추가 수정

**효과적인 이슈 작성 팁**:
```markdown
## 작업 내용
사용자 인증 엔드포인트(`POST /auth/login`)를 구현해주세요.

## 요구사항
- 이메일 + 패스워드로 로그인
- JWT 토큰 반환 (access + refresh)
- 잘못된 인증 정보 시 401 응답
- bcrypt 로 비밀번호 해시 처리

## 기술 스택
- FastAPI, SQLAlchemy, python-jose, passlib

## 테스트
- pytest 로 정상·실패 케이스 모두 커버
```

---

### A6. Google Gemini

#### 개요
Google DeepMind 가 개발한 멀티모달 LLM. 코드 생성, 이미지·문서 분석, 긴 컨텍스트(1M 토큰) 지원.
Gemini API 는 무료 티어 제공.

#### API 키 발급
1. https://aistudio.google.com 접속 → Google 계정 로그인
2. **Get API Key** → **Create API key** 클릭
3. 생성된 `AIza...` 키 복사

#### 환경 변수 설정
```bash
echo 'export GEMINI_API_KEY="AIza-your-key-here"' >> ~/.bashrc
source ~/.bashrc
```

#### Python SDK 설치 및 사용

```bash
pip install google-genai
```

```python
from google import genai
import os

client = genai.Client(api_key=os.environ["GEMINI_API_KEY"])

# 텍스트 생성
response = client.models.generate_content(
    model="gemini-2.5-pro",
    contents="Python 에서 데코레이터 패턴을 설명하고 실용적인 예시를 작성해줘.",
)
print(response.text)

# 코드 실행 도구 활성화
response = client.models.generate_content(
    model="gemini-2.5-pro",
    contents="피보나치 수열 100번째 항을 계산해줘.",
    config={"tools": [{"code_execution": {}}]},
)
print(response.text)
```

#### Gemini CLI

```bash
# Node.js 필요
npm install -g @google/gemini-cli

# 인증 (Google 계정 또는 API 키)
gemini auth login

# API 키 방식
export GEMINI_API_KEY="AIza-your-key-here"

# 대화형 실행
gemini

# 비대화형 명령
gemini -p "이 Python 파일을 분석하고 리팩터링 제안을 해줘" < main.py

# 특정 모델 사용
gemini --model gemini-2.5-pro "이 코드의 시간복잡도를 분석해줘"
```

#### VS Code 연동 (Google Cloud Code 확장)

```bash
code --install-extension GoogleCloudTools.cloudcode
```

또는 **Continue** 확장으로 Gemini 연동:

```json
{
  "models": [
    {
      "title": "Gemini 2.5 Pro",
      "provider": "gemini",
      "model": "gemini-2.5-pro",
      "apiKey": "AIza-your-key-here"
    }
  ]
}
```

#### Gemini 무료 티어 한도 (2025년 기준)
| 모델 | 무료 요청 한도 |
|------|--------------|
| Gemini 2.0 Flash | 1,500 req/day |
| Gemini 2.5 Pro | 25 req/day |
| Gemini 1.5 Flash | 1,500 req/day |

---

### A7. AI Agent 도구 비교 및 선택 가이드

| 도구 | 제공사 | 무료 여부 | 주요 강점 | 코딩 특화 기능 |
|------|--------|----------|-----------|---------------|
| Grok | xAI | API 유료 | 실시간 데이터, 긴 컨텍스트 | 코드 생성·디버깅 |
| Claude | Anthropic | API 유료 (무료 웹 UI) | 안전성, 200K 컨텍스트, 코드 리뷰 | claude-code CLI, MCP |
| ChatGPT | OpenAI | 웹 무료, API 유료 | 범용 이해, GPT-4o 멀티모달 | Function Calling, 플러그인 |
| Codex CLI | OpenAI | API 비용 발생 | 터미널 자율 에이전트 | 파일 수정·명령 실행 자동화 |
| Copilot | GitHub/MS | 개인 $10/월 (학생 무료) | IDE 깊은 통합, PR 에이전트 | 자동완성, Chat, Coding Agent |
| Gemini | Google | 무료 티어 제공 | 1M 컨텍스트, 멀티모달 | 코드 실행 도구 내장 |

#### 시나리오별 권장 조합

**개인 학습 / 사이드 프로젝트**
- Gemini (무료 티어) + GitHub Copilot (무료 플랜) + Continue 확장

**팀 개발 / 스타트업**
- GitHub Copilot Business + Claude API (코드 리뷰 자동화) + Codex CLI

**엔터프라이즈 / 사내망**
- GitHub Copilot Enterprise + OpenAI API (사내 게이트웨이 경유) + MCP 서버

---

### A8. 개인 AI 개발 환경 통합 설정

#### 공통 환경 변수 관리 (`.env` 파일 활용)

프로젝트 루트에 `.env` 파일 생성 (`.gitignore` 에 추가 필수):

```bash
# .env — 절대 Git 에 커밋하지 마세요!
OPENAI_API_KEY=sk-your-openai-key
ANTHROPIC_API_KEY=sk-ant-your-anthropic-key
GEMINI_API_KEY=AIza-your-gemini-key
XAI_API_KEY=xai-your-xai-key
GITHUB_TOKEN=ghp-your-github-token
```

```bash
# .gitignore 에 추가
echo ".env" >> .gitignore
echo ".env.local" >> .gitignore
```

**Python 에서 `.env` 로드**:

```bash
pip install python-dotenv
```

```python
from dotenv import load_dotenv
import os

load_dotenv()  # .env 파일 자동 로드

openai_key = os.getenv("OPENAI_API_KEY")
anthropic_key = os.getenv("ANTHROPIC_API_KEY")
```

#### Continue 확장으로 여러 AI 모델 통합 (`~/.continue/config.json`)

VS Code 에서 **Continue** 확장 하나로 모든 AI 모델을 전환하며 사용할 수 있습니다.

```bash
# Continue 확장 설치
code --install-extension Continue.continue
```

```json
{
  "models": [
    {
      "title": "GPT-4o",
      "provider": "openai",
      "model": "gpt-4o",
      "apiKey": "sk-your-openai-key"
    },
    {
      "title": "Claude Opus 4.5",
      "provider": "anthropic",
      "model": "claude-opus-4-5",
      "apiKey": "sk-ant-your-anthropic-key"
    },
    {
      "title": "Gemini 2.5 Pro",
      "provider": "gemini",
      "model": "gemini-2.5-pro",
      "apiKey": "AIza-your-gemini-key"
    },
    {
      "title": "Grok 3",
      "provider": "openai",
      "model": "grok-3",
      "apiBase": "https://api.x.ai/v1",
      "apiKey": "xai-your-xai-key"
    }
  ],
  "tabAutocompleteModel": {
    "title": "Copilot (자동완성)",
    "provider": "github",
    "model": "copilot"
  },
  "contextProviders": [
    { "name": "code" },
    { "name": "docs" },
    { "name": "diff" },
    { "name": "terminal" },
    { "name": "problems" },
    { "name": "folder" },
    { "name": "codebase" }
  ],
  "slashCommands": [
    { "name": "edit", "description": "코드 수정" },
    { "name": "comment", "description": "주석 추가" },
    { "name": "tests", "description": "테스트 생성" },
    { "name": "share", "description": "대화 공유" }
  ]
}
```

#### AI 코딩 워크플로우 권장 순서

```
1. 기능 설계
   └─ ChatGPT / Claude → 아키텍처 설계, 기술 선택 논의

2. 코드 작성
   └─ GitHub Copilot → IDE 내 실시간 자동완성·인라인 생성

3. 코드 리뷰
   └─ Claude / Gemini → 코드 품질·보안·성능 검토

4. 자동화 작업 (반복적 구현)
   └─ Codex CLI / Copilot Agent → 파일 수정, 테스트 작성, 리팩터링 자동화

5. 배포·운영 질의
   └─ Grok / Gemini → 최신 기술 동향, 실시간 문서 검색
```

---

## 📺 관련 유튜브 동영상

아래 링크를 클릭하면 유튜브에서 관련 동영상을 검색할 수 있습니다.

- 🔍 [개발환경 기본 설정 검색](https://www.youtube.com/results?search_query=개발환경+기본+설정)
- 🔍 [PowerShell 개발환경 구성 검색](https://www.youtube.com/results?search_query=PowerShell+개발환경+구성)
- 🔍 [WSL2 개발환경 설정 검색](https://www.youtube.com/results?search_query=WSL2+개발환경+설정)
- 🔍 [Git GitHub 사용법 검색](https://www.youtube.com/results?search_query=Git+GitHub+사용법)
- 🔍 [Node.js Python Java 개발환경 검색](https://www.youtube.com/results?search_query=Node.js+Python+Java+개발환경)
- 🔍 [GitHub Copilot AI 코딩 검색](https://www.youtube.com/results?search_query=GitHub+Copilot+AI+코딩)
