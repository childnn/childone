package org.anonymous.demo;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/17 10:54
 */
public class Printf {
    public static void main(String[] args) {
        long x = 1000L * 1000000 * 1000;
        System.out.printf("%d", x);
        System.out.println("\0--\u0000--");
        double eps = 1.0e-5;
        System.out.printf("%f%n", eps); // 默认六位小数
        double e = 1.0E5;
        System.out.println("e = " + e);
        System.out.println("(1 - '0') = " + (1 - '0'));

        System.out.println("=============================");
        int i = 98765;
        char c = 1;

        int temp = i;
        i = c;
        c = (char) temp; // 强转后丢弃 int 高位.
        System.out.println("c = " + c);
        System.out.println("i = " + i);

        System.out.println(Double.NaN);
        System.out.println(Double.MAX_VALUE);
        double d = 1.7976931348623157e308;
        System.out.println(0xCAFEBABE);
    }

}
