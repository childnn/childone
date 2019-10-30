package date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import static date.Java8Date.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @see LocalDateTime
 * @see LocalDate
 * @see LocalTime
 * @see Instant
 * @see ZoneId
 * @see DateTimeFormatter
 * @see Date
 * @see java.util.Calendar
 * @see java.text.DateFormat
 * @see java.text.SimpleDateFormat
 * @see java.util.TimeZone
 *
 * @since 2019/9/19 21:40
 */
public class Transformation {

    private static final ZoneId ZONEID = ZoneId.systemDefault();

    /**
     * zone ids.
     */
    @Test
    public void zoneIds() {
        for (Map.Entry<String, String> entry : ZoneId.SHORT_IDS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.err.println(key + ": " + value);
        }
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        //...
    }

    @Test
    public void dateToLocalDate() {
        Date date = new Date();
        System.out.println("date = " + date); // Fri Sep 20 09:10:17 CST 2019
        @SuppressWarnings("deprecation")
        String localeString = date.toLocaleString();
        System.out.println("localeString = " + localeString); // 2019-9-20 9:10:17
        Instant instant = date.toInstant();
        System.out.println("instant = " + instant + "\n"); // 2019-09-20T01:10:17.307Z

        // ZoneId zoneId = ZoneId.systemDefault();
        System.out.println("zoneId = " + ZONEID); // Asia/Shanghai
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONEID);
        System.out.println("localDateTime = " + localDateTime + "\n"); // 2019-09-20T09:10:17.307

        LocalDate localDate = localDateTime.toLocalDate();
        System.out.println("localDate = " + localDate); // 2019-09-20
        int year = localDate.getYear();
        System.out.println("year = " + year); // 2019
        Month month = localDate.getMonth();
        System.out.println("month = " + month); // SEPTEMBER
        int ordinal = month.ordinal();
        System.out.println("ordinal = " + ordinal); // start from 0.
        int value = month.getValue();
        System.out.println("value = " + value); // 实际的月份数字: 9.
        int dayOfYear = localDate.getDayOfYear();
        System.out.println("dayOfYear = " + dayOfYear); // 263
        int dayOfMonth = localDate.getDayOfMonth();
        System.out.println("dayOfMonth = " + dayOfMonth); // 20
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println("dayOfWeek = " + dayOfWeek); // FRIDAY
        int dayOfWeekValue = dayOfWeek.getValue();
        System.out.println("dayOfWeekValue = " + dayOfWeekValue + "\n"); // 5

        LocalTime localTime = localDateTime.toLocalTime();
        System.out.println("localTime = " + localTime); // 09:16:41.673
        String localTimeString = localTime.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
        System.out.println("localTimeString = " + localTimeString); // 09:18:19
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();
        System.out.println(hour + ":" + minute + ":" + second); // 9:20:2

    }

    /**
     * String --> LocalDateTime --> Date
     */
    @Test
    public void dateTime2Date() {
        LocalDateTime localDateTime = LocalDateTime.parse("2019-12-12 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("localDateTime = " + localDateTime);

        Instant instant = localDateTime.atZone(ZONEID).toInstant();
        Date date = Date.from(instant);
        System.out.println("date = " + date);

    }

    @Test
    public void parse() {
        LocalDate localDate = LocalDate.parse("2019-12-12");
        System.out.println("localDate = " + localDate);
    }

    /**
     * @see LocalDateTime#parse(java.lang.CharSequence, java.time.format.DateTimeFormatter)
     * @see DateTimeFormatter#parse(java.lang.CharSequence, java.time.temporal.TemporalQuery)
     * @see LocalDateTime#from(java.time.temporal.TemporalAccessor)
     */
    @Test
    public void test() {
        LocalDate localDate = LocalDate.now().minusDays(23L); // 可以自动根据加减的天数转换月份.
        System.out.println("localDate = " + localDate);
        String time = localDate + " 12:12:12";
        System.out.println("time = " + time);
        // 字符串转 LocalDateTime.
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT));
        System.out.println("dateTime = " + dateTime);


        LocalDateTime dateTime1 = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT).parse(time, new TemporalQuery<LocalDateTime>() {
            @Override
            public LocalDateTime queryFrom(TemporalAccessor temporal) {
                return LocalDateTime.from(temporal);
            }
        });


        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        System.out.println("date = " + date);


        String dateF = dateTime.toLocalDate().format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
        System.out.println("dateF = " + dateF);
        LocalTime localTime = dateTime.toLocalTime();
        String timeF = localTime.format(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
        System.out.println("timeF = " + timeF);
        LocalDateTime localDateTime = localTime.atDate(localDate);
        System.out.println("localDateTime = " + localDateTime);
        LocalDateTime localDateTime1 = localDate.atTime(localTime);
        System.out.println("localDateTime1 = " + localDateTime1);
    }

    @Test
    public void test1() {

    }

}
