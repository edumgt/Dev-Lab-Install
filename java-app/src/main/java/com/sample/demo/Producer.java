package com.sample.demo;

public class Producer extends Thread {
    private final Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for (int i = 1; i <= 15; i++) {
            buffer.produce(i);
            try {
                Thread.sleep(500); // 생산 간 간격
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}


