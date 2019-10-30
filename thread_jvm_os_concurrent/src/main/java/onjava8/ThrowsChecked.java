package onjava8;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/10 19:21
 */
public class ThrowsChecked {
    static ThrowsChecked nochecked(ThrowsChecked tc) {
        return tc;
    }

    static ThrowsChecked withchecked(ThrowsChecked tc) throws Checked {
        return tc;
    }

    static void testStream() {
        Stream.of(new ThrowsChecked())
                .map(ThrowsChecked::nochecked)
                // .map(ThrowsChecked::withchecked); // [1]
                .map(tc -> {
                    try {
                        return withchecked(tc);
                    } catch (Checked e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    static void testCompletableFuture() {
        CompletableFuture.completedFuture(new ThrowsChecked())
                .thenApply(ThrowsChecked::nochecked)
                // .thenApply(ThrowsChecked::withchecked); // [2]
                .thenApply(tc -> {
                    try {
                        return withchecked(tc);
                    } catch (Checked e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    class Checked extends Exception {
    }
}
