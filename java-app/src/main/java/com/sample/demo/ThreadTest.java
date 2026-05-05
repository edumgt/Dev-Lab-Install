package com.sample.demo;
public class ThreadTest {
    public static void main(String[] args) {
        CountingThread t = new CountingThread();
        t.start(); // 카운트가 1초 간격으로 출력됩니다
    }
}
