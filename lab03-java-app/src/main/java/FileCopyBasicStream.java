import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyBasicStream {
    public static void main(String[] args) {
        String sourceFile = "./test.txt";     // 원본 파일
        String destFile = "./test1.txt";      // 복사할 대상 파일

        try (
            FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(destFile)
        ) { 
            byte[] buffer = new byte[1024]; // 1KB 버퍼
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead); // 읽은 만큼만 쓰기
            }

            System.out.println("✅ 파일 복사 완료: " + destFile);
        } catch (IOException e) {
            System.out.println("❌ 파일 복사 중 오류 발생: " + e.getMessage());
        }
    }  
}
