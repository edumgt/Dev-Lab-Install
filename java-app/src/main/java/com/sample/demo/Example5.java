package com.sample.demo;
import java.util.UUID;

public class Example5 {
    public static void main(String[] args) {
        while (true) {
            UUID uuid = UUID.randomUUID();
            String uuidStr = uuid.toString();

            // 앞 2자리 추출
            String prefix = uuidStr.substring(0, 2);

            // 숫자 여부 확인 (00~99)
            if (prefix.matches("\\d{2}")) {
                System.out.println("조건 충족! UUID = " + uuidStr);
                break; // while 탈출
            } else {
                System.out.println("조건 불충족: " + uuidStr);
            }
        }

        System.out.println("프로그램 종료");
    }

}
