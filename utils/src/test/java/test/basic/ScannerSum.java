package test.basic;

import java.util.Scanner;

public class ScannerSum {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("请输入第一个数字：");
        int a = sc.nextInt();
        System.out.print("请输入第二个数字：");
        int b = sc.nextInt();
        int sum = a + b;
        System.out.print("和是：" + sum);
    }
}
