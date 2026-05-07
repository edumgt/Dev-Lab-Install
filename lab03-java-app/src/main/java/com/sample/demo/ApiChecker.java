package com.sample.demo;

import java.io.IOException;
import java.net.*;
// import java.net.URL;

public class ApiChecker {

    private static final String TARGET_URL = "https://www.data.go.kr";
    private static final int INTERVAL_MS = 6000;  

    public static void main(String[] args) {
        while (true) {
            try {
                checkApi();
                Thread.sleep(INTERVAL_MS);
            } catch (InterruptedException e) {
                System.out.println("Interrupted. Exiting.");
                break;
            }
        }
    }

    private static void checkApi() {
        try {
            URL url = new URL(TARGET_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);  // 5초 타임아웃
            conn.setReadTimeout(5000);
            int responseCode = conn.getResponseCode();

            if (responseCode >= 200 && responseCode < 300) {
                System.out.println("✅ API is UP! Response code: " + responseCode);
            } else {
                System.out.println("⚠️ API returned error. Response code: " + responseCode);
            }

            conn.disconnect();
        } catch (IOException e) {
            System.out.println("❌ Failed to reach API: " + e.getMessage());
        }
    }
}

