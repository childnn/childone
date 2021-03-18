package org.anonymous.se.origin.demo.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 2019年2月21日16:50:41
 * 给定一组数据，要求你编写程序，将数字中所有数的顺序颠倒输出。
 * Collections工具类的方法：
 * static void reverse​(List<?> list) 反转指定列表中元素的顺序。
 */
public class Test01 {
    public static void main(String[] args) {
        System.out.println("请输入一串数字");
        String s = new Scanner(System.in).nextLine();
        //定义集合，接收每一位数字
        List<Integer> list = new ArrayList<>();
        //遍历并反转
        for (int i = 0; i < s.length(); i++) {
            //字符串转int
            int i1 = Integer.parseInt(s.charAt(i) + "");
            //int存入集合
            list.add(i1);
        }
        //反转集合
        Collections.reverse(list);
        //遍历集合并输出
        for (Integer i : list) {
            System.out.println(i);
        }
    }
}
