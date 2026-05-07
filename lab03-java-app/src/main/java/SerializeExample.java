import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import com.sample.demo.Person;

public class SerializeExample {
    public static void main(String[] args) {
        Person person = new Person("홍길동", 30);

        try (
            FileOutputStream fos = new FileOutputStream("./person.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(person);
            System.out.println("✅ 객체가 person.ser 파일에 직렬화됨");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

