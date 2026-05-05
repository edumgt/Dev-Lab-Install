package com.sample.demo;

public class CountingThread extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("카운트: " + i);
            try {
                Thread.sleep(1000); // 1초 멈춤
            } catch (InterruptedException e) {
                System.out.println("스레드 중단됨");
            }
        }
    }
}
