package org.anonymous.singleton;

/**
 * @author child
 * 2019/7/8 14:04
 *  lazy-loading 与 双重校验 double-checked locking.
 *  线程安全
 *  延迟加载
 *  效率较高
 *
 *  double-checked locking 在 java 1.4 以前会与 volatile 冲突,
 *  volatile 关键字导致双重检查加锁失败.
 */
public class Singleton8$efficiency {

    /**
     * volatile 关键字确保: 当 instance 变量被初始化实例时, 多个线程正确的
     * 处理 instance 变量.
     */
    private static volatile Singleton8$efficiency instance;

    private Singleton8$efficiency() {

    }

    public static Singleton8$efficiency newInstance() {
        // 检查实例, 如果不存在, 就进入同步区块.
        if (null == instance) {
            // 只有第一次, 才会彻底执行同步代码.
            synchronized (Singleton8$efficiency.class) {
                if (null == instance) {
                    instance = new Singleton8$efficiency();
                }
            }
        }

        return instance;
    }
}
