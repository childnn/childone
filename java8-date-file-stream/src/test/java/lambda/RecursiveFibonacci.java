package lambda;

import java.util.function.Function;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 12:31
 */
public class RecursiveFibonacci {

    Function<Integer, Integer> fun;

    public RecursiveFibonacci() {
        fun = n -> n == 0 ? 0
                : n == 1 ? 1
                : fun.apply(n - 1) + fun.apply(n - 2);
    }

    public static void main(String[] args) {
        RecursiveFibonacci rf = new RecursiveFibonacci();
        for (int i = 0; i < 10; i++) {
            //System.out.println(rf.fibonacci(i));
            //System.out.printf("fibonacci: %d%n", rf.fibonacci(i));
            System.out.format("fibonacci: %d%n", rf.fibonacci(i));
        }
    }

    int fibonacci(int n) {
        return fun.apply(n);
    }

}
