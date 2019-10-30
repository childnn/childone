package onjava8;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 22:17
 */
public class ParallelStreamPuzzle3 {

    public static void main(String[] args) {
        List<Integer> x = IntStream.range(0, 30)
                .peek(e -> System.out.println(e + ": " + Thread.currentThread().getName()))
                .limit(10)
                .parallel()
                .boxed()
                .collect(Collectors.toList());
        System.out.println(x);
    }
}
