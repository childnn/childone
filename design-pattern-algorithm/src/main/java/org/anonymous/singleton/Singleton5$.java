package org.anonymous.singleton;

/**
 * @author child
 * 2019/7/8 13:56
 * 前面几种本质上都是 eager 模式
 *
 * lazy-loading: 存在并发问题
 */
public class Singleton5$ {
    private static Singleton5$ instance;

    private Singleton5$() {

    }

    public static Singleton5$ newInstance() {
        if (instance == null) {
            instance = new Singleton5$();
        }
        return instance;
    }
}
