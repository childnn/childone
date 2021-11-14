package test.basic.test;

import java.util.Calendar;

// 七、用程序判断2018年2月14日是星期几。Calendar
public class test009 {
    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        // 月份索引0-11，12个月
        c.set(2018, 1, 14);
        int i = c.get(Calendar.DAY_OF_WEEK);
//        System.out.println(i);
        System.out.println(getWeek(i));

    }

    public static String getWeek(int i) {
        String[] s = {"", "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return s[i];
    }
}

