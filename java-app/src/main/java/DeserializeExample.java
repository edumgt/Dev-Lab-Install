import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.sample.demo.Person;

public class DeserializeExample {
    public static void main(String[] args) {
        try (
            FileInputStream fis = new FileInputStream("./person.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            Person person = (Person) ois.readObject();
            System.out.println("✅ 역직렬화된 객체 정보: " + person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

