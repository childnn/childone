package stream;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/15 13:14
 */
public class RandInts {
    private static int[] rints = new Random(47)
            .ints(0, 1000)
            .limit(100).toArray();

    public static IntStream rands() {
        return Arrays.stream(rints);
    }
}
