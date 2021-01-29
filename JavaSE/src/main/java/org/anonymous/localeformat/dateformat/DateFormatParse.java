package org.anonymous.localeformat.dateformat;

import java.text.DateFormat;
import java.text.ParseException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.text.DateFormat#parse 方法可以把字符串解析成 Date 对象, 但是对于样式限制较严格
 * 使用 {@link java.text.SimpleDateFormat} 更灵活
 * @see org.anonymous.localeformat.dateformat.SimpleDateFormatTest
 * @since 2021/1/13 14:37
 */
public class DateFormatParse {

    public static void main(String[] args) throws ParseException {
        String s1 = "2014-12-12";
        String s2 = "2013年12月10日";

        System.out.println(DateFormat.getDateInstance().parse(s1));
        System.out.println(DateFormat.getDateInstance(DateFormat.LONG).parse(s2));
        // System.out.println(DateFormat.getDateInstance().parse(s2)); // ParseException: s2 为 LONG 样式日期

    }

}
