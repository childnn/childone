package org.anonymous.origin.demo.test;

import java.util.Scanner;

/**
 * 2019年2月21日16:50:47
 * 读入一个正整数 n，计算其各位数字之和，用汉语拼音写出和的每一位数字。
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("请输入一个数字序列：");
        String s = new Scanner(System.in).nextLine();
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            //            System.out.println(s.charAt(i)); //测试：输出各位数字
            //字符转字符串(数字字符不是对应十进制中的数字 字符'0' --> 48)
            String ch = s.charAt(i) + "";
            //字符串转int
            int a = Integer.parseInt(ch);
            //求各位数字之和
            sum += a;
        }
        //        System.out.println(sum); //测试：输出各位数字之和
        //int 转 String
        String sm = sum + "";
        //        String sm = String.valueOf(sum);
        //遍历String
        for (int i = 0; i < sm.length(); i++) {
            //获取每一位的数字字符
            char c = sm.charAt(i); //字符'0' --> ASCII 48
            //数字字符转字符串
            String sc = c + "";
            //字符串数字转 int
            int a = Integer.parseInt(sc);
            //调用f() ，直接输出结果
            System.out.print(f(a) + " ");
        }
    }

    //定义一个方法，把数字转化成对应的拼音
    private static String f(int i) {
        String[] arr = {"ling", "yi", "er", "san", "si", "wu", "liu", "qi", "ba", "jiu"};
        for (int j = 0; j < arr.length; j++) {
            if (i == j)
                return arr[j];
        }
        return null;
    }
}
