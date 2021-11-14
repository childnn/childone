package util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.function.Supplier;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @see LocalDateTime
 * @see LocalDate
 * @see LocalTime
 * @see DateTimeFormatter
 * @see Duration
 * @see Period
 * @see Year
 * @see Month
 * @see MonthDay
 *
 * @author MiaoOne
 * @since 2021/3/17 18:20
 * @see cn.hutool.core.date.DateUtil
 */
public final class Java8DateUtil {

    public static void main(String[] args) {
        System.out.println(new Date());
    }

    private Java8DateUtil() {
        // Can't init.
    }

    //-------------------------------------
    // Formats
    //-------------------------------------

    /**
     * @return Current-time in pattern "yyyy-MM-dd".
     * @see DateTimePattern#PATTERN_YEAR_DATE_MONTH
     */
    public static String currentLocalDate() {
        return LocalDate.now().format(dateFormatter());
    }

    /**
     * @return Current-time in pattern "yyyy-MM-dd HH:mm:ss"
     * @see DateTimePattern#PATTERN_DEFAULT
     */
    public static String currentLocalDateTime() {
        return LocalDateTime.now().format(defaultDateTimeFormatter());
    }

    /**
     * @return Format the specified {@link Date} to pattern "yyyy-MM-dd HH:mm:ss".
     */
    public static String formatToDateTime(Date date) {
        return date2LocalDateTime(date).format(defaultDateTimeFormatter());
    }

    /**
     * @return Format the specified {@link Date} to pattern {@code pattern}.
     */
    public static String format(Date date, Supplier<String> pattern) {
        return date2LocalDateTime(date).format(DateTimeFormatter.ofPattern(pattern.get()));
    }

    /**
     * @return Format the specified {@link Date} to pattern {@code pattern}.
     */
    public static String format(Date date, DateTimePattern pattern) {
        return date2LocalDateTime(date).format(DateTimeFormatter.ofPattern(pattern.pattern()));
    }

