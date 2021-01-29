package org.anonymous.localeformat.dateformat;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.text.DateFormat.FULL;
import static java.text.DateFormat.LONG;
import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.SHORT;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.text.DateFormat#getInstance()
 * @see java.text.DateFormat#getDateInstance 日期格式化, 只有日期, 没有时间. 可指定日期样式, Locale
 * @see java.text.DateFormat#getTimeInstance 时间格式化, 只有时间, 没有日期. 可指定 style, Locale
 * @see java.text.DateFormat#getDateTimeInstance 日期时间格式化, 日期和时间都有. 可指定 style, Locale
 * style 参数
 * @see java.text.DateFormat#FULL
 * @see java.text.DateFormat#LONG
 * @see java.text.DateFormat#MEDIUM
 * @see java.text.DateFormat#SHORT
 * @see java.text.DateFormat#DEFAULT
 * todo: 有时间可以比较 jdk 中的实现方式和其他开源代码中的实现方式有何不同
 * 比如: {@link org.apache.commons.lang3.time.FastDateFormat}
 * @since 2021/1/13 13:56
 */
public class DateFormatTest {

    public static void main(String[] args) {
        final Date dateToFormat = new Date();

        List<Locale> locales = Arrays.asList(Locale.CHINA, Locale.US);
        List<Integer> styles = Arrays.asList(SHORT, MEDIUM, LONG, FULL);

        Map<Locale, List<DateFormat>> dfMap = new HashMap<>();
        Map<Locale, List<DateFormat>> tfMap = new HashMap<>();
        Map<Locale, List<DateFormat>> dtfMap = new HashMap<>();

        locales.forEach(locale -> {
            List<DateFormat> dateFormats = styles.stream()
                    .map(style -> DateFormat.getDateInstance(style, locale)).collect(Collectors.toList());
            List<DateFormat> timeFormats = styles.stream()
                    .map(style -> DateFormat.getTimeInstance(style, locale)).collect(Collectors.toList());
            List<DateFormat> dateTimeFormats = styles.stream().map(style -> DateFormat.getDateTimeInstance(style, style, locale)).collect(Collectors.toList());

            dfMap.put(locale, dateFormats);
            tfMap.put(locale, timeFormats);
            dtfMap.put(locale, dateTimeFormats);
        });

        locales.forEach(locale -> {
            System.out.println(locale.getDisplayName());
            dfMap.get(locale).forEach(df -> System.out.println(df.format(dateToFormat)));
            tfMap.get(locale).forEach(tf -> System.out.println(tf.format(dateToFormat)));
            dtfMap.get(locale).forEach(dtf -> System.out.println(dtf.format(dateToFormat)));
            System.out.println("==============================================");
        });

    }

}