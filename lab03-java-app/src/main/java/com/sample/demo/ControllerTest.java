package com.sample.demo;
public class ControllerTest {
    public static void main(String[] args) {
        UserController userController = new UserController();
        AdminController adminController = new AdminController();

        // 사용자 요청 시뮬레이션
        System.out.println(userController.helloUser());

        // 관리자 요청 시뮬레이션
        System.out.println(adminController.helloAdmin());
    }
}
