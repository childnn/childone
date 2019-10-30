package stream.optional;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 13:00
 */
public class OptionalFilter {
    static String[] elements = {
            "Foo", "", "Bar", "Baz", "Bingo"
    };

    static Stream<String> testStream() {
        return Arrays.stream(elements);
    }

    /**
     * **注意**，不同于普通 for 循环，这里的索引值范围并不是 `i < elements.length`，
     * 而是 `i <= elements.length`。所以最后一个元素实际上超出了流。
     * 方便的是，这将自动成为 **Optional.empty**，你可以在每一个测试的结尾中看到。
     *
     * @param descr
     * @param pred
     */
    static void test(String descr, Predicate<String> pred) {
        System.out.println(" ---( " + descr + " )---");
        for (int i = 0; i <= elements.length; i++) {
            System.out.println(
                    testStream()
                            .skip(i)
                            .findFirst()
                            .filter(pred));
        }
    }

    public static void main(String[] args) {
        test("true", str -> true);
        test("false", str -> false);
        test("str != \"\"", str -> str != "");
        test("str.length() == 3", str -> str.length() == 3);
        test("startsWith(\"B\")",
                str -> str.startsWith("B"));
    }

}
