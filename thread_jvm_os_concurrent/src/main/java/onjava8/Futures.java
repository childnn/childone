package onjava8;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 22:41
 */
public class Futures {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        Future<Integer> f =
                exec.submit(new CountingTask(99));
        System.out.println(f.get()); // [1] 当你的任务尚未完成的**Future**上调用**get()**时，调用会阻塞（等待）直到结果可用
        exec.shutdown();
    }
}
