package test.basic;

import java.util.Random;
import java.util.Scanner;

/**
 * 第五题:
 * 1.键盘录入一个字符串
 * 2.从字符串中随机获取3次字符,将获取的3个字符组成一个新的字符串.打印到控制台上
 */
class test05_ {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        Random r = new Random();
//        System.out.println(r); // 地址

        // 创建数组，将得到的三个字符放入 字符数组；或者用空字符串拼接三个字符串，在比那里
        char[] ch = new char[3];
        for (int i = 0; i < 3; i++) {
            int x = r.nextInt(s.length());  // x 表示索引
            System.out.println("随机数为：" + x);
            System.out.println("随机数字符为：" + s.charAt(x));
            ch[i] = s.charAt(x);
        }
        System.out.println(new String(ch));
    }

}
