package onjava8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 20:34
 * 并行
 */
public class ParallelPrime {

    static final int COUNT = 100_000;

    public static boolean isPrime(long n) {
        return LongStream.rangeClosed(2, (long) Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }

    public static void main(String[] args) throws IOException {
        Timer timer = new Timer();
        List<String> primes = LongStream.iterate(2, i -> i + 1)
                .parallel()              // 注释这一行, 查看运行时间
                .filter(ParallelPrime::isPrime)
                .limit(COUNT)
                .mapToObj(Long::toString)
                .collect(Collectors.toList());
        System.out.println(timer.duration());
        Files.write(Paths.get("primes.txt"), primes, StandardOpenOption.CREATE);
    }

}
