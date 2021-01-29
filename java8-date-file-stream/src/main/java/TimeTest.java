package cn.itheima;

import java.util.Calendar;
import java.util.Date;

/**
 * getTime()方法：
 * 1.Calendar抽象类：
 * Date getTime()：返回一个表示此 Calendar 时间值（从历元至现在的毫秒偏移量）的 Date 对象。
 * 2.Date类：
 * long getTime()：返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
 * setTime()方法：
 * 1.Calendar类：
 * void setTime(Date date)：使用给定的 Date 设置此 Calendar 的时间。
 * 2.Date类：
 * void setTime(long time)：设置此 Date 对象，以表示 1970 年 1 月 1 日 00:00:00 GMT 以后 time 毫秒的时间点。
 * 等价于Date(long date)：分配 Date 对象并初始化此对象，以表示自从标准基准时间（称为“历元（epoch）”，即 1970 年 1 月 1 日 00:00:00 GMT）以来的指定毫秒数。
 * 毫秒值：
 * 1.Calendar类：
 * long getTimeInMillis()：返回此 Calendar 的时间值，以毫秒为单位。
 * void setTimeInMillis(long millis)：用给定的 long 值设置此 Calendar 的当前时间值。
 * 2.Date类：
 * long getTime()：返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
 * 3.System类：
 * static long currentTimeMillis()：返回以毫秒为单位的当前时间。
 */

//获取系统当前时间
public class TimeTest {
    public static void main(String[] args) {
        //方法一：Date空参构造
        Date date = new Date();
        System.out.println("date = " + date);
        //方法二：Date含参构造，输入当前时间的毫秒值
        Date date1 = new Date(System.currentTimeMillis());
        System.out.println("date1 = " + date1);
        Date date3 = new Date(Calendar.getInstance().getTimeInMillis());
        System.out.println("date3 = " + date3);
        Date date5 = new Date(date.getTime());
        System.out.println("date5 = " + date5);
        //方法三：Calendar对象调用getTime()方法
        Date date2 = Calendar.getInstance().getTime();
        System.out.println("date2 = " + date2);

        Calendar ca = Calendar.getInstance();
        ca.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
        Date date4 = ca.getTime();
        System.out.println("date4 = " + date4);
    }
}
