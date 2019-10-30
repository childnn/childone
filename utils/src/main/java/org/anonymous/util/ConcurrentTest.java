package org.anonymous.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/20 9:22
 */
public class ConcurrentTest {
    private AtomicInteger i = new AtomicInteger(0);

    private int a = 0;

    public static void main(String[] args) {
        final ConcurrentTest cas = new ConcurrentTest();
        List<Thread> ts = new ArrayList<>(600);

        long start = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    cas.count();
                    cas.safeCount();
                }
            });
            ts.add(t);
        }
        ts.forEach(Thread::start);

        // 等待所有线程执行完毕.
        ts.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(cas.a);
        System.out.println(cas.i.get());
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    /* 使用 CAS 实现线程安全计数器. */
    private void safeCount() {
        for (; ; ) {
            int a = this.i.get();
            boolean suc = this.i.compareAndSet(a, ++a);
            if (suc) {
                break;
            }
        }
    }

    /* 非线程安全计数器. */
    private void count() {
        a++;
    }
}
