package com.sample.demo;
public class Example6 {
    public static void main(String[] args) {
        outer: 
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 3; j++) {
                if (j == 2) {
                    System.out.println("j==2 → 바깥 loop 다음으로 넘어감");
                    continue; // 안쪽 loop를 중단하고 바깥 loop 다음 회차로 이동
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }
    }
}
