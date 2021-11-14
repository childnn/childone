package test.basic;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 第十题:
 * 分析以下需求，并用代码实现
 * 1.创建一个储存整数的集合,键盘录入5个数据存入集合
 * 2.遍历集合,将集合中大于10的元素打印到控制台上,并求和.
 */
public class test710 {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            list.add(sc.nextInt());
        }
        int sum = 0;
        System.out.println("输入数据大于10的有：");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > 10) {
                System.out.print(list.get(i) + " ");
                sum += list.get(i);
            }
        }
        System.out.println();
        System.out.println("大于10的数和为：" + sum);
    }

}
