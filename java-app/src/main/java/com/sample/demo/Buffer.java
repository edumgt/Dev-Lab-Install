package com.sample.demo;

public class Buffer {
    private int data;
    private boolean hasData = false;

    public synchronized void produce(int value) {
        while (hasData) {
            try {
                wait(); // 소비자가 소비할 때까지 대기
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        data = value;
        hasData = true;
        System.out.println("생산: " + data);
        notifyAll(); // 소비자들을 모두 깨운다
    }

    public synchronized int consume() {
        while (!hasData) {
            try {
                wait(); // 생산자가 데이터를 넣을 때까지 대기
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        hasData = false;
        System.out.println("소비: " + data);
        notifyAll(); // 생산자를 깨운다
        return data;
    }
}
