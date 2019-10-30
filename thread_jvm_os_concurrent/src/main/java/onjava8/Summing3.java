package onjava8;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 21:28
 */
public class Summing3 {

    // Approximate largest value of SZ before
    // running out of memory on my machine:
    public static final int SZ = 10_000_000;
    public static final long CHECK = (long) SZ * ((long) SZ + 1) / 2;

    static long basicSum(Long[] ia) {
        long sum = 0;
        for (Long aLong : ia) sum += aLong;
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(CHECK);
        Long[] aL = new Long[SZ + 1];
        Arrays.parallelSetAll(aL, i -> (long) i);
        Summing.timeTest("Long Array Stream Reduce", CHECK, () -> Arrays.stream(aL).reduce(0L, Long::sum));
        Summing.timeTest("Long Basic Sum", CHECK, () -> basicSum(aL));
        // Destructive summation:
        Summing.timeTest("Long parallelPrefix", CHECK, () -> {
            Arrays.parallelPrefix(aL, Long::sum);
            return aL[aL.length - 1];
        });
    }

}
