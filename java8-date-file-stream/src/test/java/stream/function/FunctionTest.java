package stream.function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/7 10:47
 * @see java.util.function.Function
 */
public class FunctionTest {

    @Test
    public void test() {
        Function f = new Function() {
            @Override
            public Object apply(Object o) {
                return null;
            }
        };
    }

    @Test
    public void test1() {
        // Optional.ofNullable(1)
        /*Stream<String> stringStream = */
        Arrays.asList(1, 3, 4)
                .stream()
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) {
                        return integer != null && integer > 1;
                    }
                })
                // map --> key -> value
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer i) {
                        System.out.println("i = " + i);
                        return i + "";
                    }
                }).collect(Collectors.toList());
        // stringStream.collect(Collectors.toList());

        new ArrayList<>().forEach(r -> {

        });
        Supplier<String> supplier = supplier();
        // String s = supplier.get();
        String string = string();

    }

    public Supplier<String> supplier() {

        return new Supplier<String>() {
            @Override
            public String get() {
                // fasdfasdfdsaf
                // 100s
                return "";
            }
        };
    }

    public String string() {
        // fdasfa
        return "";
    }

}
