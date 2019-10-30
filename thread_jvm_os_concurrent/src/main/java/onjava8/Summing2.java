package onjava8;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 21:04
 */
public class Summing2 {

    // Approximate largest value of SZ before
    // running out of memory on my machine:
    public static final int SZ = 20_000_000;
    public static final long CHECK = (long) SZ * ((long) SZ + 1) / 2;

    static long basicSum(long[] ia) {
        long sum = 0;
        for (long l : ia) sum += l;
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(CHECK);
        long[] la = new long[SZ + 1];
        Arrays.parallelSetAll(la, i -> i);
        Summing.timeTest("Array Stream Sum", CHECK, () -> Arrays.stream(la).sum());
        Summing.timeTest("Parallel", CHECK, () -> Arrays.stream(la).parallel().sum());
        Summing.timeTest("Basic Sum", CHECK, () -> basicSum(la)); // Destructive summation:
        Summing.timeTest("parallelPrefix", CHECK, () -> {
            Arrays.parallelPrefix(la, Long::sum);
            return la[la.length - 1];
        });
    }

}
