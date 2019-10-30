package org.anonymous.singleton;

/**
 * @author child
 * 2019/7/8 13:47
 * eager-2
 */
public class Singleton3 {
    private static final Singleton3 singleton3;

    static {
        singleton3 = new Singleton3();
    }

    private Singleton3() {

    }

    public static Singleton3 getInstance() {
        return singleton3;
    }
}
