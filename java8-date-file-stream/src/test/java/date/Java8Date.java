package date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @see LocalDate
 * @see LocalTime
 * @see LocalDateTime
 * @see MonthDay
 * @see Year
 * @see YearMonth
 * @see java.time.Clock 获取指定时区的当前日期,时间  System.currentTimeMillis() 的替代.
 * @see java.time.Duration 持续时间, 时间段
 * @see java.time.Instant 具体的时刻, 精确到纳秒.
 * {@link java.time.Instant#now()} 获取当前时刻
 * {@link java.time.Instant#now(java.time.Clock)} 获取 clock 对应的时刻
 * 有 minusXxx()/plusXxx() 方法
 * @see java.time.ZonedDateTime 一个时区化的日期,时间
 * @see java.time.ZoneId 时区
 * @see java.time.DayOfWeek 枚举
 * ---
 * @see java.time.format.DateTimeFormatter#format(java.time.temporal.TemporalAccessor)
 * @see java.time.LocalDateTime#format(java.time.format.DateTimeFormatter)  此方法调用的就是上一个方法
 * 以上两个方法作用完全一致: 将日期格式化为指定样式的字符串
 * @see java.time.LocalDateTime#parse
 * @see java.time.format.DateTimeFormatter#parse
 * @since 2019/8/26 13:07
 */
public class Java8Date {

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    @Test
    public void t1() {
        Clock clock = Clock.systemUTC();
        System.out.println("clock.instant() = " + clock.instant());
        System.out.println("clock.millis() = " + clock.millis());
        System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
        Duration d = Duration.ofSeconds(6000L);
        System.out.println("6000s = " + d.toMinutes() + "min");
        System.out.println("6000s = " + d.toHours() + "hours");
        System.out.println("6000s = " + d.toDays() + "days");

        // plus 6000s
        Clock c2 = Clock.offset(clock, d);
        System.out.println("c2.instant() = " + c2.instant());

        System.out.println("---------------instant---------------");
        Instant instant = Instant.now();
        System.out.println("instant = " + instant);
        Instant i2 = instant.plusSeconds(6000L);
        System.out.println("i2 = " + i2);
        Instant i3 = Instant.parse("2021-01-08T11:45:04.931Z");
        System.out.println("i3 = " + i3);
        Instant i4 = i3.plus(Duration.ofHours(5).plusMinutes(4));
        System.out.println("i4 = " + i4);
        Instant i5 = i4.minus(Duration.ofDays(5));
        System.out.println("i5 = " + i5);

        System.out.println("-----LocalDate--------");

        LocalDate localDate = LocalDate.now();
        System.out.println("localDate = " + localDate);
        localDate = LocalDate.ofYearDay(2014, 146);
        System.out.println("localDate = " + localDate);
        localDate = LocalDate.of(2014, Month.MAY, 21);
        System.out.println("localDate = " + localDate);

        System.out.println("-----LocalTime--------");

        LocalTime localTime = LocalTime.now();
        System.out.println("localTime = " + localTime);
        localTime = LocalTime.of(22, 33);
        System.out.println("localTime = " + localTime);
        localTime = LocalTime.ofSecondOfDay(5503);
        System.out.println("localTime = " + localTime);

        System.out.println("-----LocalDateTime--------");

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime = " + localDateTime);
        localDateTime = localDateTime.plusHours(25).plusMinutes(3);
        System.out.println("localDateTime + 25h3min = " + localDateTime);

        System.out.println("-----Year/YearMonth/MonthDay--------");
        Year year = Year.now();
        System.out.println("year = " + year);
        year = year.plusYears(5);
        System.out.println("year + 5y = " + year);
        YearMonth yearMonth = year.atMonth(10);
        System.out.println("yearMonth + 10M = " + yearMonth);
        yearMonth = yearMonth.plusYears(5).minusMonths(3);
        System.out.println("yearMonth + 5y - 3M = " + yearMonth);

        MonthDay md = MonthDay.now();
        System.out.println("md = " + md);
        md = md.with(Month.MAY).withDayOfMonth(23);
        System.out.println("md = " + md);
    }

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
