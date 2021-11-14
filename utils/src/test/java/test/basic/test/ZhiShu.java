package test.basic.test;

import java.util.Scanner;

/**
 * 判断质数
 */
public class ZhiShu {

    public static void main(String[] args) {
        System.out.println("请输入一个正整数: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int i;
        for (i = 2; i < n; i++) {
            if (0 == n % i) {
                break;
            }
        }
        if (i == n) {
            System.out.println("是质数");
        } else {
            System.out.println("不是质数");
        }
    }

}
