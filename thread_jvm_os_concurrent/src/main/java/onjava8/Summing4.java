package onjava8;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 21:30
 */
public class Summing4 {

    public static void main(String[] args) {
        System.out.println(Summing3.CHECK);
        Long[] aL = new Long[Summing3.SZ + 1];
        Arrays.parallelSetAll(aL, i -> (long) i);
        Summing.timeTest("Long Parallel", Summing3.CHECK, () -> Arrays.stream(aL).parallel().reduce(0L, Long::sum));
    }

}
