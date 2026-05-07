import java.io.*; // ✅ Method Area: 클래스 로딩 시 java.io 패키지의 클래스 메타데이터가 로드됨
import java.net.HttpURLConnection; // ✅ Method Area
import java.net.URL; // ✅ Method Area

public class ApiPostExample { // ✅ Method Area: 클래스 로딩 시 클래스 구조(메서드, 변수 등) 메타정보 저장

    // ✅ Method Area: static 변수 → 클래스 영역에 저장됨 (모든 스레드 공유)
    private static final String TARGET_URL = "https://reqres.in/api/users";

    public static void main(String[] args) { // ✅ Method Area: main 메서드 자체는 클래스 영역에 로드됨
                                             // ✅ Stack: main 호출 시 Stack Frame 생성, args 변수 저장
        try {
            // ✅ Stack: 지역변수 jsonRequest (참조 변수) 저장
            // ✅ Heap: new String 객체가 생성되어 저장됨
            String jsonRequest = "{ \"name\": \"Kim\", \"job\": \"Developer\" }";

            // ✅ Stack: response 변수 (참조 변수) 저장
            // ✅ Heap: sendPostRequest() 내부에서 반환된 String 객체가 저장됨
            String response = sendPostRequest(TARGET_URL, jsonRequest);

            // ✅ PC Register: println 실행 시 현재 실행 위치 추적
            System.out.println("📌 서버 응답 JSON:");
            System.out.println(response);

        } catch (IOException e) { // ✅ Stack: e 참조변수 저장
                                  // ✅ Heap: IOException 객체 생성
            System.out.println("❌ API 요청 실패: " + e.getMessage());
        }
    }

    // ✅ Method Area: sendPostRequest 메서드 자체는 메서드 영역에 로드됨
    private static String sendPostRequest(String targetUrl, String jsonInput) throws IOException {
        // ✅ Stack: targetUrl, jsonInput 참조 변수 저장
        // ✅ Heap: URL 객체 생성됨
        URL url = new URL(targetUrl);
        

        // ✅ Heap: HttpURLConnection 객체 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // ✅ Method Area: setRequestMethod, setRequestProperty 등의 메서드 정의는 이미 로드됨
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        // ✅ try-with-resources 구문
        // os 참조변수는 Stack에 저장, 실제 OutputStream 객체는 Heap에 존재
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8"); // ✅ Stack: input 참조
                                                        // ✅ Heap: byte[] 배열 객체 생성
            os.write(input, 0, input.length);
        }

        // ✅ Stack: responseCode 변수 저장
        int responseCode = conn.getResponseCode();

        // ✅ Stack: is 참조변수 저장, 실제 InputStream 객체는 Heap에 존재
        InputStream is = (responseCode >= 200 && responseCode < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        // ✅ Stack: br 참조, 실제 BufferedReader 객체는 Heap에 저장
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));

        // ✅ Stack: response 참조, 실제 StringBuilder 객체는 Heap에 저장
        StringBuilder response = new StringBuilder();

        // ✅ Stack: line 참조변수 저장, Heap에는 문자열(String) 객체가 할당됨
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line.trim()); // ✅ Heap: StringBuilder가 내부적으로 새로운 문자열 버퍼 생성
        }
        br.close();

        conn.disconnect(); // ✅ 네이티브 메서드 호출 시 Native Method Stack 활용

        // ✅ 반환값: Heap에 있는 String 객체를 리턴, Stack에서는 참조 변수(response)만 사용됨
        return response.toString();
    }
}
