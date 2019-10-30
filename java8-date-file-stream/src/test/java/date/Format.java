package date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static date.Java8Date.DEFAULT_DATE_FORMAT;
import static date.Java8Date.DEFAULT_TIME_FORMAT;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/18 16:02
 */
public class Format {
    @Test
    public void test() {
        LocalTime now = LocalTime.now();
        System.out.println("now = " + now);
        LocalTime parse = LocalTime.parse("20:00:00", DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
        System.out.println("parse = " + parse);
        int i = now.compareTo(parse);
        System.out.println("i = " + i); // left < right 负数.
    }

    @Test
    public void test1() {
        String format = LocalDate.now().format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
        System.out.println("format = " + format);
    }
}