    /**
     * @return Format the specified {@link LocalDateTime} to pattern {@code pattern}.
     * @see #format(LocalDateTime, DateTimePattern)
     */
    public static String format(LocalDateTime localDateTime, Supplier<String> pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern.get()));
    }

    /**
     * @return Format the specified {@link LocalDateTime} to pattern {@code pattern}.
     * @see #format(LocalDateTime, Supplier<String>)
     */
    public static String format(LocalDateTime localDateTime, DateTimePattern pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern.pattern()));
    }

    /**
     * @return Format the specified {@link LocalDateTime} to pattern {@code pattern}.
     */
    public static String format(LocalDate localDate, Supplier<String> pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern.get()));
    }

    /**
     * @return Format the specified {@link LocalDateTime} to pattern {@code pattern}.
     */
    public static String format(LocalTime localTime, Supplier<String> pattern) {
        return localTime.format(DateTimeFormatter.ofPattern(pattern.get()));
    }

    /**
     * @return Format the specified {@link LocalDateTime} to pattern "yyyy-MM-dd HH:mm:ss".
     */
    public static String formatToDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(defaultDateTimeFormatter());
    }

    /**
     * @return Format the specified {@link Date} to pattern "yyyy-MM-dd".
     */
    public static String formatToDate(Date date) {
        return date2LocalDateTime(date).format(dateFormatter());
    }

    /**
     * @return Format the specified {@link LocalDateTime} to pattern "yyyy-MM-dd".
     */
    public static String formatToDate(LocalDateTime localDateTime) {
        return localDateTime.format(dateFormatter());
    }

    /**
     * @return Format the specified {@link LocalDate} to pattern "yyyy-MM-dd".
     */
    public static String formatToDate(LocalDate localDate) {
        return localDate.format(dateFormatter());
    }


    //---------------------------------------
    // DateTimeFormatters
    //---------------------------------------

    /**
     * @return {@link DateTimeFormatter} of 'yyyy-MM-dd HH:mm:ss'.
     */
    public static DateTimeFormatter defaultDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(DateTimePattern.PATTERN_DEFAULT.pattern());
    }

    public static DateTimeFormatter ofPattern(DateTimePattern pattern) {
        return DateTimeFormatter.ofPattern(pattern.pattern());
    }

    public static DateTimeFormatter ofPattern(Supplier<String> pattern) {
        return DateTimeFormatter.ofPattern(pattern.get());
    }

    /**
     * @return {@link DateTimeFormatter} of 'yyyy-MM-dd'.
     */
    public static DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ofPattern(DateTimePattern.PATTERN_YEAR_DATE_MONTH.pattern());
    }


    //------------------------------------------------
    // Parses
    //------------------------------------------------

    /**
     * @param date date-string in pattern "yyyy-MM-dd"
     * @return {@link LocalDate}
     */
    public static LocalDate parse2LocalDate(String date) {
        return LocalDate.parse(date, dateFormatter());
    }

    /**
     * @param dateTime "yyyy-MM-dd HH:mm:ss"
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse2LocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, defaultDateTimeFormatter());
    }

    /**
     * Parse the specified {@code dateTime} using the giving {@code pattern}.
     *
     * @param dateTime the dateTime to parse
     * @param pattern  the pattern to use
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse2LocalDateTime(String dateTime, DateTimePattern pattern) {
        return LocalDateTime.parse(dateTime, ofPattern(pattern));
    }

    public static LocalDateTime parse2LocalDateTime(String dateTime, Supplier<String> pattern) {
        return LocalDateTime.parse(dateTime, ofPattern(pattern));
    }

    /**
     * 将给定字符串格式化为指定 {@code java.time} 日期类型
     * @param time the time to be parsed
     * @param pattern {@link DateTimePattern}
     * @param clazz the type of the time to be parsed
     * @param <T> the type of the except result
     * @return {@link LocalDateTime} {@link LocalDate} {@link LocalTime}
     */
    public static <T extends TemporalAccessor> T parse(String time, DateTimePattern pattern, Class<T> clazz) {
        return parse(time, (Supplier<String>) pattern, clazz);
    }

    /**
     * @see #parse(String, DateTimePattern, Class)
     */
    @SuppressWarnings("unchecked")
    public static <T extends TemporalAccessor> T parse(String time, Supplier<String> pattern, Class<T> clazz) {
        T result;
        final TemporalAccessor parse = ofPattern(pattern).parse(time);
        if (clazz == LocalDateTime.class) {
            result = (T) LocalDateTime.from(parse);
        } else if (clazz == LocalDate.class) {
            result = (T) LocalDate.from(parse);
        } else if (clazz == LocalTime.class) {
            result = (T) LocalTime.from(parse);
        } else {
            throw new IllegalArgumentException(String.format("Invalid sub-type of java.time.temporal.TemporalAccessor: [%s], " +
                    "please modify method [com.df.cbhis.utility.comm.Java8DateUtil#parse] if necessary.", clazz.getName()));
        }
        return result;
    }

    //--------------------------------
    // Converts
    //--------------------------------

    /**
     * Convert {@link LocalDateTime} to {@link Date}
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * Convert {@link LocalDate} to {@link Date}, with time 00:00:00
     */
    public static Date localDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().toInstant(ZoneOffset.of("+8")));
    }

    /**
     * Convert {@link Date} to {@link LocalDateTime}
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Convert {@link Date} to {@link LocalDate}
     */
    public static LocalDate date2LocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Convert {@link Date} to {@link LocalTime}
     */
    public static LocalTime date2LocalTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * Trim time, return yyyy-MM-dd pattern-Date
     */
    public static Date trimTime(Date date) {
        return localDate2Date(date2LocalDate(date));
    }

    /**
     *
     * @param date the original Date
     * @param localTime the new time to replace
     * @return replace the Date's time(HH:mm:ss) with the giving LocalTime
     */
    public static Date replaceTime(Date date, LocalTime localTime) {
        return localDateTime2Date(date2LocalDate(date).atTime(localTime));
    }

    /**
     *
     * @param date the original Date
     * @param localTime the new time to replace
     * @return replace the Date's time(HH:mm:ss) with the giving LocalTime
     */
    public static Date replaceTime(LocalDateTime date, LocalTime localTime) {
        return localDateTime2Date(date.toLocalDate().atTime(localTime));
    }

    //-----------------------------------------------------------
    // Plus(+)/Minus(-)
    //-----------------------------------------------------------

    /**
     * @see LocalDateTime#plusYears(long)
     * @see LocalDate#plusYears(long)
     */
    public static Date plusYears(Date date, long years) {
        return localDateTime2Date(date2LocalDateTime(date).plusYears(years));
    }

    /**
     * @see LocalDateTime#plusMonths(long)
     * @see LocalDate#plusMonths(long)
     */
    public static Date plusMonths(Date date, long months) {
        return localDateTime2Date(date2LocalDateTime(date).plusMonths(months));
    }

    /**
     * @see LocalDateTime#plusWeeks(long)
     * @see LocalDate#plusWeeks(long)
     */
    public static Date plusWeeks(Date date, long weeks) {
        return localDateTime2Date(date2LocalDateTime(date).plusWeeks(weeks));
    }

    /**
     * @see LocalDateTime#plusDays(long)
     * @see LocalDate#plusDays(long)
     */
    public static Date plusDays(Date date, long days) {
        return localDateTime2Date(date2LocalDateTime(date).plusDays(days));
    }

    /**
     * @see LocalDateTime#plusHours(long)
     * @see LocalTime#plusHours(long)
     */
    public static Date plusHours(Date date, long hours) {
        return localDateTime2Date(date2LocalDateTime(date).plusHours(hours));
    }

    /**
     * @see LocalDateTime#plusMinutes(long)
     * @see LocalTime#plusMinutes(long)
     */
    public static Date plusMinutes(Date date, long minutes) {
        return localDateTime2Date(date2LocalDateTime(date).plusMinutes(minutes));
    }

    /**
     * @see LocalDateTime#plusSeconds(long)
     * @see LocalTime#plusSeconds(long)
     */
    public static Date plusSeconds(Date date, long seconds) {
        return localDateTime2Date(date2LocalDateTime(date).plusSeconds(seconds));
    }

    /**
     * @see LocalDateTime#plusNanos(long)
     * @see LocalTime#plusNanos(long)
     */
    public static Date plusNanos(Date date, long nanos) {
        return localDateTime2Date(date2LocalDateTime(date).plusNanos(nanos));
    }

    /**
     * @see LocalDateTime#minusYears(long)
     * @see LocalDate#minusYears(long)
     */
    public static Date minusYears(Date date, long years) {
        return localDateTime2Date(date2LocalDateTime(date).minusYears(years));
    }

    /**
     * @see LocalDateTime#minusMonths(long)
     * @see LocalDate#minusMonths(long)
     */
    public static Date minusMonths(Date date, long months) {
        return localDateTime2Date(date2LocalDateTime(date).minusMonths(months));
    }

    /**
     * @see LocalDateTime#minusWeeks(long)
     * @see LocalDate#minusWeeks(long)
     */
    public static Date minusWeeks(Date date, long weeks) {
        return localDateTime2Date(date2LocalDateTime(date).minusWeeks(weeks));
    }

    /**
     * @see LocalDateTime#minusDays(long)
     * @see LocalDate#minusDays(long)
     */
    public static Date minusDays(Date date, long days) {
        return localDateTime2Date(date2LocalDateTime(date).minusDays(days));
    }

    /**
     * @see LocalDateTime#minusHours(long)
     * @see LocalTime#minusHours(long)
     */
    public static Date minusHours(Date date, long hours) {
        return localDateTime2Date(date2LocalDateTime(date).minusHours(hours));
    }

    /**
     * @see LocalDateTime#minusMinutes(long)
     * @see LocalTime#minusMinutes(long)
     */
    public static Date minusMinutes(Date date, long minutes) {
        return localDateTime2Date(date2LocalDateTime(date).minusMinutes(minutes));
    }

    /**
     * @see LocalDateTime#minusSeconds(long)
     * @see LocalTime#minusSeconds(long)
     */
    public static Date minusSeconds(Date date, long seconds) {
        return localDateTime2Date(date2LocalDateTime(date).minusSeconds(seconds));
    }

    /**
     * @see LocalDateTime#minusNanos(long)
     * @see LocalTime#minusNanos(long)
     */
    public static Date minusNanos(Date date, long nanos) {
        return localDateTime2Date(date2LocalDateTime(date).minusNanos(nanos));
    }

    /**
     * 比较两个日期的 年月日 是否相同
     */
    public static boolean isSameDay(Date day1, Date day2) {
        if (day1 == null) {
            return day2 == null;
        }
        if (day2 == null) {
            return false;
        }
        return date2LocalDate(day1).equals(date2LocalDate(day2));
    }

}
