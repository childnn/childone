package org.anonymous.thread.pool;

import java.util.concurrent.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/9/18
 * 周期性任务抛出异常, 会
 */
public class FixedRateExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                // 模拟任务执行时间超过1秒
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Task executed!" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // try {
            throw new RuntimeException("Exception occurred!");
            // } catch (RuntimeException e) {
            // throw new RuntimeException(e);
            // }
        };

        // 使用scheduleAtFixedRate方法以固定的速率触发任务
        // The sequence of task executions continues indefinitely until one of the following exceptional completions occur:
        // The task is explicitly cancelled via the returned future.
        // The executor terminates, also resulting in task cancellation.
        // An execution of the task throws an exception. In this case calling get on the returned future will throw ExecutionException, holding the exception as its cause.
        ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        // exception
        Object o = scheduledFuture.get(); // 可能阻塞
        // System.out.println("o = " + o);
        // executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }

}
