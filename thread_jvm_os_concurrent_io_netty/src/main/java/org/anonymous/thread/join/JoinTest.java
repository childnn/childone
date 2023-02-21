package org.anonymous.thread.join;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/25 14:58
 * join: Waits for this thread to die.
 */
public class JoinTest {

    // join 可以指定等待时间.
    // "子线程" 与 "被 join 的线程" 执行顺序不一定,
    // 但是 "被 join 的线程 99" 输出一定在 "main 20" 之前,
    // 即必须等 join 线程执行完毕, 才能执行当前线程.
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
        };
        // new Thread(task, "子线程").start();

        for (int i = 0; i < 100; i++) {
            if (20 == i) {
                final Thread join = new Thread(task, "被 join 的线程");
                join.start();

                // main 线程调用 子线程 join 方法, main 线程
                // 必须等待 子线程执行结束才会向下执行.
                join.join();
            }
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }

}
