package lambda;

import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 16:11
 */
public class CurriedIntAdd {

    public static void main(String[] args) {
        IntFunction<IntUnaryOperator> curriedIntAdd = a -> b -> a + b;
        IntUnaryOperator add4 = curriedIntAdd.apply(4);
        System.out.println(add4.applyAsInt(5));
    }

}
