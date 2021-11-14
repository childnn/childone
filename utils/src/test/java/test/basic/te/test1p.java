package test.basic.te;

public class test1p {

    public static void main(String[] args) {

        int a = 3;
        int b = 5;
        int c = 0;
        // 先取 b 的值 5 与 5 相乘，再对 b 进行加 1 ，b 变为 6；然后在取 a 的值 3， 并用 25 除以 3 得 8，然后在对 a 加 1，a 变为 4
        c = b++ * 5 / a++;  // 对变量进行自增运算，并参与运算
        System.out.println("a = " + a + "\tb = " + b + "\tc = " + c);
        // 取 b 的值 6，然后对 a 加 1，a 变为 5，并计算 6 于 5 的和为 11；然后对 b 加 1 ，b 变为 7
        c = b++ + ++a; // 对变量进行自增运算，并参与运算
        System.out.println("a = " + a + "\tb = " + b + "\tc = " + c);
        // 先取 a 的值 5 ，然后对 b 加 1， b 变为 8 ，并计算 5 与 8 的差为 -3 ；然后对 a 加 1 ，a 变为 6
        c = a++ - ++b; // 自增自减，并参与运算
        System.out.println("a = " + a + "\tb = " + b + "\tc = " + c);

        System.out.println(2 << 4); // 位运算，把2 左移 4 位，表示 2 乘以 2 的 4 次方

    }

}
