package com.sample.demo;
public class Example1 {
    public static void main(String[] args) {
        // byte 타입 변수 byteVal 선언 및 초기화 (값: 10)
        byte byteVal = 10;

        // byte → int : 작은 타입을 큰 타입으로 변환 (명시적 캐스팅 불필요하지만, 예시로 (int) 사용)
        int intVal = (int) byteVal;
        System.out.println("intVal:" + intVal); // 출력: intVal:10

        // int 타입 변수 intVal2 선언 및 초기화 (값: 100)
        int intVal2 = 100000;

        // int → byte : 큰 타입을 작은 타입으로 변환 (명시적 캐스팅 필요, 데이터 손실 가능)
        byte byteVal1 = (byte) intVal2;
        System.out.println("byteVal1:" + byteVal1); // 출력: byteVal1:100 (100은 byte 범위 안에 있음)

        // char 타입 변수 charVal 선언 및 초기화 (문자 '가')
        char charVal = '가';

        // char → int : 유니코드 코드 포인트 값으로 변환
        intVal = charVal;
        System.out.println("intVal:" + intVal); // 출력: intVal:44032 ('가'의 유니코드 값)

        // int 타입 변수 intVal1 선언 및 초기화 (값: 500)
        int intVal1 = 500;

        // int → long : 작은 타입을 큰 타입으로 자동 변환 (묵시적 형변환, widening)
        long longVal = intVal1;
        System.out.println("longVal:" + longVal); // 출력: longVal:500
    }
}
