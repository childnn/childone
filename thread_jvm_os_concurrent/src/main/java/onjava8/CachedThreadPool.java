package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see SingleThreadExecutor
 * @since 2020/4/9 22:31
 * 每个任务都有自己的线程，所以它们都并行运行
 */
public class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .mapToObj(NapTask::new)
                .forEach(exec::execute);
        exec.shutdown();
    }
}
