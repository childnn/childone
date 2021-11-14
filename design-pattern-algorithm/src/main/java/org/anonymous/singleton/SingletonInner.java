package org.anonymous.singleton;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/18 11:26
 */
public class SingletonInner {
    private SingletonInner() {

    }

    public static SingletonInner singletonInner() {
        return SingletonInnerHolder.SINGLETON;
    }

    public static void main(String[] args) {
        new SingletonInner();
        System.out.println("==================");
        SingletonInner singletonInner = SingletonInner.singletonInner();
    }

/*    private class NonStatic {
        public NonStatic() {
            System.out.println(getClass());
        }

        //public static final SingletonInner SINGLETON = new SingletonInner(); // error
        public final SingletonInner SINGLETON = new SingletonInner();

    }

    public final SingletonInner s = SingletonInnerHolder.SINGLETON;*/

    private static class SingletonInnerHolder {
        public static final SingletonInner SINGLETON = new SingletonInner();

        // 不会被调用
        public SingletonInnerHolder() {
            System.out.println("SingletonInnerHolder");
        }
    }

}
