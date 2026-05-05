import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class MethodRefExample {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Java", "Python", "Go");

        // System.out::println
        names.forEach(System.out::println);

        // String::toUpperCase
        List<String> upper = names.stream()
                                  .map(String::toUpperCase)
                                  .collect(Collectors.toList());
        System.out.println(upper);

        // Math::max
        BinaryOperator<Integer> maxOp = Math::max;
        System.out.println(maxOp.apply(10, 20));  // 20

        // 생성자 참조
        Supplier<List<String>> listSupplier = ArrayList::new;
        List<String> newList = listSupplier.get();
        newList.add("Hello");
        System.out.println(newList);
    }
}
