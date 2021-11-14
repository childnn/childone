package test.basic;

import java.util.Scanner;

/**
 * 第七题:
 * 1.定义方法 isSXH(int num)
 * 功能:判断数字num是否是水仙花数,如果是返回true,如果不是返回false
 * 2.在主方法中,键盘录入数据,调用isSXH方法,判断传入的数据是否为水仙花数,如果是就输出"xxx为水仙花数",否则就输出"xxx不是水仙花数"
 * 演示:
 * 请输入一个三位整数:
 * 100
 * 100不是水仙花数
 */
public class test77 {

    public static void main(String[] args) {
        if (isSXH()) {
            System.out.println("为水仙花数");
        } else {
            System.out.println("不是水仙花数");
        }

    }

    public static boolean isSXH() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个三位数：");
        int count = 1;
        while (true) {
            int num = sc.nextInt();

            if (num < 100 || num > 1000) {
                if (count < 3) {
                    System.out.println("您确定输入的是一个三位数吗？请重新输入一个三位数：");
                    count++;
                } else {
                    System.out.println("不要戏弄我！您的次数已用尽！");
                    break;
                }
            } else {
                int ge = num % 10;
                int shi = num / 10 % 10;
                int bai = num / 100;
                if (num == ge * ge *ge + shi * shi * shi + bai * bai * bai) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return  false;
    }
}
