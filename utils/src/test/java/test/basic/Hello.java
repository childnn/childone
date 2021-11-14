package test.basic;

import java.util.Scanner;

public class Hello {

    public static void main(String[] args) {
        // 创建输入对象,提示输入信息
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个字符串：");
        String s = sc.nextLine();
        // 将字符串变成字符数组
        char[] arr = s.toCharArray();
        // 大写转小写： 直接转字符串，如果操作数组，还要遍历，很麻烦
        String s1 = s.toLowerCase();
        // 比较第一位和最后一位字符
        char c = s1.charAt(0);
        System.out.println(s1.charAt(0));
        System.out.println(s1.charAt(s.length() - 1));
        char d = s1.charAt(s.length() - 1);
//        d = c;
//        if (c != d) {
        char temp = s1.charAt(0);
//        s1.charAt(0) = s1.charAt(s.length() - 1); // 等号左边报错，是因为是变量吗？
        d = temp;
//        System.out.println(temp);
        System.out.println(c);
        System.out.println(d);
//        }
    }

}
