package com.sample.demo;

public class AdminController extends BaseController {
    public String helloAdmin() {
        logRequest("/admin/hello");
        return commonResponse("안녕하세요, 관리자님!");
    }
}

