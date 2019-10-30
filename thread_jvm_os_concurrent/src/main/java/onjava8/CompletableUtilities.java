package onjava8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/10 17:29
 */
public class CompletableUtilities {

    // Get and show value stored in a CF:
    public static void showr(CompletableFuture<?> c) {
        try {
            System.out.println(c.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    // For CF operations that have no value:
    public static void voidr(CompletableFuture<Void> c) {
        try {
            c.get(); // Returns void
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
