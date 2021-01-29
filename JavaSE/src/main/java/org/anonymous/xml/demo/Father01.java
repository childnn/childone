package org.anonymous.xml.demo;

/**
 * shadow
 * 2019/3/19 19:26
 * 1.类的静态成员随着类的加载而加载
 * 类的非静态成员,在这个类的对象创建出来的时候会被全部初始化(加载)
 * 2.父引用无法访问子类特有成员
 */
public class Father01 {
    private final String s = "father";

    Father01() {
        f();
    }

    public static void main(String[] args) {
        Father01 f = new Father01.Son();
        f.f();
        Son s = new Son();
        s.f();
    }

    void f() { //被子类重写覆盖
        System.out.println(s);
    }

    static class Son extends Father01 {
        private final String s = "son"; //有对象才会被赋值

        Son() {
            super(); //null -- 调用了子类的方法
            //            new Father01(); //father
        }

        @Override
        void f() {
            System.out.println(s); //null
        }
    }

}

class Father02 {
    private final String s = "father";

    Father02() {
        this.f();
    }

    public static void main(String[] args) {
        Father02 f = new Son();
        f.f();
        Son s = new Son();
        s.f();
    }

    private void f() { //调用这个方法
        System.out.println(s); //father
    }

    static class Son extends Father02 {
        private final String s = "son";

        Son() {

        }

        //        @Override //不是重写
        void f() {
            System.out.println(s);
        }
    }
}

class Father {
    Father() {
        call();
    }

    private static void call() {
        int i = 10;
        System.out.println(i); //10
    }

    public static void main(String[] args) {
        Father f = new Son();
    }

    static class Son extends Father {
        private final int i = 20;

        Son() {
            super();
        }

        void call() {
            System.out.println(i);
        }
    }
}

class Dmeo01 {
    public static void main(String[] args) {
        //        Father f = new Father.Son();
        //        f.call();
    }
}