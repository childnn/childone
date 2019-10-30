package lambda;

import java.util.function.Function;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 12:15
 * 递归
 */
public class RecursiveFactorialTest {

    static Function<Integer, Integer> f;

    public static void main(String[] args) {
        f = n -> n == 0 ? 1 : n * f.apply(n - 1);

        for (int i = 0; i < 10; i++) {
            System.out.println(f.apply(i));
        }
    }

    class F implements Function<Integer, Integer> {
        @Override
        public Integer apply(Integer i) {
            return i == 0 ? 1 : i * apply(i - 1);
        }
    }


}
