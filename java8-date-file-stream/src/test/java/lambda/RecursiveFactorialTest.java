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

        long factorial = tailFactorial(Integer.MAX_VALUE, Integer.MAX_VALUE);
        System.out.println("factorial = " + factorial);

    }

    // 函数式编程有一个概念，叫做柯里化（currying），意思是将多参数的函数转换成单参数的形式。
    static long factorial(long n) {
        return tailFactorial(n, 1);
    }

    // 尾递归的实现，往往需要改写递归函数，确保最后一步只调用自身。
    // 做到这一点的方法，就是把所有用到的内部变量改写成函数的参数。
    static long tailFactorial(long n, long total) {
        if (n == 1)
            return total;
        return tailFactorial(n - 1, n * total);
    }

    class F implements Function<Integer, Integer> {
        @Override
        public Integer apply(Integer i) {
            return i == 0 ? 1 : i * apply(i - 1);
        }
    }


}
