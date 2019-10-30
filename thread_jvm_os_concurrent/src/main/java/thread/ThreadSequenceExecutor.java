package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/13 11:08
 */
public class ThreadSequenceExecutor {

    public static void main(String[] args) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(() -> System.err.println(Thread.currentThread().getName()));
        es.submit(() -> System.err.println(Thread.currentThread().getName()));
        es.submit(() -> System.err.println(Thread.currentThread().getName()));
        es.shutdown();
    }

}
