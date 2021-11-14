package test.basic;

/**
 * 计算：1！+ 2！+ 3! + …… + n! 的值。
 */

import java.util.Scanner;

public class JieCheng {

    public static void main(String[] args) {

        System.out.println("请输入你想计算的阶乘：");
        int n = new Scanner(System.in).nextInt();
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            int num = 1; // 注意num定义在for外和for内的区别:每一次外层循环都从 1 开始做乘法

            for (int j = 1; j <= i; j++) {
                num = num * j;
            }
            System.out.println(num); // n! 的值
            sum += num;
        }

        System.out.println(sum);
    }

}
