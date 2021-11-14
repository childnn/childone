package test.basic;

import java.util.ArrayList;
import java.util.Random;

/**
 * 第九题:
 * 分析以下需求，并用代码实现
 * 1.创建两个长度为5的数组，数组内元素为随机生成的 1-100之间的[偶数]。(r.nextInt(50)+1)*2
 * 2.定义一个方法hebin(描述如下)，传入两个数组，方法中将两个数组元素合并到一个新数组中，并且将新数组的内容打印到控制台上
 */
public class test79 {

    public static void main(String[] args) {
        int[] arr1 = new int[5];
        int[] arr2 = new int[5];
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            arr1[i] = (r.nextInt(50) + 1) * 2;
            arr2[i] = (r.nextInt(50) + 1) * 2;
        }

        heBin(arr1,arr2);
    }

    public static void heBin(int[] arr1, int[] arr2) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr1.length; i++) {
            list.add(arr1[i]);
        }
        for (int i = 0; i < arr2.length; i++) {
            list.add(arr2[i]);
        }
        int[] arr = new int[arr1.length + arr2.length];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}
