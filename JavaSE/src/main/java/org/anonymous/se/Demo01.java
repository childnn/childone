package org.anonymous.se;

/**
 * @author child
 * 2019/7/10 18:09
 */
public class Demo01 {
    public static void main(String[] args) {
        class Foo {
            final int i = 3;
        }
        Object o = new Foo();
        Foo f = (Foo) o;
        int a = f.i;
        System.out.println("a = " + a);

        Foo f1 = new Foo();
        Object o1 = new Object();
        //        o1 = f1; // 没有这一行, 下面运行报错: ClassCastException

        Foo f2 = ((Foo) o1);
    }
}
