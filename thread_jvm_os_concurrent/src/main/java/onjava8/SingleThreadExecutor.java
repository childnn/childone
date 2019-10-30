package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see CachedThreadPool
 * @since 2020/4/9 22:24
 */
public class SingleThreadExecutor {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        IntStream.range(0, 10)
                .mapToObj(NapTask::new)
                .forEach(exec::execute);
        System.out.println("All tasks submitted");
        exec.shutdown();
        while (!exec.isTerminated()) { // 在所有任务完成后变为 true
            System.out.println(Thread.currentThread().getName() + " awaiting termination");
            new Nap(0.1);
        }
    }

}
