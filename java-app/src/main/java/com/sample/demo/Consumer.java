package com.sample.demo;
public class Consumer extends Thread {
    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 1; i <= 15; i++) {
            buffer.consume();
            try {
                Thread.sleep(800); // 소비 간 간격
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}