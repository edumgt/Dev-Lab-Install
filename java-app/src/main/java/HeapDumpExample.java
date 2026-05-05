import java.util.ArrayList;
import java.util.List;

public class HeapDumpExample {
    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Person> people = new ArrayList<>();

        // 100만 명의 Person 객체 생성
        for (int i = 0; i < 1_000_000; i++) {
            people.add(new Person("Person" + i, i));
        }

        System.out.println("생성 완료! 이제 heap dump를 떠보세요.");
        
        // 프로그램을 멈추지 않고 계속 유지
        Thread.sleep(10 * 60 * 1000);  // 10분 동안 대기
    }
}
