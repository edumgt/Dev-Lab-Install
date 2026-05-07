import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostJsonExample {
    public static void main(String[] args) {
        try {
            String targetUrl = "https://json_server/api";
            URL url = new URL(targetUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 설정
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            // POST 파라미터 구성
            String params = "a=1" +
                    "&b=2";
            // 필요시 추가 파라미터 붙이기

            // 요청 바디에 쓰기
            OutputStream os = conn.getOutputStream();
            os.write(params.getBytes());
            os.flush();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // HTTP OK
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("JSON Response:");
                System.out.println(response.toString());
            } else {
                System.out.println("Failed: HTTP error code : " + responseCode);
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
