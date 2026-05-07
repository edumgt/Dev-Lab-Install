package com.sample.demo;
public class Example7 {
    public static void main(String[] args) {
        String name = "홍길동";   // ✅ Heap의 String Pool에 "홍길동" 저장, name은 참조
        if ( name.equals("홍길동")) System.out.println("1");  // 🔸 NPE(NullPointerException) 가능성 있음
        if ( "홍길동".equals(name)) System.out.println("2");  // 🔸 NPE 발생하지 않음 → 더 안전한 방식
    }
}
/*
 * 
 * name.equals("홍길동")
→ name이 null이면 NPE 발생

"홍길동".equals(name)
→ "홍길동"은 리터럴이므로 절대 null 아님 → 안전한 비교 방식
 */