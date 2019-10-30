package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see CachedThreadPool2
 * 这是SingleThreadExecutor的主要好处 - 因为它一次运行一个任务，这些任务不会相互干扰，因此强加了线程安全性。
 * 这种现象称为线程限制，因为在单线程上运行任务限制了它们的影响。线程限制限制了加速，但可以节省很多困难的调试和重写。
 * @since 2020/4/9 22:34
 */
public class SingleThreadExecutor3 {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        IntStream.range(0, 10)
                .mapToObj(InterferingTask::new)
                .forEach(exec::execute);
        exec.shutdown();
    }
}
