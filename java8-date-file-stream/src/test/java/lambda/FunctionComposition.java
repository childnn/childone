package lambda;

import java.util.function.Function;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 15:12
 */
public class FunctionComposition {
    static Function<String, String>
            f1 = s -> {
        System.out.println(s);
        return s.replace('A', '_');
    },
            f2 = s -> s.substring(3),
            f3 = String::toLowerCase,
    // 先执行 f2
    f4 = f1.compose(f2).andThen(f3);

    public static void main(String[] args) {
        System.out.println(
                f4.apply("GO AFTER ALL AMBULANCES"));
    }
}
