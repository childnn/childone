package org.anonymous.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @see ReentrantLock 替换 synchronized
 * @since 2024/9/18
 */
public class ReentrantLockTest {

    static class Counter {
        private int count;

        public void add(int n) {
            synchronized (this) {
                count += n;
            }
        }
    }

    static class Counter1 {
        private final Lock lock = new ReentrantLock();
        private int count;

        public void add(int n) {
            lock.lock();
            try {
                count += n;
            } finally {
                lock.unlock();
            }
        }
        /*
        顾名思义，ReentrantLock是可重入锁，它和synchronized一样，一个线程可以多次获取同一个锁。
        和synchronized不同的是，ReentrantLock可以尝试获取锁：

            if (lock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    ...
                } finally {
                    lock.unlock();
                }
            }


         */
    }

}
