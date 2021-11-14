package org.anonymous.singleton;

/**
 * @author child
 * 2019/7/8 13:46
 * eager-1
 */
public class Singleton2 {
    // 静态成员本身就是线程安全的.
    // JVM 加载类时, 初始化静态成员, JVM 保证任何线程在访问 当前 静态变量时, 一定先创建此实例.
    private static Singleton2 singleton2 = new Singleton2();

    private Singleton2() {

    }

    public static Singleton2 getInstance() {
        return singleton2;
    }
}
