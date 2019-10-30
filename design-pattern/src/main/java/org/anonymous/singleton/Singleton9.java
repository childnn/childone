package org.anonymous.singleton;

/**
 * @author child
 * 2019/7/8 15:13
 * @see Singleton4
 * 静态内部类的单例与饿汉式采用的机制类似, 但又有不同.
 * 两者都是采用了类装载的机制来保证初始化实例只有一个线程.
 * 不同的地方在于 eager 方式是只要 类加载就会实例化, 没有 lazy-loading
 * 而静态内部类方式在 inner class 被装载时不会立即实例化, 而是在需要实例化时,
 * 调用 newInstance 方法, 才会装载单例类, 从而完成 实例化
 *
 * 避免线程不安全
 * 延迟加载
 * 效率高
 */
public class Singleton9 {

    private Singleton9() {

    }

    private static class SingletonInstance{
        private static final Singleton9 instance = new Singleton9();
    }

    public static Singleton9 newInstance() {
        return SingletonInstance.instance;
    }
}

