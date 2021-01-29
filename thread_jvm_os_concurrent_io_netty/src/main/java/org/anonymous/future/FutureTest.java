package org.anonymous.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.concurrent.Callable
 * @see java.util.concurrent.Future
 * @see java.util.concurrent.FutureTask
 * @since 2021/1/25 13:48
 */
public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // FutureTask 实现 Runnable, 可以直接传给 Thread.
        // FutureTask 对 Runnable/Callable 进行封装.
        final FutureTask<Integer> ft = new FutureTask<>(() -> {
            int i;
            for (i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + " 的循环变量 i 的值: " + i);
            }
            return i;
        });

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " 的循环变量 i 的值: " + i);
            if (20 == i) {
                new Thread(ft, "有返回值的线程").start();
            }
        }

        System.out.println("子线程的返回值: " + ft.get());
    }

}
