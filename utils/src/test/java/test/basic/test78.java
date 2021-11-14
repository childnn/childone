package test.basic;

import java.util.Scanner;

/**
 * 第八题:
 * 1.模拟计算器功能，对键盘录入的两个int类型的数据进行加、减、乘、除的运算，并打印运算结果
 * 要求：
 * (1)键盘录入三个整数,其中前两个整数代表参加运算的数据，
 * 第三个整数为要进行的运算(0:表示加法运算,1:表示减法运算,2:表示乘法运算,3:表示除法运算)
 * (2)演示格式如下:
 * 请输入第一个整数:30
 * 请输入第二个整数:40
 * 请输入您要进行的运算(0:表示加法运算,1:表示减法运算,2:表示乘法运算,3:表示除法运算):0
 * 控制台输出:30+40=70
 */
public class test78 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入第一个整数:");
        int i = sc.nextInt();
        System.out.println("请输入第二个整数:");
        int j = sc.nextInt();
        System.out.println("请输入您要进行的运算(0:表示加法运算,1:表示减法运算,2:表示乘法运算,3:表示除法运算):");
        int x = sc.nextInt();
        if (x != 0 && x != 1 && x != 2 && x != 3) {
            System.out.println("对不起，没有该运算法则！");
        } else if (0 == x) {
            System.out.println(i + "+" + j + "=" + (i + j));
        } else if (1 == x) {
            System.out.println(i + "-" + j + "=" + (i - j));
        } else if (2 == x) {
            System.out.println(i + "×" + j + "=" + (i * j));
        } else {
            System.out.println(i + "÷" + j + "=" + (1.0 * i / j));
        }

    }

}
