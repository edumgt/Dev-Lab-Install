import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;

public class LambdaExample {

    public static void main(String[] args) {
        
        // 1. Function<T, R>: 입력 T → 출력 R
        Function<Integer, Integer> square = x -> x * x;
        System.out.println("Square of 5: " + square.apply(5)); // 25

        // 2. Predicate<T>: 조건 판별 (true/false)
        Predicate<String> isLongWord = word -> word.length() > 5;
        System.out.println("Is 'hello' a long word? " + isLongWord.test("hello")); // false

        // 3. Consumer<T>: 받아서 소비(출력 등)만 하고 반환값 없음
        Consumer<String> printer = message -> System.out.println(">> " + message);
        printer.accept("람다식 연습 중입니다.");

        // 4. Runnable: 매개변수 없고 반환도 없음
        Runnable sayHello = () -> System.out.println("Hello from Runnable!");
        sayHello.run();
    }
}
