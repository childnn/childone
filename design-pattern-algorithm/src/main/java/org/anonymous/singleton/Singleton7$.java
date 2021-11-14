package org.anonymous.singleton;

/**
 * @author child
 * 2019/7/8 14:02
 *
 * lazy-loading: 同步代码块
 * 这里仍然会有线程安全问题: 如果多个线程同时访问 if 的判断语句,
 *  仍然有可能创建多个实例
 */
public class Singleton7$ {

    private static Singleton7$ instance;

    private Singleton7$() {

    }

    public static Singleton7$ newInstance() {
        if (instance == null) {
            synchronized (Singleton7$.class) {
                instance = new Singleton7$();
            }
        }

        return instance;
    }
}
