package jvm.polymorphic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/13 19:55
 */
public class InvokeGrand {
    public static void main(String[] args) {
        new Son().thinking();
    }

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
            try {
                MethodType mt = MethodType.methodType(void.class);
                MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
                mh.invoke(this);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
