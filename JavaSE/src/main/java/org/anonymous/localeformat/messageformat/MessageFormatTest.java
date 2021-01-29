package org.anonymous.localeformat.messageformat;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/12 19:09
 */
public class MessageFormatTest {

    public static void main(String[] args) {
        Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        // locale = Locale.US; // 运行看结果.

        ResourceBundle rb = ResourceBundle.getBundle("mess", locale);
        System.out.println(MessageFormat.format(rb.getString("msg"), rb.getString("name"),
                DateTimeFormatter.ofPattern(rb.getString("date_format")).format(LocalDate.now())));
    }

}
