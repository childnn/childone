package stream;

import java.util.stream.LongStream;

import static java.util.stream.LongStream.iterate;
import static java.util.stream.LongStream.rangeClosed;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 11:37
 */
public class Prime {
    public static Boolean isPrime(long n) {
        return rangeClosed(2, (long) Math.sqrt(n))
                .noneMatch(i -> n % i == 0);
    }

    public static void main(String[] args) {
        new Prime().numbers()
                .limit(10)
                .forEach(n -> System.out.format("%d ", n));
        System.out.println();
        new Prime().numbers()
                .skip(90)
                .limit(10)
                .forEach(n -> System.out.format("%d ", n));
    }

    public LongStream numbers() {
        return iterate(2, i -> i + 1)
                .filter(Prime::isPrime);
    }
}
