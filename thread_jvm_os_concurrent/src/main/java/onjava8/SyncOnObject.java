package onjava8;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/7 20:43
 */
public class SyncOnObject {

    static void test(boolean fNap, boolean gNap) {
        DualSynch ds = new DualSynch();
        List<CompletableFuture<Void>> cfs =
                Arrays.stream(new Runnable[]{
                        () -> ds.f(fNap), () -> ds.g(gNap)})
                        .map(CompletableFuture::runAsync)
                        .collect(Collectors.toList());
        cfs.forEach(CompletableFuture::join);
        ds.trace.forEach(System.out::println);
    }

    public static void main(String[] args) {
        test(true, false);
        System.out.println("****");
        test(false, true);
    }

}

class DualSynch {
    ConcurrentLinkedQueue<String> trace = new ConcurrentLinkedQueue<>();
    private Object syncObject = new Object();

    public synchronized void f(boolean nap) {
        for (int i = 0; i < 5; i++) {
            trace.add("f() " + i);
            if (nap)
                new Nap(0.01);
        }
    }

    public void g(boolean nap) {
        synchronized (syncObject) {
            for (int i = 0; i < 5; i++) {
                trace.add("g() " + i);
                if (nap) new Nap(0.01);
            }
        }
    }
}
