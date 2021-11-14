package test.basic.test;

import java.util.Arrays;

/**
 * 五、分析以下需求，并用代码实现：
 * (1)打印由7，8，9三个数组成的三位数，要求该三位数中任意两位数字不能相同；
 * (2)打印格式最后的三位数字以空格分隔，如789 798 879 897 978 987。
 * 注：要求使用StringBuilder来完成
 * 789--987 reverse
 * 798--897
 * 879--978
 *
 */
public class test005 {
    public static void main(String[] args) {
        StringBuilder bu = new StringBuilder("78978");
        // 一共有六个数字，定义长度为6的数组
        int[] arr = new int[6];
        int a = 0;
        for (int i = 0; i < 3; i++) {
            String s = bu.substring(i, i + 3);
            // 以下几个有什么区别
//            System.out.println(s);
//            System.out.println(s.toString()); // toString 的对象不能为 null
//            System.out.println(String.valueOf(s)); //
            StringBuilder bu2 = new StringBuilder(s);
            arr[a++] = Integer.parseInt(bu2.toString());
            arr[a++] = Integer.parseInt(bu2.reverse().toString());
//            System.out.println(bu2.toString() + " " + bu2.reverse().toString());
        }

        Arrays.sort(arr); // 升序排序

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

        String s = null;
//        System.out.println(s.toString()); // NullPointerException，NPE
        System.out.println(String.valueOf(s)); // 这里把 null 当成字符串"null"输出
//        System.out.println(String.valueOf(null)); // NullPointerException，不能直接转换 null
    }
}
