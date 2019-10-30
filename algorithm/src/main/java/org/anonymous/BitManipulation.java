package org.anonymous;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/16 14:25
 */
public class BitManipulation {

    public static void main(String[] args) {
        // 利用或操作 `|` 和空格将英文字符转换为小写
        char a = (char) ('a' | ' ');
        System.out.println("a = " + a);
        a = (char) ('A' | ' ');
        System.out.println("a = " + a);

        System.out.println();

        // 利用与操作 `&` 和下划线将英文字符转换为大写
        char b = (char) ('b' & '_');
        System.out.println("b = " + b);
        b = (char) ('B' & '_');
        System.out.println("b = " + b);

        System.out.println();

        // 利用异或操作 `^` 和空格进行英文字符大小写互换
        char d = (char) ('d' ^ ' ');
        System.out.println("d = " + d);
        d = (char) ('D' ^ ' ');
        System.out.println("d = " + d);

        System.out.println();

        // 判断两个数是否异号
        int x = -1, y = 2;
        boolean f = (x ^ y) < 0;
        System.out.println("f = " + f);
        x = 3;
        y = 4;
        f = (x ^ y) > 0;
        System.out.println("f = " + f);

        System.out.println();

        // 交换两个数
        a = 1;
        b = 2;
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println("a = " + a);
        System.out.println("b = " + b);

    }

}
