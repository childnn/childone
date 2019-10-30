package onjava8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see SingleThreadExecutor3
 * 避免竞争条件的最好方法是避免可变的共享状态。
 * @since 2020/4/9 22:33
 * 每个任务增加val一百次
 * 非线程安全
 * 多个任务同时修改同一个变量会产生竞争
 */
public class CachedThreadPool2 {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        IntStream.range(0, 10)
                .mapToObj(InterferingTask::new)
                .forEach(exec::execute);
        exec.shutdown();
    }
}
