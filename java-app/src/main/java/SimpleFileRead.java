import java.io.*;
import java.nio.charset.Charset;

public class SimpleFileRead {
    public static void main(String[] args) {
        try {
            // 파일 열기
            BufferedReader br = new BufferedReader(
                    new FileReader("C:\\edumgt-java-education\\java-education-001\\resources\\test.txt",
                            Charset.forName("UTF-8")));

            System.out.println(" Charset.defaultCharset() -- " + Charset.forName("UTF-8"));

            // 한 줄씩 읽기
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close(); // 자원 해제
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
