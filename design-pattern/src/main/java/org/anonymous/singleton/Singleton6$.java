package org.anonymous.singleton;

/**
 * @author child
 * 2019/7/8 14:00
 * lazy-loading: 加锁
 * 同步方法: 为 static 方法加 synchronized 锁, 虽然可以避免线程安全问题
 *  但是这里 就相当于为 整个类加了同步锁(静态方法属于 类)
 *  效率低, 不推荐使用.
 * 同步一个方法, 可能造成程序执行效率下降 100 倍.
 *
 */
public class Singleton6$ {

    private static Singleton6$ instance;
    private Singleton6$() {

    }

    public synchronized static Singleton6$ newInstance() {
        if (instance == null) {
            instance = new Singleton6$();
        }
        return instance;
    }
}
