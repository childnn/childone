package test.basic;

/**
 * Object类：类 Object 是类层次结构的根类。每个类都使用 Object 作为超类。所有对象（包括数组）都实现这个类的方法。
 *          String toString()：返回该对象的字符串表示。 // 引用数据类型默认比较地址值，没有意义，必须重写
 *          boolean equals(Object obj)：指示其他某个对象是否与此对象“相等”。// 默认比较地址值
 * Objects类：jdk 1.7+ : 容忍空指针，防止空指针异常
 *          static boolean equals​(Object a, Object b) 返回 true如果参数相等，彼此 false否则。
 *
 * Date类
 *          Date()： 分配 Date 对象并初始化此对象，以表示分配它的时间（精确到毫秒）。
 *          Date(long date)： 传递毫秒值，转换为date日期
 *          long getTime()： 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 【Date 对象】表示的毫秒数。
 *                      中国东八区，加八小时
 */

import java.util.Date;

public class Demo01 {

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date); // 系统当前时间：Wed Jan 30 17:27:06 CST 2019
        Date date1 = new Date(100000L); //Thu Jan 01 08:01:40 CST 1970
        System.out.println(date1);
        System.out.println(date1.getTime());
        long time = date.getTime();
        System.out.println(time); // 自 1970 年 1 月 1 日 00:00:00 GMT 以来到系统当前时间对应的毫秒数

    }

}
