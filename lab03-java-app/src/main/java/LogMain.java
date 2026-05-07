import java.util.logging.Logger;

public class LogMain {
    private static final Logger logger = Logger.getLogger(LogMain.class.getName());

    public static void main(String[] args) {
        

        logger.info("INFO 로그입니다");
        logger.warning("경고 로그입니다");
        logger.severe("에러 로그입니다");
    }
}
