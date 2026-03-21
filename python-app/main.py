from fastapi import FastAPI

app = FastAPI()


@app.get("/")
def hello():
    return {"message": "Hello, World! (Python + FastAPI)"}


@app.get("/health")
def health():
    return {"status": "OK"}
