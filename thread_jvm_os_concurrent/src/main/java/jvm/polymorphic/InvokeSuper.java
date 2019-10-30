package jvm.polymorphic;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/13 19:52
 */
public class InvokeSuper {
    static class GrandFather {
        void thinking() {
            System.out.println("i am grandfather");
        }
    }

    static class Father extends GrandFather {
        @Override
        void thinking() {
            System.out.println("i am father");
        }
    }

    static class Son extends Father {
        @Override
        void thinking() {
            super.thinking(); // super 关键字只能调用父类方法, 祖类方法呢?
        }
    }


}
