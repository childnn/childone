package test.basic;

import java.util.Scanner;

/**
 * 第三题:
 * 1.键盘录入5个字符串,组成一个数组
 * 2.统计录入的字符串数组中的大写字母,小写字母,数字分别有多少个.
 * 比较：test00/test03a.java
 * 比较：test00/test03.java
 */
public class test03b {

    public static void main(String[] args) {
       // 定义输入对象, 显示提示信息
        Scanner sc = new Scanner(System.in);
        System.out.println("请分步输入5个字符串：");
        // 定义字符串数组，长度为5
        String[] arr = new String[5];
        // 定义空字符串
        String str = ""; // 关键！！！！！！！
        // 定义计数器
        int countUpper = 0;
        int countLower = 0;
        int countNum = 0;
        // 调用输入方法 5 次： for
        for (int i = 0; i < 5; i++) {
            String s = sc.nextLine();
            // 把 输入 字符串 赋值给数组
            arr[i] = s;
            // 直接把五个字符串加起来：在外面定义空字符串
            str += arr[i]; // 【关键！！！！！！！！！！】
        }
        // 遍历 新形成的字符串
        for (int i = 0; i < str.length(); i++) {
            // 判断字符类型：在外面定义计数器
            if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
                countUpper++;
            } else if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                countLower++;
            } else if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                countNum++;
            }
        }
        System.out.println("大写字母个数为：" + countUpper);
        System.out.println("小写字母个数为：" + countLower);
        System.out.println("数字个数为：" + countNum);
    }

}
