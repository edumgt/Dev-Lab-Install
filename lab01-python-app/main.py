# FastAPI 프레임워크에서 FastAPI 클래스를 임포트합니다.
# FastAPI는 Python 기반의 현대적이고 빠른 웹 API 프레임워크입니다.
from fastapi import FastAPI

# FastAPI 애플리케이션 인스턴스를 생성합니다.
# 이 객체(app)가 모든 HTTP 라우팅과 미들웨어의 진입점이 됩니다.
app = FastAPI()


# @app.get("/") 데코레이터: HTTP GET 메서드로 "/" 경로에 요청이 들어올 때
# 아래 hello() 함수를 실행하도록 라우트를 등록합니다.
@app.get("/")
def hello():
    # 딕셔너리를 반환하면 FastAPI가 자동으로 JSON 형식으로 직렬화하여 응답합니다.
    # 클라이언트는 {"message": "Hello, World! (Python + FastAPI)"} 라는 JSON을 받게 됩니다.
    return {"message": "Hello, World! (Python + FastAPI)"}


# @app.get("/health") 데코레이터: HTTP GET 메서드로 "/health" 경로에 요청이 들어올 때
# 아래 health() 함수를 실행하도록 라우트를 등록합니다.
# 이 엔드포인트는 컨테이너/쿠버네티스 환경에서 헬스 체크(livenessProbe, readinessProbe)에 사용됩니다.
@app.get("/health")
def health():
    # 서비스가 정상 동작 중임을 나타내는 {"status": "OK"} JSON을 반환합니다.
    # 쿠버네티스는 이 응답의 HTTP 200 상태 코드를 확인하여 Pod 상태를 판단합니다.
    return {"status": "OK"}
