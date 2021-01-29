package org.anonymous.localeformat;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/12 19:03
 */
public class HelloLocale {
    public static void main(String[] args) {
        Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        // locale = Locale.US; // 运行看结果
        ResourceBundle rb = ResourceBundle.getBundle("mess", locale);
        String hello = rb.getString("hello");
        System.out.println("hello = " + hello);
    }
}
