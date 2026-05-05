import java.util.Scanner;
import java.util.Base64;

public class ScannerExample {
    public static void main(String[] args) {
        // Scanner 객체 생성 (System.in을 통해 콘솔 입력 받음)
        Scanner scanner = new Scanner(System.in);

        // 문자열 입력
        System.out.print("PlainText를 입력하세요: ");
        String plainText = scanner.nextLine();

        // 인코딩 (문자열 → Base64)
        String encoded = Base64.getEncoder().encodeToString(plainText.getBytes());
        System.out.println("Encoded : " + encoded);

        // 디코딩 (Base64 → 문자열)
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        String decoded = new String(decodedBytes);
        System.out.println("Decoded 1 : " + decoded);

        String cipherText = "tb/H2LmwsPoguem1zrvqwMwguLa4o7Dt";
        byte[] decodedBytes1 = Base64.getDecoder().decode(cipherText);
        String decoded1 = new String(decodedBytes1);
        System.out.println("Decoded 2 : " + decoded1);

        // Scanner 닫기
        scanner.close();
    }
}
