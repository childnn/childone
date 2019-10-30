package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/5 10:20
 */
public class WorkStealingPool {
    public static void main(String[] args)
            throws InterruptedException {
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService exec = Executors.newWorkStealingPool();
        IntStream.range(0, 10)
                .mapToObj(n -> new ShowThread())
                .forEach(exec::execute);
        exec.awaitTermination(1, TimeUnit.SECONDS);
    }
}

class ShowThread implements Runnable {
    @Override
    public void run() {
        System.out.println(
                Thread.currentThread().getName());
    }
}