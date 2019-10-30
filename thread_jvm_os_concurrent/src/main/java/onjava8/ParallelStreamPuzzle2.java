package onjava8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 22:00
 */
public class ParallelStreamPuzzle2 {

    public static final Deque<String> trace = new ConcurrentLinkedDeque<>();

    public static void main(String[] args) throws Exception {
        List<Integer> x = Stream.generate(new IntGenerator())
                .limit(10)
                .parallel()
                .collect(Collectors.toList());
        System.out.println(x);
        Files.write(Paths.get("PSP2.txt"), trace);
    }

    static class IntGenerator implements Supplier<Integer> {
        private AtomicInteger current = new AtomicInteger();

        @Override
        public Integer get() {
            trace.add(current.get() + ": " + Thread.currentThread().getName());
            return current.getAndIncrement();
        }
    }

}
