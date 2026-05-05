import java.io.*;

public class FileReadUTF8 {
    public static void main(String[] args) {
        // 상위 디렉토리의 파일 경로
        String filePath = "./test.txt";

        try (
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader reader = new BufferedReader(isr)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // 한 줄씩 출력
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류 발생: " + e.getMessage());
        }
    }
}
