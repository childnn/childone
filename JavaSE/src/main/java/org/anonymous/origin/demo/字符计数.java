package org.anonymous.origin.demo;

import java.util.Scanner;

public class 字符计数 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请随意输入一个由abcd四个小写字母组成的字符串");
        String s = sc.nextLine();
        char[] arr = s.toCharArray();
        int a = 0, b = 0, c = 0, d = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'a') {
                a++;
            }
            if (arr[i] == 'b') {
                b++;
            }
            if (arr[i] == 'c') {
                c++;
            }
            if (arr[i] == 'd') {
                d++;
            }
        }

        System.out.println("a(" + a + ")b(" + b + ")c(" + c + ")d(" + d + ")");
    }
}

/*
    树,以下代码有误
    1.字符串转数组

    2.编写策略判断重复元素的个数
        a.创建一个Map，键是唯一的存储字符，值可以重复存储个数 Map<Character,Integer> map = new TreeMap<Character,Integer>();
        b.遍历该数组
            获取每一个元素的角标，
            如果为null,则将该字符添加到Map的键中，值存储1
            否则，将角标自增1，存储到值

*/
/*
class 字符计数2 {
    public static void main(String[] args) {
        String str = "aababcabcdabcde";
        char[] chs = str.toCharArray();
        Map<Character, Integer> map = new TreeMap<Character, Integer>();

        int count = 0;
        for (char ch : chs) {
//            int index = map.get(ch);
            if (null == map.get(ch)) {
                map.put(ch, 1);
            } else {
                count++;
                map.put(ch, count);
            }
        }

        StringBuffer sb = new StringBuffer();
        Set<Character> keys = map.keySet();
        for (Character ch : keys) {
            sb.append(ch).append("(").append(map.get(ch)).append(")");
        }

        System.out.print(sb.toString());
        System.out.println();

    }
}*/
