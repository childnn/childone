package stream;

import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 17:03
 * 斐波那契数列将数列中最后两个元素进行求和以产生下一个元素。
 * `iterate()` 只能记忆结果，因此我们需要利用一个变量 `x` 追踪另外一个元素。
 */
public class Fibonacci {
    int x = 1;

    public static void main(String[] args) {
        new Fibonacci().numbers()
                .skip(20) // 过滤前 20 个
                .limit(10) // 然后取 10 个
                .forEach(System.out::println);
    }

    Stream<Integer> numbers() {
        // producing a {@code Stream} consisting of {@code seed}, {@code f(seed)},
        //     * {@code f(f(seed))}, etc.
        return Stream.iterate(0, i -> {
            int result = x + i;
            x = i;
            return result;
        });
    }
}
