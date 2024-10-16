package org.anonymous.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/9/14
 * https://liaoxuefeng.com/blogs/all/2017-05-03-java-fork-join/index.html
 */
public class SumTask extends RecursiveTask<Long> {
    static final int THRESHOLD = 100;
    long[] array;
    int start;
    int end;

    SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) throws Exception {
        // 关键代码是fjp.invoke(task)来提交一个Fork/Join任务并发执行，然后获得异步执行的结果。
        // 我们设置任务的最小阈值是100，当提交一个400大小的任务时，在4核CPU上执行，会一分为二，再二分为四，
        // 每个最小子任务的执行时间是1秒，由于是并发4个子任务执行，整个任务最终执行时间大约为1秒。
        // 创建随机数组成的数组:
        long[] array = new long[1000];
        fillRandom(array);
        // fork/join task:
        ForkJoinPool fjp = new ForkJoinPool(10); // 最大并发数4
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = fjp.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }

    private static void fillRandom(long[] array) {

    }

    @Override
    protected Long compute() {
        // 编写这个Fork/Join任务的关键在于，在执行任务的compute()方法内部，先判断任务是不是足够小，
        // 如果足够小，就直接计算并返回结果（注意模拟了1秒延时），否则，把自身任务一拆为二，分别计算两个子任务，再返回两个子任务的结果之和。
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.printf("compute %d~%d = %d%n", start, end, sum);
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        System.out.printf("split %d~%d ==> %d~%d, %d~%d%n", start, end, start, middle, middle, end);
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);
        // 我们查看JDK的invokeAll()方法的源码就可以发现，invokeAll()的N个任务中，
        // 其中N-1个任务会使用fork()交给其它线程执行，但是，它还会留一个任务自己执行，这样，就充分利用了线程池，保证没有空闲的不干活的线程。
        invokeAll(subtask1, subtask2);
        Long subresult1 = subtask1.join();
        Long subresult2 = subtask2.join();
        Long result = subresult1 + subresult2;
        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
        return result;
    }

}
