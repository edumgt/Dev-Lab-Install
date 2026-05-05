import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RealKoreanWordsExample {
    public static void main(String[] args) {
        String[] realWords = {
            "사과", "바나나", "학교", "연필", "컴퓨터", "자동차", "전화기", "사자", "호랑이", "물고기",
            "달력", "바다", "하늘", "친구", "음악", "사진", "요리", "강아지", "고양이", "영화"
        };

        String originalFile = "real_words.txt"; 
        String sortedFile = "sorted_words.txt";

        // 1. 무작위 섞고 앞에서 20개 선택
        List<String> wordList = new ArrayList<>(Arrays.asList(realWords));
        Collections.shuffle(wordList);
        List<String> selectedWords = wordList.subList(0, 20);

        // 2. 저장
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(originalFile), StandardCharsets.UTF_8))) {
            writer.write(String.join(",", selectedWords));
            System.out.println("✅ 원본 파일 저장 완료: " + originalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. 가나다 정렬 및 복사 저장
        List<String> sortedWords = new ArrayList<>(selectedWords);
        Collections.sort(sortedWords);
 
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(sortedFile), StandardCharsets.UTF_8))) {
            writer.write(String.join(",", sortedWords));
            System.out.println("✅ 정렬 파일 저장 완료: " + sortedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
