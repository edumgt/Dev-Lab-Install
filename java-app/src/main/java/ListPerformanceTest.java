import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListPerformanceTest {
    public static void main(String[] args) {
        int count = 100_000;

        // ArrayList 테스트
        List<Integer> arrayList = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            arrayList.add(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("ArrayList 끝 추가: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        arrayList.add(0, -1);
        end = System.currentTimeMillis();
        System.out.println("ArrayList 앞 삽입: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < arrayList.size(); i++) {
            int x = arrayList.get(i);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList 순차 접근: " + (end - start) + " ms");

        // LinkedList 테스트
        List<Integer> linkedList = new LinkedList<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            linkedList.add(i);
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList 끝 추가: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        linkedList.add(0, -1);
        end = System.currentTimeMillis();
        System.out.println("LinkedList 앞 삽입: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < linkedList.size(); i++) {
            int x = linkedList.get(i);
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList 순차 접근: " + (end - start) + " ms");
    }
}
