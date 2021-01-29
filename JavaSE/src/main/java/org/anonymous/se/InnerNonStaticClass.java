package org.anonymous.se;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/6 20:55
 * 如果存在非静态内部类对象, 则一定存在其依附的外部类对象
 * 但外部类对象存在时, 不一定存在非静态内部类对象
 * 非静态内部类不可有静态成员
 */
public class InnerNonStaticClass {
    int out;

    public static void main(String[] args) {
        // 执行下述代码, 只创建了外部类对象, 因此 上述外部类访问内部变量会报错
        InnerNonStaticClass insc = new InnerNonStaticClass();
        insc.accessInner();
    }

    void accessInner() {
        // System.out.println(in); // error
        int in = new Inner().in; // OK
    }

    class Inner {
        /*static { // error

        }*/

        int in;

        {
            System.out.println("ok");
        }

        void accessOut() {
            System.out.println(out);
        }
    }
}
