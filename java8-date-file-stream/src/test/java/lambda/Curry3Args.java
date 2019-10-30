package lambda;

import java.util.function.Function;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 16:10
 */
public class Curry3Args {
    public static void main(String[] args) {
        Function<String,
                Function<String,
                        Function<String, String>>> sum =
                a -> b -> c -> a + b + c;
        Function<String,
                Function<String, String>> hi =
                sum.apply("Hi ");
        Function<String, String> ho =
                hi.apply("Ho ");
        System.out.println(ho.apply("Hup"));
    }
}
