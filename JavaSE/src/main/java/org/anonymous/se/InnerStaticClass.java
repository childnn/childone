package org.anonymous.se;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/6 21:01
 * 静态内部类属于外部类本身, 而不属于外部类的某个对象
 * static 的作用是把类的成员变成类相关, 而不是实例相关, 即 static 修饰的成员属于整个类, 而不属于单个对象.
 * 外部类的上一级程序单元是包, 所以不可用 static 修饰.
 */
public class InnerStaticClass {
    private static int p2;
    private int p1;

    void accessInner() {
        // System.out.println(i1); // error
        System.out.println(Inner.i1);
        System.out.println(new Inner().i2);
    }

    // 静态内部类是外部类的类相关的,而不是外部类的对象相关的.
    // 静态内部类不依赖于外部类的对象存在
    static class Inner {
        private static int i1;
        private int i2;

        void accessOuter() {
            // System.out.println(p1); // error
            System.out.println(p2);
        }
    }
}
