package org.anonymous.thread.pool;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/25 19:19
 */
public class CalTask extends RecursiveTask<Integer> {

    // 每个任务累加 20 个数
    private static final int THRESHOLD = 20;
    private final int[] arr;
    private final int start;
    private final int end;

    // 累计从 start-end 的数组元素
    public CalTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] arr = new int[10000];
        final Random r = new Random();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            int tmp = r.nextInt(100);
            arr[i] = tmp;
            sum += tmp;
        }
        System.out.println("sum = " + sum);

        final ForkJoinPool pool = ForkJoinPool.commonPool();
        final ForkJoinTask<Integer> forkJoinTask = pool.submit(new CalTask(arr, 0, arr.length));
        System.out.println(forkJoinTask.get());

        pool.shutdown();
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if (THRESHOLD > (end - start)) {
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
            return sum;
        } else {
            int middle = (start + end) / 2;
            final CalTask left = new CalTask(arr, start, middle);
            final CalTask right = new CalTask(arr, middle, end);

            // 并行: fork
            left.fork();
            right.fork();

            return left.join() + right.join(); // join
        }
    }
}
