package org.anonymous.se;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/18 11:11
 * 静态代码块 > 非静态代码块(构造代码块) > 构造方法
 * 静态代码块只执行一次
 */
public class Static {

    private static final int j;
    private static final int i;

    // 静态代码块本身对定义在其后的变量可以赋值,但不可访问
    // 定义在之前的不受限制
    static {
        i = 3;
        j = 4;
        System.out.println("static" + j/*i*/);
    }

    private final int b;
    private final int a;

    // 非静态代码块同上.
    {
        a = 1;
        b = 2;
        System.out.println("non static" + b/*a*/);
    }

    public Static() {
        System.out.println("constructor");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Class.forName("org.anonymous.se.Static");
        System.out.println("===================");
        new Static();
        System.out.println("===================");
        new Static();
        System.out.println("===================");
    }

}
