package stream;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 13:35
 */
public class LastElement {
    public static void main(String[] args) {
        OptionalInt last = IntStream.range(10, 20)
                .reduce((n1, n2) -> n2);
        System.out.println(last.orElse(-1));
        // Non-numeric object:
        Optional<String> lastobj =
                Stream.of("one", "two", "three")
                        .reduce((n1, n2) -> n1 + n2);
        String reduce = Stream.of("one", "two", "three")
                // n1: 上一次计算的值,
                // n2: stream 中的当前元素
                .reduce("", (n1, n2) -> {
                    System.out.println("n1 = " + n1);
                    System.out.println("n2 = " + n2);
                    return n1 + n2;
                });
        System.out.println("reduce = " + reduce);
        System.out.println(
                lastobj.orElse("Nothing there!"));
    }
}
