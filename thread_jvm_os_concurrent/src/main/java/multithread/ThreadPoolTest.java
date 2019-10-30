package multithread;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.concurrent.ThreadPoolExecutor#ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue, ThreadFactory, RejectedExecutionHandler)
 * * @param corePoolSize the number of threads to keep in the pool, even
 * *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
 * * @param maximumPoolSize the maximum number of threads to allow in the
 * *        pool
 * * @param keepAliveTime when the number of threads is greater than
 * *        the core, this is the maximum time that excess idle threads
 * *        will wait for new tasks before terminating.
 * * @param unit the time unit for the {@code keepAliveTime} argument
 * * @param workQueue the queue to use for holding tasks before they are
 * *        executed.  This queue will hold only the {@code Runnable}
 * *        tasks submitted by the {@code execute} method.
 * * @param threadFactory the factory to use when the executor
 * *        creates a new thread
 * * @param handler the handler to use when execution is blocked
 * *        because the thread bounds and queue capacities are reached
 * 拒绝策略: 当同时运行的线程数量达到最大线程数量并且队列已经被放满任务时.
 * @see ThreadPoolExecutor.AbortPolicy：抛出 RejectedExecutionException来拒绝新任务的处理。
 * @see ThreadPoolExecutor.CallerRunsPolicy：调用执行自己的线程运行任务。您不会任务请求。但是这种策略会降低对于新任务提交速度，影响程序的整体性能。另外，这个策略喜欢增加队列容量。如果您的应用程序可以承受此延迟并且你不能任务丢弃任何一个任务请求的话，你可以选择这个策略。
 * @see ThreadPoolExecutor.DiscardPolicy： 不处理新任务，直接丢弃掉。
 * @see ThreadPoolExecutor.DiscardOldestPolicy： 此策略将丢弃最早的未处理的任务请求。
 * -------------------------------------------------
 * * The runState provides the main lifecycle control, taking on values:
 * *
 * *   RUNNING:  Accept new tasks and process queued tasks
 * *   SHUTDOWN: Don't accept new tasks, but process queued tasks
 * *   STOP:     Don't accept new tasks, don't process queued tasks,
 * *             and interrupt in-progress tasks
 * *   TIDYING:  All tasks have terminated, workerCount is zero,
 * *             the thread transitioning to state TIDYING
 * *             will run the terminated() hook method
 * *   TERMINATED: terminated() has completed
 * ----------------------------------------------------
 * @since 2020/1/6 21:09
 */
public class ThreadPoolTest {

    // 当提交的任务数大于corePoolSize时，会优先放到队列缓冲区，
    // 只有填满了缓冲区后，才会判断当前运行的任务是否大于maxPoolSize，
    // 小于时会新建线程处理。大于时就触发了拒绝策略，
    // 总结就是：当前提交任务数大于（maxPoolSize + queueCapacity）时就会触发线程池的拒绝策略了。
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;

    // 线程池每次会同时执行 5 个任务，这 5 个任务执行完之后，剩余的 5 个任务才会被执行
    // 配置的核心线程数为 5 、等待队列容量为 100 ，所以每次只可能存在 5 个任务同时执行，
    // 剩下的 5 个任务会被放到等待队列中去。当前的 5 个任务之行完成后，才会之行剩下的 5 个任务。
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            Runnable work = () -> {
                System.out.println(Thread.currentThread().getName() + " Start Time = " + LocalDateTime.now());
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " End Time = " + LocalDateTime.now());
            };
            // 执行 任务.
            executor.execute(work);
        }

        // 终止线程池.
        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        System.out.println("Finished all task...");
    }
}
