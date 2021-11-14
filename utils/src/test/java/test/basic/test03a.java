package test.basic;

/**
 * * 第三题:
 * 1.键盘录入5个字符串,组成一个数组
 * 2.统计录入的字符串数组中的大写字母,小写字母,数字分别有多少个.
 *  比较：test00/test03a.java
 * 比较：test00/test03b.java
 */

import java.util.Scanner;

public class test03a {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入第一个字符串：");
        String a = sc.nextLine();
        System.out.println("请输入第二个字符串：");
        String b = sc.nextLine();
        System.out.println("请输入第三个字符串：");
        String c = sc.nextLine();
        System.out.println("请输入第四个字符串：");
        String d = sc.nextLine();
        System.out.println("请输入第五个字符串：");
        String e = sc.nextLine();
        int countUpper = 0; // 记录大写字母个数
        int countLower = 0; // 记录小写字母个数
        int countNum = 0; // 记录数字个数
        // 创建 字符串数组，并放入五个输入的字符串
        String[] str = {a, b, c, d, e};
        // 每一个字符串转字符数组
        char[] charA = str[0].toCharArray();
        char[] charB = str[1].toCharArray();
        char[] charC = str[2].toCharArray();
        char[] charD = str[3].toCharArray();
        char[] charE = str[4].toCharArray();
        // 遍历数组 charA
        for (int i = 0; i < charA.length; i++) {
            // 判断字符属性
            if (charA[i] >= 'A' && charA[i] <= 'Z') {
                countUpper++;
            } else if (charA[i] >= 'a' && charA[i] <= 'z') {
                countLower++;
            } else if (charA[i] >= '0' && charA[i] <= '9') {
                countNum++;
            }
        }

        for (int i = 0; i < charB.length; i++) {
            // 判断字符属性
            if (charB[i] >= 'A' && charB[i] <= 'Z') {
                countUpper++;
            } else if (charB[i] >= 'a' && charB[i] <= 'z') {
                countLower++;
            } else if (charB[i] >= '0' && charB[i] <= '9') {
                countNum++;
            }
        }
        for (int i = 0; i < charC.length; i++) {
            // 判断字符属性
            if (charC[i] >= 'A' && charC[i] <= 'Z') {
                countUpper++;
            } else if (charC[i] >= 'a' && charC[i] <= 'z') {
                countLower++;
            } else if (charC[i] >= '0' && charC[i] <= '9') {
                countNum++;
            }
        }
        for (int i = 0; i < charD.length; i++) {
            // 判断字符属性
            if (charD[i] >= 'A' && charD[i] <= 'Z') {
                countUpper++;
            } else if (charD[i] >= 'a' && charD[i] <= 'z') {
                countLower++;
            } else if (charD[i] >= '0' && charD[i] <= '9') {
                countNum++;
            }
        }
        for (int i = 0; i < charE.length; i++) {
            // 判断字符属性
            if (charE[i] >= 'A' && charE[i] <= 'Z') {
                countUpper++;
            } else if (charE[i] >= 'a' && charE[i] <= 'z') {
                countLower++;
            } else if (charE[i] >= '0' && charE[i] <= '9') {
                countNum++;
            }
        }


//        // 遍历 数组 String[] str
//        for (int i = 0; i < str.length; i++) {
//            char[] chars = str[i].toCharArray();
//            // 遍历 数组 char[] chars
//            for (int j = 0; j < chars.length; j++) {
//                // 判断字符属性
//                if (chars[i] >= 'A' && chars[i] <= 'Z') {
//                    countUpper++;
//                } else if (chars[i] >= 'a' && chars[i] <= 'z') {
//                    countLower++;
//                } else if (chars[i] >= '0' && chars[i] <= '9') {
//                    countNum++;
//                }
//            }
//        }
        System.out.println("大写字母个数为：" + countUpper);
        System.out.println("小写字母个数为：" + countLower);
        System.out.println("数字个数为：" + countNum);

    }

}
