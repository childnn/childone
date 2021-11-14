package test.basic;
/**
 * 猜数字：
 * 思路：
 * 1. 首先需要产生一个随机数字，并且一旦产生就不会变化。用【Random】类的【nextInt】方法
 * 2. 需要键盘输入一个数字，用到 【Scanner】类的 【nextInt】 方法
 * 3. 判断 随机数字 与 输入数字 的大小：【if……else】
 *    如果输入数字大了，提示大了，并继续程序。【循环】
 *    如果输入数字太小，提示小了，并继续程序。
 *    如果输入数字等于随机数，提示正确，并结束程序。【循环break】
 * 4. 【重点】：如果一直猜不对，将一直循环，这意味着不知道【循环次数】
 *    循环有两种：for， while
 *    当知道循环次数时，用  for
 *    当不知道循环次数，用 while
 */

import java.util.Random;
import java.util.Scanner;

public class RandomScanner {

    public static void main(String[] args) {
        // 定义一个随机数对象【r】，调用随机数方法（功能），定义一个变量【randomNumber】
        Random r = new Random(/*1000*/);
        int randomNumber = r.nextInt(100);
        System.out.println(randomNumber);

//        System.out.println("请输入您猜的整数：");
        // 定义一个输入对象【s】，调用输入方法（功能），定义一个变量【guessNumber】
        Scanner s = new Scanner(System.in);
//        int guessNumber = s.nextInt();

        // 因为电脑给的随机数【randomNumber】我们不知道（程序自己知道），进而比较【输入的数字】与【程序自己知道的随机数】
//        boolean b = false; // error  【死循环】必须是【while(true) {}】,只有小括号中为(true),while 才会执行
        while(true) {
            System.out.println("请输入您猜的整数：");
            int guessNumber = s.nextInt();

            if (guessNumber > randomNumber) {
                System.out.println("请输入一个更小的整数!");
//                continue; // 多余，因为 while 恒成立，除非遇到【break;】
            } else if (guessNumber < randomNumber) {
                System.out.println("请输入一个更大的整数!");
//                continue;
            } else {
                System.out.println("Correct!");
                break;
            }
        }
    }
}
