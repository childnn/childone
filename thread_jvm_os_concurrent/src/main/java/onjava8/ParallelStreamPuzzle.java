package onjava8;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 21:58
 */
public class ParallelStreamPuzzle {

    public static void main(String[] args) {
        List<Integer> x = Stream.generate(new IntGenerator())
                .limit(10)
                .parallel()  // [1] 注释看结果: 并行的影响
                .collect(Collectors.toList());
        System.out.println(x);
    }

    static class IntGenerator implements Supplier<Integer> {
        private int current = 0;

        public Integer get() {
            return current++;
        }
    }
}
