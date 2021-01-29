package org.anonymous.localeformat;

import java.util.Arrays;
import java.util.Locale;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.ResourceBundle
 * @see java.util.Locale 封装特定的国家/区域,语言环境
 * @see java.text.MessageFormat 格式化带占位符的字符串
 * baseName.properties
 * baseName_language.properites
 * baseName_language_country.properties
 * language 和 country 不可随意变化,必须为 Java 支持的语言/国家
 * @since 2021/1/12 18:47
 */
public class LocaleTest {

    public static void main(String[] args) {

        Locale[] locales = Locale.getAvailableLocales();
        Arrays.stream(locales).forEach(locale -> System.out.println(locale.getDisplayCountry() +
                " = " + locale.getCountry() + "\t"
                + locale.getDisplayLanguage() + " = " + locale.getLanguage()));
    }

}
