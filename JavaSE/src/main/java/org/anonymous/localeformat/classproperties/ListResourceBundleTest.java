package org.anonymous.localeformat.classproperties;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.ResourceBundle.Control#newBundle(java.lang.String, java.util.Locale, java.lang.String, java.lang.ClassLoader, boolean)
 * 如果系统中同时存在 properties 文件, class 文件, 将会以类文件为主, 而不会调用资源文件.
 * 对于简体中文的 Locale, ResourceBundle 搜索资源文件的顺序是:
 * -- baseName_zh_CN.class
 * -- baseName_zh_CN.properties
 * -- baseName_zh.class
 * -- baseName_zh.properties
 * -- baseName.class
 * -- baseName.properties
 * 按上述顺序, 如果前面的文件不存在, 才会使用下一个, 直到都不存在而抛出异常.
 * @since 2021/1/13 11:36
 */
public class ListResourceBundleTest {

    public static void main(String[] args) {
        Locale locale = Locale.getDefault(Locale.Category.FORMAT);

        String name = MyMessage_zh_CN.class.getName();
        // name = MyMessage_en_US.class.getName();
        ResourceBundle rb = ResourceBundle.getBundle(name, locale);
        System.out.println(MessageFormat.format(rb.getString("msg"), rb.getString("name"),
                DateTimeFormatter.ofPattern(rb.getString("date_format")).format(LocalDate.now())));

    }

}
