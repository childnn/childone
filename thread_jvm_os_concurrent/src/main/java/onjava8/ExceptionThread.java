package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/5 10:48
 */
public class ExceptionThread implements Runnable {

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(new ExceptionThread());
        es.shutdown();
    }

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
