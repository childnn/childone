package onjava8;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/6 18:10
 */
public class EvenChecker implements Runnable {
    private final int id;
    private IntGenerator generator;

    public EvenChecker(IntGenerator generator, int id) {
        this.generator = generator;
        this.id = id;
    }

    // Test any IntGenerator:
    public static void test(IntGenerator gp, int count) {
        List<CompletableFuture<Void>> checkers =
                IntStream.range(0, count)
                        .mapToObj(i -> new EvenChecker(gp, i))
                        .map(CompletableFuture::runAsync)
                        .collect(Collectors.toList());
        checkers.forEach(CompletableFuture::join);
    }

    // Default value for count:
    public static void test(IntGenerator gp) {
        new TimedAbort(4, "No odd numbers discovered");
        test(gp, 10);
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            if (val % 2 != 0) {
                System.out.println(val + " not even!");
                generator.cancel(); // Cancels all EvenCheckers
            }
        }
    }

}
