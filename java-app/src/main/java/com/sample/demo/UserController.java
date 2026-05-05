package com.sample.demo;

public class UserController extends BaseController {
    public String helloUser() {
        logRequest("/user/hello");
        return commonResponse("안녕하세요, 사용자님!");
    }
}

