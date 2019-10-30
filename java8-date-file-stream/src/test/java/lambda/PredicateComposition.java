package lambda;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 15:14
 */
public class PredicateComposition {
    static Predicate<String>
            p1 = s -> s.contains("bar"),
            p2 = s -> s.length() < 5,
            p3 = s -> s.contains("foo"),
    // 不含 "bar" 且长度小于 5; 或者 包含 "foo", 则返回 true
    p4 = p1.negate().and(p2).or(p3);

    public static void main(String[] args) {
        Stream.of("bar", "foobar", "foobaz", "fongopuckey")
                .filter(p4)
                .forEach(System.out::println);
    }
}
