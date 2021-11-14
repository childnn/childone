package test.basic;

import java.util.Scanner;

/**
 * 第二题:
 * 1.键盘录入一个字符串
 * 2.统计录入的字符串中的大写字母,小写字母,数字分别有多少个.
 */
class test02_ {

    public static void main(String[] args) {
        System.out.println("请输入：");
        String s = new Scanner(System.in).nextLine();
        int countUpper = 0; // 记录大写字母个数
        int countLower = 0; // 记录小写字母个数
        int countNum = 0; // 记录数字个数
        // 直接遍历字符串
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                countUpper++;
            } else if (c >= 'a' && c <= 'z') {
                countLower++;
            } else if (c >= '0' && c <= '9') {
                countNum++;
            }
        }
        System.out.println("大写字母个数为：" + countUpper);
        System.out.println("小写字母个数为：" + countLower);
        System.out.println("数字个数为：" + countNum);
        // 字符串字符转数组
//        for (char c : s.toCharArray()) {
//            if (c >= 'A' && c <= 'Z') {
//                countUpper++;
//            } else if (c >= 'a' && c <= 'z') {
//                countLower++;
//            } else if (c >= '0' && c <= '9') {
//                countNum++;
//            }
//        }
    }

}
