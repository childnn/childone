package test.basic;

/**
 * Scanner 类的功能：可以实现键盘输入数据到程序中
 *
 * 引用数据类型的一般使用步骤：
 * 1.导包
 *      import 包名称.类名称;
 *      如果需要使用的【目标类】和【当前类】在同一个包下，则可以省略导包语句不写
 *      只有【java.lang】包下的内容不需要导包，其他的包都需要写导包【import】
 *  2.创建
 *       类名称 对象名 = new 类名称();
 *  3.使用
 *       对象名.方法名();
 *
 *  获取键盘输入的 int 数字：int num = sc.nextInt();
 *  获取键盘输入的字符串： String str = sc.next(); // 注意区别
 */

import java.util.Scanner; // 导包
public class ScannerTest {

    public static void main(String[] args) {
        // 创建对象
        // System.in 代表从键盘输入
        Scanner sc = new Scanner(System.in);

        // 获取键盘数字
        int num = sc.nextInt();
        System.out.println("键盘输入的int数字是：" + num);

        String str = sc.next();
        System.out.println("键盘输入的字符串是：" + str);
    }

}
