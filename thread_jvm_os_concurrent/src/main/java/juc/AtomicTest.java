package juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/16 9:09
 * CAS: Compare and Set
 * CAS 指令需要有
 */
public class AtomicTest {
    static final int THREADS_COUNT = 20;
    static AtomicInteger race = new AtomicInteger();

    static void increase() {
        race.incrementAndGet();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increase();
                }
            });
            threads[i].start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.format("用时: %d%n", System.currentTimeMillis() - start);
        System.out.println(race);
    }

}
