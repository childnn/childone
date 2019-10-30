package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 22:28
 * 一旦你callexec.shutdown()，尝试提交新任务将抛出RejectedExecutionException。
 */
public class SingleThreadExecutor2 {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        IntStream.range(0, 10)
                .mapToObj(NapTask::new)
                .forEach(exec::execute);
        exec.shutdown();
    }
}

// **exec.shutdown()**的替代方法是**exec.shutdownNow()**，它除了不接受新任务外，还会尝试通过中断任务来停止任何当前正在运行的任务。
class MoreTasksAfterShutdown {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(new NapTask(1));
        exec.shutdown();
        try {
            exec.execute(new NapTask(99));
        } catch (RejectedExecutionException e) {
            System.err.println(e);
        }
    }
}