package onjava8;

import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 22:43
 */
public class CountingStream {
    public static void main(String[] args) {
        System.out.println(
                IntStream.range(0, 10)
                        .parallel()
                        .mapToObj(CountingTask::new)
                        .map(CountingTask::call)
                        .reduce(0, Integer::sum));
    }
}
