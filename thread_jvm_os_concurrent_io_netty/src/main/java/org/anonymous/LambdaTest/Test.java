package org.anonymous.LambdaTest;

interface YourInterface {
    int f(int i);
}

interface HisInterface {
    int f();
}

interface MyInterface {
    void f();
}

/**
 * 2019年2月21日15:35:24
 * lambda表达式基本格式：省略接口名，直接进入重写方法的内部
 * (形参1, 形参2) -> {方法体}
 * 要求：
 * 1.当接口有且仅有一个抽象方法时(函数式接口)，才可以使用lambda表达式
 * 2.当重写后的方法只有一句代码时，可以省略 标准格式中的  {return ;}  要求要省略一起省略
 * 3.当重写方法有且仅有一个形参时，可以省略形参外的小括号（只有这一种情况可以这么做）
 */

public class Test {
    public static void main(String[] args) {
        //方法一
        g(new MyInterface() {
            @Override
            public void f() {
                System.out.println("接口的匿名实现类对象！");
            }
        });
        //方法二
        g(() -> System.out.println("lambda表达式"));
        //接口的匿名实现类对象的lambda表达式形式
        MyInterface mi = () -> {
        };
        System.out.println(mi); //com.itheima.LambdaTest.zTest$$LambdaTest$2/6738746@7cf10a6f
        System.out.println("=====================");
        //无参有返回值的方法
        k(new HisInterface() {
            @Override
            public int f() {
                return 12;
            }
        });
        k(() -> 12);
        //有参有返回值的方法
        f(new YourInterface() {
            @Override
            public int f(int i) {
                return i;
            }
        }, 213);
        f(i -> i, 213);
    }

    private static void f(YourInterface yi, int i) {
        System.out.println(yi.f(i));
    }

    private static void k(HisInterface hi) {
        System.out.println(hi.f());
    }

    private static void g(MyInterface myInterface) {
        myInterface.f();
    }

}

