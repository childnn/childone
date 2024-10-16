package org.anonymous.thread.lock;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/9/18
 * 实现 多读 单写, 但是 此为悲观读锁, 读时不可写
 */
public class ReadWriteLockTest {

    static class Counter {
        private final Lock lock = new ReentrantLock();
        private final int[] counts = new int[10];

        public void inc(int index) {
            lock.lock();
            try {
                counts[index] += 1;
            } finally {
                lock.unlock();
            }
        }

        /*
        有些时候，这种保护有点过头。因为我们发现，任何时刻，只允许一个线程修改，也就是调用inc()方法是必须获取锁，
        但是，get()方法只读取数据，不修改数据，它实际上允许多个线程同时调用。

            实际上我们想要的是：允许多个线程同时读，但只要有一个线程在写，其他线程就必须等待：

         */
        public int[] get() {
            lock.lock();
            try {
                return Arrays.copyOf(counts, counts.length);
            } finally {
                lock.unlock();
            }
        }
    }

    /*
    把读写操作分别用读锁和写锁来加锁，在读取时，多个线程可以同时获得读锁，这样就大大提高了并发读的执行效率。
        使用ReadWriteLock时，适用条件是同一个数据，有大量线程读取，但仅有少数线程修改。
        例如，一个论坛的帖子，回复可以看做写入操作，它是不频繁的，但是，浏览可以看做读取操作，是非常频繁的，这种情况就可以使用ReadWriteLock。

     */
    static class ReadWriteCounter {
        private final ReadWriteLock rwlock = new ReentrantReadWriteLock();
        // 注意: 一对读锁和写锁必须从同一个rwlock获取:
        private final Lock rlock = rwlock.readLock();
        private final Lock wlock = rwlock.writeLock();
        private final int[] counts = new int[10];

        public void inc(int index) {
            wlock.lock(); // 加写锁
            try {
                counts[index] += 1;
            } finally {
                wlock.unlock(); // 释放写锁
            }
        }

        public int[] get() {
            rlock.lock(); // 加读锁
            try {
                return Arrays.copyOf(counts, counts.length);
            } finally {
                rlock.unlock(); // 释放读锁
            }
        }
    }

}
