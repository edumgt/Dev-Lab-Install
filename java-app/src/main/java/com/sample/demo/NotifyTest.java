package com.sample.demo;
public class NotifyTest {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Producer p1 = new Producer(buffer);
        Consumer c1 = new Consumer(buffer);
        Consumer c2 = new Consumer(buffer);

        p1.start();
        c1.start();
        c2.start(); // 두 소비자가 함께 경쟁
    }
}
