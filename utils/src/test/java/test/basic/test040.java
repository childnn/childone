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
public class test040 {

    public static void main(String[] args) {
        // 创建输入对象
        Scanner sc = new Scanner(System.in);
        // 提示输入信息
        System.out.println("请输入一个字符串：");
        String s = sc.nextLine();
        // 转小写: 不按照题意，先转小写会更简单
        String s1 = s.toLowerCase();
        // 转字符数组
        char[] chars = s1.toCharArray();
        // 第一位和最后一位不等则换
        if (chars[0] != chars[chars.length - 1]) {
            char temp = chars[0];
            chars[0] = chars[chars.length - 1];
            chars[chars.length - 1] = temp;
        }
        // 遍历数组
        for (int i = 0; i < chars.length; i++) {
            // 判断 偶数索引，并替换
            if (0 == i % 2) {
                chars[i] = '~'; // 注意是单引号
            }
            System.out.print(chars[i]);
        }
    }

}



