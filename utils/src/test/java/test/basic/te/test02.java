package test.basic.te;

/**
 * 证明形参和实参不是一个变量
 */
public class test02 {

    public static void main(String[] args) {

        int i = 10;
        System.out.println(i);
        f(i);
        System.out.println(i);
        i = g(i);
        System.out.println(i);
    }

    public static void f(int i) {
        i = 99;
    }

    public static int g(int i) {
        return i = 99;
    }

}
