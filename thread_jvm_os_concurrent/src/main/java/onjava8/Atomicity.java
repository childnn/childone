package onjava8;

import java.util.concurrent.CompletableFuture;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 15:52
 */
public class Atomicity {
    public static void test(IntTestable it) {
        new TimedAbort(4, "No failures found");
        CompletableFuture.runAsync(it);
        while (true) {
            int val = it.getAsInt();
            if (val % 2 != 0) {
                System.out.println("failed with: " + val);
                System.exit(0);
            }
        }
    }
}
