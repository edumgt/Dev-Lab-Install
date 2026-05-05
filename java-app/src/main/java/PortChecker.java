import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PortChecker {

    private static final String HOST = "IP_Address";
    private static final int PORT = 15000;
    private static final int INTERVAL_MS = 10000; 

    public static void main(String[] args) {
        while (true) {
            try {
                checkPort();
                Thread.sleep(INTERVAL_MS);
            } catch (InterruptedException e) {
                System.out.println("Interrupted. Exiting.");
                break;
            }
        }
    }

    private static void checkPort() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(HOST, PORT), 5000);  // 5초 타임아웃
            System.out.println("✅ Port " + PORT + " on " + HOST + " is OPEN!");
        } catch (IOException e) {
            System.out.println("❌ Port " + PORT + " on " + HOST + " is CLOSED or unreachable: " + e.getMessage());
        }
    }
}
