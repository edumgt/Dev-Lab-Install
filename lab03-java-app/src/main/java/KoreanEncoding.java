// import java.nio.charset.Charset;

public class KoreanEncoding {
    public static void main(String[] args) throws Exception {
        String text = "한글";

        byte[] eucKr = text.getBytes("EUC-KR");
        byte[] cp949 = text.getBytes("MS949");
        byte[] utf8  = text.getBytes("UTF-8");

        System.out.println("EUC-KR : " + bytesToHex(eucKr));
        System.out.println("CP949  : " + bytesToHex(cp949));
        System.out.println("UTF-8  : " + bytesToHex(utf8));
    }

    static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
