package test.basic;

import java.util.Random;
import java.util.Scanner;

public class RandomScannerPlus {

    public static void main(String[] args) {
        Random r = new Random();
        int i = r.nextInt(100);
        System.out.println("请输入一个整数");
        // 给十次机会，判断 i 的值
        for (int k = 0; k < 10; k++) {
            Scanner sc = new Scanner(System.in);
            int j = sc.nextInt();
            if (9 == k) {
                System.out.println("十次机会已用完！！！");
            } else if (i < j){
                System.out.println("请输入一个更小的整数！");
            } else if (i > j) {
                System.out.println("请输入一个更大的整数！");
            } else {
                System.out.println("Correct!");
                break;
            }
        }
    }

}
