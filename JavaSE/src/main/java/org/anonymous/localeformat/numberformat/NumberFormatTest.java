package org.anonymous.localeformat.numberformat;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.text.Format
 * @see java.text.MessageFormat {@link java.text.MessageFormat#format} {@link java.text.MessageFormat#parse}
 * @see java.text.NumberFormat  {@link java.text.NumberFormat#format} {@link java.text.NumberFormat#parse}
 * @see java.text.DateFormat
 * 数值/日期和字符串互转.
 * @see java.text.NumberFormat#getInstance
 * @see java.text.NumberFormat#getCurrencyInstance 货币格式化, 可指定 Locale
 * @see java.text.NumberFormat#getIntegerInstance 整数格式化, 可指定 Locale
 * @see java.text.NumberFormat#getNumberInstance 通用数值格式化, 可指定 Locale
 * @see java.text.NumberFormat#getPercentInstance 百分数格式化, 可指定 Locale
 * 使用 NumberFormat 类将字符串解析成数值的意义不大, 因为包装类就具备该转换功能.
 * @since 2021/1/13 12:08
 */
public class NumberFormatTest {

    public static void main(String[] args) {
        double d = 1234000.567;
        List<Locale> locales = Arrays.asList(Locale.CHINA, Locale.JAPAN, Locale.GERMAN, Locale.US);

        // List<NumberFormat> numberInstance = locales.stream().map(NumberFormat::getNumberInstance).collect(Collectors.toList());
        // List<NumberFormat> percentInstance = locales.stream().map(NumberFormat::getPercentInstance).collect(Collectors.toList());
        // List<NumberFormat> currencyInstance = locales.stream().map(NumberFormat::getCurrencyInstance).collect(Collectors.toList());

        Map<Locale, NumberFormat> nfMap = new HashMap<>();
        Map<Locale, NumberFormat> pfMap = new HashMap<>();
        Map<Locale, NumberFormat> cfMap = new HashMap<>();
        locales.forEach(locale -> {
            nfMap.put(locale, NumberFormat.getNumberInstance(locale));
            pfMap.put(locale, NumberFormat.getPercentInstance(locale));
            cfMap.put(locale, NumberFormat.getCurrencyInstance(locale));
        });

        locales.forEach(locale -> {
            System.out.println(locale.getDisplayName());
            System.out.println("通用数值格式: " + nfMap.get(locale).format(d));
            System.out.println("百分比数值格式: " + pfMap.get(locale).format(d));
            System.out.println("货币数值格式: " + cfMap.get(locale).format(d));
        });
    }

}
