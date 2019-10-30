package date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @see LocalDate
 * @see LocalTime
 * @see LocalDateTime
 * @since 2019/8/26 13:07
 */
public class Java8Date {

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    @Test
    public void test() {
        LocalDate now = LocalDate.now();
        System.out.println("now = " + now); // 2019-09-17
        long l = now.toEpochDay();
        System.out.println("l = " + l); // 18156
        String s = now.toString();
        System.out.println("s = " + s); // 2019-09-17

        LocalDate localDate = LocalDate.ofEpochDay(0);
        System.out.println("localDate = " + localDate); // localDate = 1970-01-01
    }

    @Test
    public void test1() {
        LocalDate date = LocalDate.of(2011, 2, 2);
        System.out.println("date = " + date); // date = 2011-02-02
        LocalDate parse = LocalDate.parse("2019-09-09");
        System.out.println("parse = " + parse); // parse = 2019-09-09
    }

    @Test
    public void test2() {
        LocalTime time = LocalTime.of(0, 0, 0);
        LocalDate date = LocalDate.now().minusDays(1L);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        System.out.println("dateTime = " + dateTime);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now = " + now);
    }

    @Test
    public void test3() {
        String now = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(new Date());
        System.out.println("now = " + now);

        String now1 = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT).format(LocalDateTime.now());
        System.out.println("now1 = " + now1);
        String now2 = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
        System.out.println("now2 = " + now2);

    }

    @Test
    public void test4() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        System.out.println("dayOfWeek = " + dayOfWeek);
        DayOfWeek dayOfWeek1 = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).getDayOfWeek();
        System.out.println("dayOfWeek1 = " + dayOfWeek1);
    }


}
