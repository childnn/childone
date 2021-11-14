package test.basic;

import java.util.Scanner;

/**
 * 第四题:
 * 1.键盘录入一个字符串
 * 2.将该字符串变成字符数组
 * 3.将字符数组中的所有大写字母变成小写字母
 * 4.如果第一位和最后一位的内容不相同,则交换
 * 5.将字符数组中索引为偶数的元素变成'~'
 * 6.打印数组元素的内容
 * ------------------------------
 * 【结果展示】
 * 请输入字符串
 * abcDEf719
 * 最终显示的效果
 * ~b~d~f~1~
 */
class test04_ {

    public static void main(String[] args) {
        String s = new Scanner(System.in).nextLine();
        // 字符串转字符数组: 这一步可以和下面一步换位置，然后下面的比较可以用 字符数组的 [0] 和 [n-1]比较（字符属于基本数据类型，可以通过 temp 转换）
        char[] chars = s.toCharArray();
        // 大写转小写
        String s1 = s.toLowerCase();
        String s3 = null;
        // 获取第一位[0]和最后一位元素[n-1],并比较,不同则换
        if (s1.charAt(0) != s1.charAt(s.length() - 1)) {
//            char temp = s1.charAt(0);
//            s1.charAt(0) = s1.charAt(s.length() - 1); // error, 不能这样赋值
//            s1.charAt(s.length() - 1) = temp;
//            String first = s1.replace(s1.charAt(0), s1.charAt(s.length() - 1));
//            String last = s1.replace(s1.charAt(s.length() - 1), s1.charAt(0)); // error ，replace是替换字符串中 所有相同 的【子字符串】
            // 切割：保留一号索引[1]一直到[n - 1]号索引：substring
            String s2 = s1.substring(1, s1.length() - 1); // 【注意】这里是长度减一
//            System.out.println(s2);

            // 加长：两端加长 (完成交换？)
            s3 = s1.charAt(s.length() - 1) + s2 + s1.charAt(0);
        } else {
            // 若相同，直接把 s1 (小写字符串) 赋值给 s3
            s3 = s1;
        }
//        System.out.println(s3);

        // 获取 字符串 s3 的奇数索引元素: 奇数索引个数为 【长度 / 2 + 1】， for循环
          // 定义字符数组，将 s3 的偶数元素放入数组: 这里删除了字符串，符合题意？
        char[] ch = new char[s3.length() / 2];
        for (int i = 1; i < s3.length(); i += 2) {
            ch[(i - 1) / 2] = s3.charAt(i);
        }
        System.out.println(ch);
        // 遍历数组 ch，加入符号“~”
        System.out.print("~");
        for (int i = 0; i < ch.length; i++) {
            System.out.print(ch[i] + "~");
        }
    }

}



