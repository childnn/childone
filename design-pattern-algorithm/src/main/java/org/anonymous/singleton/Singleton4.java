package org.anonymous.singleton;

/**
 * @author child
 * 2019/7/8 13:50
 * 这里设置了一个 flag 参数, 防止反射创建对象, 破坏单例
 */
public class Singleton4 {
    private Singleton4 singleton4;

    private static boolean flag = false;

    private Singleton4() {
        if (!flag) {
            flag = true;
        } else {
            throw new RuntimeException("this class is singleton...");
        }
    }

    private static class SingletonHolder{
        private static final Singleton4 SINGLETON_4 = new Singleton4();
    }

    public static Singleton4 getInstance() {
        return SingletonHolder.SINGLETON_4;
    }

}
