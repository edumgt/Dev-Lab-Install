# Java Basic

이 저장소는 **Java 기초 예제**를 다루며, 초급 개발자가 바로 실습할 수 있도록 개발 환경 구축 가이드를 함께 제공합니다.

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

### Playwright 캡처
![WSL 설치 페이지](browser:/tmp/codex_browser_invocations/2a3de15839acebbe/artifacts/images/wsl-install-page.png)

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

### 권장 가상환경 생성
```bash
python -m venv .venv
# PowerShell
.\\.venv\\Scripts\\Activate.ps1
# macOS/Linux
source .venv/bin/activate
```

### 공식 사이트
- https://www.python.org/downloads/

### Playwright 캡처
![Python 다운로드 페이지](browser:/tmp/codex_browser_invocations/28626ebf368157fd/artifacts/images/python-download-page.png)

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

### npm 초기 설정 예시
```bash
npm config set init-author-name "your-name"
npm config set init-license "MIT"
```

### 공식 사이트
- https://nodejs.org/en/download

### Playwright 캡처
![Node.js 다운로드 페이지](browser:/tmp/codex_browser_invocations/1d42d9efd4a2723f/artifacts/images/nodejs-download-page.png)

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

### Playwright 캡처
![Docker Desktop 다운로드 페이지](browser:/tmp/codex_browser_invocations/02d9c6bc16ba15eb/artifacts/images/docker-desktop-page.png)

---

## 5) Java 최신 버전 구축

### 설치 순서
1. Oracle Java Downloads 또는 OpenJDK 배포판에서 최신 LTS 선택
2. 운영체제에 맞는 설치 파일 다운로드
3. 설치 후 환경 변수 확인
4. 버전 확인

```bash
java --version
javac --version
```

### 공식 사이트
- https://www.oracle.com/java/technologies/downloads/

### Playwright 캡처
![Java 다운로드 페이지](browser:/tmp/codex_browser_invocations/101c1236e0b069a6/artifacts/images/java-oracle-download-page.png)

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

