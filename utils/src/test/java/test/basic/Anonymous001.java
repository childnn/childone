package test.basic;

import java.util.Scanner;

// 【匿名对象】作为参数
public class Anonymous001 {

    public static void main(String[] args) {
        // 一般格式
        /*Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();*/
        // 匿名格式
        int i = new Scanner(System.in).nextInt();// 匿名对象,直接调用【nextInt()】方法 【输入 1】
        System.out.println("输入的值是：" + i); // 【输出 1】
        System.out.println("直接输出的值是：" + new Scanner(System.in).nextInt()); // 直接输出 【输入/输出 2】
        // 一般格式调用 形参 为【Scanner sc】的方法
        Scanner sc1 = new Scanner(System.in);
        param(sc1); // 【输入/输出 3】
//        System.out.println(sc1); // error， Scanner类的对象地址无法输出（类似 String 类）
        // 匿名 调用
        param(new Scanner(System.in)); // 【重点】【匿名传参】  【输入/输出 4】
        /*Scanner x = returnScanner();*/
        System.out.println("返回值是："+ returnScanner().nextInt()); // 【输入/输出 5】

    }

    private static void param(Scanner sc) { // 必须加【static】，否则【非静态方法】不能被【静态方法】调用
//        System.out.println(sc); // error
        int a = sc.nextInt(); // 【输入 3，4】
        System.out.println("a的值是："+ a); //【输出 3，4】
    }

    private static Scanner returnScanner() {
//        Scanner sc = new Scanner(System.in); // 间接返回
//        return sc;

        return new Scanner(System.in); // 【重点】【匿名返回】
    }
}