import java.util.Base64;

public class Base64Example {
    public static void main(String[] args) {
        String plainText = "Hello Base64 Encoding!";

        // 인코딩 (문자열 → Base64)
        String encoded = Base64.getEncoder().encodeToString(plainText.getBytes());
        System.out.println("Encoded : " + encoded);

        // 디코딩 (Base64 → 문자열)
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        String decoded = new String(decodedBytes);
        System.out.println("Decoded : " + decoded);
    }
}
