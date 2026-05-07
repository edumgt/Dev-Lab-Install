# Docker 설치 및 WSL2/Windows 호환 가이드

---

## 1) 설치 목적

- Windows 환경에서 Docker Desktop + WSL2 기반으로 안정적으로 컨테이너 개발
- Windows PowerShell, WSL(Ubuntu) 양쪽에서 동일한 Docker 엔진 사용
- Docker Desktop UI가 열리지 않는 경우를 대비한 PowerShell 점검/복구 절차 제공

---

## 2) 설치 권장 구성

### 권장 아키텍처

- Host OS: Windows 10/11
- 가상화: BIOS/UEFI에서 Virtualization(AMD-V/Intel VT-x) 활성화
- Windows 기능: WSL, Virtual Machine Platform (필수), Hyper-V(권장)
- Docker: Docker Desktop (WSL2 backend)

---

## 3) Docker Desktop 설치 (Windows)

1. Docker Desktop 다운로드 및 설치
   - https://www.docker.com/products/docker-desktop/
2. 설치 옵션에서 **Use WSL 2 instead of Hyper-V**(또는 WSL2 연동 옵션) 활성화
3. 설치 완료 후 재부팅
4. Docker Desktop 실행 후 초기 설정 완료

버전 확인:

```powershell
docker --version
docker compose version
docker context ls
```

동작 확인:

```powershell
docker run --rm hello-world
```

---

## 4) WSL2 연동 및 Windows/WSL 호환 설정

### 4-1) WSL2 상태 확인 (PowerShell)

```powershell
wsl --status
wsl -l -v
```

- 기본 버전이 2인지 확인
- Docker 작업용 배포판(예: Ubuntu)이 `VERSION 2`인지 확인

### 4-2) Docker Desktop의 WSL Integration 설정

1. Docker Desktop → **Settings** → **Resources** → **WSL Integration**
2. `Enable integration with my default WSL distro` 활성화
3. 사용하는 배포판(Ubuntu 등) 토글 활성화
4. **Apply & Restart**

### 4-3) WSL 터미널에서 Docker 확인

```bash
docker --version
docker compose version
docker run --rm hello-world
```

### 4-4) Windows PowerShell ↔ WSL 공통 사용 팁

- 프로젝트 루트는 가능하면 WSL 파일시스템(`~/project`)에 두는 것을 권장
- Windows 경로를 마운트할 때는 `/mnt/c/...` 경로 사용
- 볼륨 마운트 시 공백이 있는 경로는 반드시 따옴표 사용

예시:

```powershell
docker run --rm -v "${PWD}:/app" -w /app node:20-alpine node -v
```

```bash
docker run --rm -v "$PWD:/app" -w /app python:3.12 python --version
```

---

## 5) Windows에서 Docker Desktop 화면이 안 뜰 때 (PowerShell)

> 아래 절차는 **관리자 권한 PowerShell** 기준입니다.

### 5-1) 프로세스/WSL 강제 정리 후 재실행

```powershell
# Docker 관련 프로세스 종료 (존재하는 프로세스만 종료)
foreach ($p in "Docker Desktop","com.docker.backend","com.docker.proxy") {
    Get-Process -Name $p -ErrorAction SilentlyContinue | Stop-Process -Force
}

# WSL 종료
wsl --shutdown

# Docker 서비스 재시작 (환경별 서비스명 차이 대응)
$dockerSvc = Get-Service | Where-Object { $_.Name -like "*docker*" -or $_.DisplayName -like "*docker*" }
$dockerSvc | ForEach-Object { Restart-Service -Name $_.Name -ErrorAction SilentlyContinue }

# Docker Desktop 재실행
Start-Process "$Env:ProgramFiles\Docker\Docker\Docker Desktop.exe"
```

### 5-2) Windows 기능(WSL/가상화) 활성 상태 점검

```powershell
Get-WindowsOptionalFeature -Online -FeatureName Microsoft-Windows-Subsystem-Linux,VirtualMachinePlatform,Microsoft-Hyper-V-All |
    Select-Object FeatureName, State
```

필요 시 활성화:

```powershell
dism /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
dism /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
dism /online /enable-feature /featurename:Microsoft-Hyper-V-All /all /norestart
```

활성화 후 재부팅:

```powershell
shutdown /r /t 0
```

### 5-3) WSL 커널/배포판 상태 점검

```powershell
wsl --update
wsl --status
wsl -l -v
```

배포판이 멈춰 있으면:

```powershell
wsl --terminate Ubuntu
```

### 5-4) CLI 동작 여부 우선 확인 (UI 장애 분리)

```powershell
docker version
docker info
docker run --rm hello-world
```

- CLI가 정상인데 UI만 미출력인 경우: Docker Desktop 재설치 또는 설정 초기화 고려
- CLI도 비정상이면 WSL/Windows 기능/서비스 상태부터 우선 복구

### 5-5) 로그 확인 (문제 원인 추적)

```powershell
Get-Content "$Env:AppData\Docker\log\host\com.docker.backend.exe.log" -Tail 200
Get-Content "$Env:LocalAppData\Docker\log.txt" -Tail 200
```

### 5-6) 최후 조치 (설정 캐시 백업 후 초기화)

```powershell
$ts = Get-Date -Format "yyyyMMdd-HHmmss"
Rename-Item "$Env:AppData\Docker" "$Env:AppData\Docker.bak.$ts" -ErrorAction SilentlyContinue
Rename-Item "$Env:LocalAppData\Docker" "$Env:LocalAppData\Docker.bak.$ts" -ErrorAction SilentlyContinue
```

이후 Docker Desktop을 다시 설치/실행합니다.

---

## 6) 최소 점검 체크리스트

- [ ] `wsl -l -v`에서 사용 배포판이 VERSION 2
- [ ] Docker Desktop WSL Integration 활성화
- [ ] PowerShell/WSL 모두에서 `docker run --rm hello-world` 성공
- [ ] Docker Desktop UI 미출력 시 5장 순서대로 점검 완료
