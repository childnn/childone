package org.anonymous.concurrencyinpractice;

import org.anonymous.concurrencyinpractice.annotation.ThreadSafe;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/3 18:33
 * double-checked locking 在 java 1.4 以前会与 volatile 冲突, volatile 关键字导致双重检查加锁失败.
 */
@ThreadSafe
public class DoubleCheckedLock {
    /**
     * volatile 关键字确保: 当 instance 变量被初始化实例时, 多个线程正确的
     * 处理 instance 变量.
     * 禁止重排序.
     * 可见性.
     * 部分原子性.
     */
    private volatile static DoubleCheckedLock instance;

    private DoubleCheckedLock() {
        // con't init from outside
    }

    public static DoubleCheckedLock newInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedLock.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLock();
                }
            }
        }
        return instance;
    }

}
