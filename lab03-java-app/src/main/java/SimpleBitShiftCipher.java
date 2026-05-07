public class SimpleBitShiftCipher {

    // 암호화 함수
    public static int encrypt(int input, int key) {
        // key로 XOR, 왼쪽으로 3비트 shift, 다시 key로 XOR
        int result = (input ^ key);
        result = (result << 3) | (result >>> (32 - 3));  // 32비트 기준 left rotate
        result = result ^ key;
        return result;
    }

    // 복호화 함수
    public static int decrypt(int encrypted, int key) {
        // key로 XOR, 오른쪽으로 3비트 shift, 다시 key로 XOR
        int result = encrypted ^ key;
        result = (result >>> 3) | (result << (32 - 3)); // right rotate
        result = result ^ key;
        return result;
    }

    public static void main(String[] args) {
        int original = 12000;
        int key = 987654321;

        int encrypted = encrypt(original, key);
        int decrypted = decrypt(encrypted, key);

        System.out.println("원본 값: " + original);
        System.out.println("암호화된 값: " + encrypted);
        System.out.println("복호화된 값: " + decrypted);
    }
}
