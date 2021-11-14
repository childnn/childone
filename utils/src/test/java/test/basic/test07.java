package test.basic;

import java.util.Scanner;

/**
 * 第七题:
 分析以下需求，并用代码实现
 1.定义如下方法public static String getPropertyGetMethodName(String property)
 功能描述:
 (1)该方法的参数为String类型，表示用户传入的参数，返回值类型为String类型，返回值为对应的get方法的名字
 (2)如：用户调用此方法时传入参数为"name",该方法的返回值为"getName"
 传入参数为"age",该方法的返回值为"getAge"

 2.定义如下方法public static String getPropertySetMethodName(String property)
 功能描述:
 (1)该方法的参数为String类型，表示用户传入的参数，返回值类型为String类型，返回值为对应的set方法的名字
 (2)如：用户调用此方法时传入参数为"name",该方法的返回值为"setName"
 传入参数为"age",该方法的返回值为"setAge"
 */
public class test07 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        System.out.println(getPropertyGetMethodName(s));
        System.out.println(getPropertySetMethodName(s));

    }

    public static String getPropertyGetMethodName(String property) {
        // 定义长度为 1 的字符数组
        char[] ch = new char[1];
        // 数组接收 传入字符串的 0 号元素（首字母）  // 直接字符 加 空字符串 得到字符串
        ch[0] = property.charAt(0);
//        String s = ch.toString();
        // 字符数组转字符串
        String str = new String(ch);
        // 字符串转大写
        String str1 = str.toUpperCase();
        // 获取 传入字符串的 0 号元素之后的所有元素：截取 子字符串 // 【注意】直接用replace 方法替换的是所有相同的字母
        String str2 = property.substring(1);
        String str3 = "get" + str1 + str2;

        return str3;
    }

    public static String getPropertySetMethodName(String property) {
        // 定义长度为 1 的字符数组
        char[] ch = new char[1];
        // 数组接收 传入字符串的 0 号元素（首字母）
        ch[0] = property.charAt(0);
//        String s = ch.toString();
        // 字符数组转字符串
        String str = new String(ch);
        // 字符串转大写
        String str1 = str.toUpperCase();
        // 获取 传入字符串的 0 号元素之后的所有元素：截取 子字符串
        String str2 = property.substring(1);
        String str3 = "set" + str1 + str2;

        return str3;
    }

}
