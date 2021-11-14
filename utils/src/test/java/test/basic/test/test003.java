package test.basic.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// 三、用程序判断2018年2月14日是星期几。
public class test003 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = sdf.parse("2018年2月14日");
        System.out.println(date);
        // Calendar 类(抽象类)
        Calendar c = Calendar.getInstance();
        // month从0开始至11，代表1-12
        c.set(2018, 1, 14);
//        c.set(Calendar.YEAR, 2018);
//        c.set(Calendar.MONTH, 1);
//        c.set(Calendar.DATE, 14);
//        System.out.println(c.getTime());
        System.out.println(c.get(Calendar.DAY_OF_MONTH)); // 月中第十四天
        int i = c.get(Calendar.DAY_OF_WEEK); //
        System.out.println(i); // 周中第四天
        System.out.println(getWeek(i));
    }


    public static String getWeek(int i) {
        // 一周7天，周日开始，0 号索引没有
        String[] s = {"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return s[i];
    }
}
