import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class KoreanWordFileProcessor {
    // 한글 음절 유니코드 범위: 가(0xAC00) ~ 힣(0xD7A3)
    private static String getRandomHangulWord(int length) {
        Random random = new Random();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = (char) (0xAC00 + random.nextInt(0xD7A3 - 0xAC00 + 1));
            word.append(ch);
        }
        return word.toString();
    }

    public static void main(String[] args) {
        String originalFile = "./generate_words.txt";
        String sortedFile = "./sorted_words.txt"; 

        List<String> words = new ArrayList<>();

        // 1. 한글 단어 20개 생성 (2~3글자 랜덤)
        for (int i = 0; i < 20; i++) {
            int len = 2 + new Random().nextInt(2); // 2글자 또는 3글자
            words.add(getRandomHangulWord(len));
        }

        // 2. 파일로 저장 (쉼표로 구분)
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(originalFile), StandardCharsets.UTF_8))) {
            writer.write(String.join(",", words));
            System.out.println("✅ 파일 생성 완료: " + originalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. 정렬 및 복제 파일 생성
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(originalFile), StandardCharsets.UTF_8))) {

            String line = reader.readLine();
            if (line != null) {
                List<String> sortedWords = new ArrayList<>(Arrays.asList(line.split(",")));
                // 가나다 순 정렬 (자연 정렬)
                Collections.sort(sortedWords);

                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(sortedFile), StandardCharsets.UTF_8))) {
                    writer.write(String.join(",", sortedWords));
                    System.out.println("✅ 정렬 후 복제 완료: " + sortedFile);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

