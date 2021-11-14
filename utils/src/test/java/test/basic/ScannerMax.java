package test.basic;

import java.util.Scanner;

public class ScannerMax {

    public static void main(String[] args) {
        Scanner sca = new Scanner(System.in);
        System.out.println("请输入是三个整数：");
        int i = sca.nextInt();
        int j = sca.nextInt();
        int k = sca.nextInt();

        int temp = i > j ? i : j;
        int m = k > temp ? k : temp;
        System.out.println("最大值：" + m);

        int[] arr = {i, j, k};
        int max = i;
        for (int a = 0; a < arr.length; a++) {
            if (max < arr[a]) {
                max = arr[a];
            }
        }
        System.out.println("最大值是：" + max);
    }
}
