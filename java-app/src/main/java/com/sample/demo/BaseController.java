package com.sample.demo;

public abstract class BaseController {
    protected void logRequest(String endpoint) {
        System.out.println("📌 요청 도착: " + endpoint);
    }

    protected String commonResponse(String message) {
        return "🟢 공통 응답: " + message;
    }
}
