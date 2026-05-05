package com.sample.demo;

import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L; // 버전 관리용

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "이름: " + name + ", 나이: " + age;
    }
}

