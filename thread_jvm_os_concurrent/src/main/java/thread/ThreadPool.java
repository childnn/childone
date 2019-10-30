package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/29 10:10
 */
public class ThreadPool {
    public static void main(String[] args) {
        // 当线程池中线程的数目大于10的时候，就会将任务放入队列，当任务队列满了之后，就会额外创建新的线程。
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10, 15, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));
        for (int i = 0; i < 20; i++) {
            MyTask task = new MyTask(i);
            executor.execute(task);
            System.out.printf("线程池中线程数量: %s, 任务队列中等待执行的任务数量: %s, 执行完成的任务数量: %s%n",
                    executor.getPoolSize(), executor.getQueue().size(), executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }

    private static class MyTask implements Runnable {
        private final int num;

        public MyTask(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            System.out.format("正在执行任务：%s%n", num);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.format("任务 %s 执行完毕%n", num);
        }
    }
}
