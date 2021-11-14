package test.basic;

import java.util.Scanner;

/**
 * 第三题:
 * 1.键盘录入5个字符串,组成一个数组
 * 2.统计录入的字符串数组中的大写字母,小写字母,数字分别有多少个.
 * 比较：test00/test03a.java
 * 比较：test00/test03b.java (最佳)
 */
class test03_ {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 定义一个 字符串类型的数组
        String[] str = new String[5];
        System.out.println("请输入五个字符串：");
        int countUpper = 0; // 记录大写字母个数
        int countLower = 0; // 记录小写字母个数
        int countNum = 0; // 记录数字个数
        // 键盘输入五个字符串，依次赋值给字符串数组 String[] str 的5个元素
        for (int i = 0; i < 5; i++) {
            str[i] = sc.nextLine();
            // 将得到的每个元素(字符串)转成 字节数组
            char[] c = str[i].toCharArray();
            // 遍历字节数组
            for (int j = 0; j < c.length; j++) {
                // 判断 并 计数
                if (c[j] >= 'A' && c[j] <= 'Z') {
                    countUpper++;
                } else if (c[j] >= 'a' && c[j] <= 'z') {
                    countLower++;
                } else if (c[j] >= '0' && c[j] <= '9') {
                    countNum++;
                }
            }
        }
        System.out.println("大写字母个数为：" + countUpper);
        System.out.println("小写字母个数为：" + countLower);
        System.out.println("数字个数为：" + countNum);

    }

}
