package org.anonymous.thread.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.concurrent.ForkJoinPool ExecutorService 的扩展
 * 将一个任务拆分成多个"小任务", 把多个 "小任务" 放到多个处理器核心上并行执行, 当多个小任务执行完毕之后,
 * 再将这些执行结果合并起来即可.
 * @see java.util.concurrent.ForkJoinPool#ForkJoinPool 指定并行线程数, 有默认值
 * @see java.util.concurrent.ForkJoinPool#commonPool() java8, 通用池.
 * 通用池的运行状态不会受 shutdown()/shutdownNow() 方法的影响.
 * @see java.util.concurrent.ForkJoinPool#getCommonPoolParallelism() 通用池并行级别
 * 执行:
 * @see java.util.concurrent.ForkJoinPool#submit(java.util.concurrent.ForkJoinTask)
 * @see java.util.concurrent.ForkJoinPool#invoke(java.util.concurrent.ForkJoinTask)
 * @see java.util.concurrent.ForkJoinTask 表示可并行/合并的任务
 * 子类 {@link java.util.concurrent.RecursiveAction}: 无返回值任务
 * {@link java.util.concurrent.RecursiveTask}: 有返回值任务
 * @since 2021/1/25 18:39
 */
public class ForkJoinPoolTest {

    public static void main(String[] args) throws InterruptedException {
        final ForkJoinPool fjp = new ForkJoinPool();

        // 提交任务
        fjp.invoke(new PrintTask(0, 200));
        final boolean b = fjp.awaitTermination(2, TimeUnit.SECONDS);

        // 关闭 pool
        fjp.shutdown();
    }

}

class PrintTask extends RecursiveAction {

    // 每个 小任务 最多打印 50 个数
    private static final int THRESHOLD = 50;
    private final int start;
    private final int end;

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        // 当 end/start 之间的差小于 THRESHOLD 时, 开始打印
        if (THRESHOLD > (end - start)) {
            System.out.format(Thread.currentThread().getName() + " 执行 (start: %s, end: %s)%n", start, end);
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + " 的 i 值: " + i);
            }
        } else {
            System.out.format(Thread.currentThread().getName() + " fork (start: %s, end: %s)%n", start, end);
            // 当 end-start 之间的差大于 THRESHOLD, 即要打印的数超过 50 个时
            // fork: 分解任务
            int middle = (start + end) / 2;
            final PrintTask left = new PrintTask(start, middle);
            final PrintTask right = new PrintTask(middle, end);

            // 并行执行
            left.fork();
            right.fork();
        }
    }

}