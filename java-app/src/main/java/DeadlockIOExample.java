import java.io.*;

public class DeadlockIOExample {
    public static void main(String[] args) {
        try {
            // 1️⃣ 기반 스트림 (바이트 단위 입력)
            FileInputStream fis = new FileInputStream("C:\\edumgt-java-education\\java-education-001\\resources\\test.txt");

            // 2️⃣ 문자 변환 데코레이터
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

            // 3️⃣ 라인 단위 읽기 기능 추가
            BufferedReader br = new BufferedReader(isr);

            // 4️⃣ 두 개의 스레드에서 동일 인스턴스를 동시에 접근
            Runnable task = () -> {
                try {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(Thread.currentThread().getName() + " → " + line);
                        Thread.sleep(100); // 읽기 지연 (동시성 충돌 유도)
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            };

            Thread t1 = new Thread(task, "Reader-1");
            Thread t2 = new Thread(task, "Reader-2");

            // 5️⃣ 스레드 시작 (동일한 BufferedReader 사용)
            t1.start();
            t2.start();

            // join으로 메인스레드가 두 스레드 기다리게 함
            t1.join();
            t2.join();

            // 6️⃣ close()故意로 호출 안함 (자원 미해제)
            System.out.println("\n⚠ close()를 하지 않아 리소스가 해제되지 않았습니다.");
            System.out.println("⚠ 두 스레드가 동일 스트림을 사용 → 동기화 충돌로 블로킹/교착 가능");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
