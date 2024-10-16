package org.anonymous.thread.virtualthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/9/18
 */
public class VirtualThreadTest {

    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        ExecutorService es = Executors.newVirtualThreadPerTaskExecutor();
        // ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 100000; i++) {
            es.submit(() -> {
                Thread.sleep(1000);
                return 0;
            });
        }
        es.close();

        System.out.println(System.currentTimeMillis() - currentTimeMillis);
    }

}
