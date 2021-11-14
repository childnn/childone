package test.basic;

import java.util.Calendar;

/**
 * Calendar类：抽象类
 *      static Calendar getInstance(Locale aLocale)：使用默认时区和指定语言环境获得一个日历。
 *      返回值是子类对象,多态
 *
 *      get(Calendar.YEAR/MONTH/DATE):
 *      set(Calendar.YEAR/MONTH/DATE, year/month/date);
 *      set(year, month, date);
 *      add(Calendar.YEAR/MONTH/DATE, amount); : amount 可正可负，加减
 *      getTime(); 日历转日期
 */
public class CalendarClass {

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        System.out.println(c); // 地址
        int year = c.get(Calendar.YEAR); // 静态成员属性
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int date1 = c.get(Calendar.DAY_OF_MONTH);
        System.out.println(year);
        System.out.println(month);
        System.out.println(date);
        System.out.println(date1);

        c.set(Calendar.YEAR, 2018);
        System.out.println(Calendar.YEAR); // 1
        System.out.println(Calendar.MONTH); // 2
        System.out.println(Calendar.DATE); // 5
        System.out.println(c.get(Calendar.YEAR)); // 2018

        // 同时设置年月日
        c.set(9102, 12, 9);

        // add: 加/减
        c.add(Calendar.YEAR, -3);
        System.out.println(c.get(Calendar.YEAR)); // 9100

        // getTime:日历转日期date
        System.out.println(c.getTime()); // Tue Jan 09 19:49:20 CST 9100


    }
}
