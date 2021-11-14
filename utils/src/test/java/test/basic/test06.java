package test.basic;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 第六题:
 * 1.创建一个集合,往集合中键盘录入5个字符串
 * 2.遍历集合,将集合中长度大于4的元素末尾加上一个X,
 * 3.遍历集合,将集合打印在控制台上.
 * 例:键盘录入后的集合{"123","ASDFQ","qq","poiuy","asd"}
 * 打印到控制台上的集合{"123","ASDFQX","qq","poiuyX","asd"}
 */
class test06_ {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list1 = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        // 往集合中录入 5 个字符串
        for (int i = 0; i < 5; i++) {
            System.out.println("请输入第" + (i + 1) + "个字符串：");
            list.add(sc.nextLine());
        }
        // 遍历集合，获取字符串
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            // 判断长度
            if (s.length() > 4) {
                s += "X"; //  【这里可以用 list.set() 方法将新的字符串替换到元字符串的索引】
            }
            // 添加到新的集合
            list1.add(s);
        }
        System.out.println(list1);

    }

}
