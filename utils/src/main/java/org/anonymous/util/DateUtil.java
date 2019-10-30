package org.anonymous.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author liupeng
 * @create 2019-09-09
 * @see java.time.Year
 * @see java.time.YearMonth
 * @see java.time.Month
 * @see java.time.MonthDay
 * @see java.time.DayOfWeek
 * @see java.time.Duration
 * @see Instant
 * @see java.time.Clock
 **/
public class DateUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_ZONE = "GMT+8";
    public static final String BEGIN_TIME_OF_DAY = " 00:00:00";
    public static final String END_TIME_OF_DAY = " 23:59:59";

    /**
     * @author liupeng
     * @date 2019/9/9
     * @param num
     * @return java.lang.String
     * @DESCRIPTION: 返回指定日期的开始时间
     */
    public static String dayStart(int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,num);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String start = sdf.format(calendar.getTime());
        return start;
    }

    /**
     * @author liupeng
     * @date 2019/9/9
     * @param num
     * @return java.lang.String
     * @DESCRIPTION: 返回指定日期的结束时间
     */
    public static String dayEnd(int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,num);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String end = sdf.format(calendar.getTime());
        return end;
    }

    public static String getDay(int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,num);
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
        String date = sdf.format(calendar.getTime());
        return date;
    }

    /**
     * yyyy-MM-dd.
     */
    public static String nowLocalDate() {
        return LocalDate.now().format(shortDateTimeFormatter());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String nowLocalDateTime() {
        return LocalDateTime.now().format(defaultDateTimeFormatter());
    }

    /**
     * {@link Date} transfer to {@link LocalDateTime}
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * yyyy-MM-dd HH:mm:ss.
     */
    public static String formatToDateTime(Date date) {
        return date2LocalDateTime(date).format(defaultDateTimeFormatter());
    }

    /**
     * yyyy-MM-dd HH:mm:ss.
     */
    public static String formatToDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(defaultDateTimeFormatter());
    }

    /**
     * @return {@link DateTimeFormatter} of 'yyyy-MM-dd HH:mm:ss'.
     */
    public static DateTimeFormatter defaultDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
    }

    /**
     * @return {@link DateTimeFormatter} of 'yyyy-MM-dd'.
     */
    public static DateTimeFormatter shortDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(SHORT_DATE_FORMAT);
    }

    /**
     * yyyy-MM-dd.
     */
    public static String formatToDate(Date date) {
        return date2LocalDateTime(date).format(shortDateTimeFormatter());
    }

    /**
     * yyyy-MM-dd.
     */
    public static String formatToDate(LocalDateTime localDateTime) {
        return localDateTime.format(shortDateTimeFormatter());
    }

    /**
     *
     * @param date "yyyy-MM-dd"
     * @return {@link LocalDate}
     */
    public static LocalDate parse2LocalDate(String date) {
        return LocalDate.parse(date, shortDateTimeFormatter());
    }

    /**
     *
     * @param dateTime "yyyy-MM-dd HH:mm:ss"
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse2LocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, defaultDateTimeFormatter());
    }

    public static void main(String[] args) {
        Date d = new Date();
        String date = nowLocalDate();
        System.out.println("date = " + date);
        String dateTime = nowLocalDateTime();
        System.out.println("dateTime = " + dateTime);
        LocalDateTime localDateTime = date2LocalDateTime(d);
        System.out.println("localDateTime = " + localDateTime);
        String date1 = formatToDate(d);
        System.out.println("date1 = " + date1);
        String date2 = formatToDate(LocalDateTime.now());
        System.out.println("date2 = " + date2);
        String dateTime1 = formatToDateTime(d);
        System.out.println("dateTime1 = " + dateTime1);
        String dateTime2 = formatToDateTime(LocalDateTime.now());
        System.out.println("dateTime2 = " + dateTime2);

        System.out.println("=========================");

        Date date3 = localDateTimeToDate(LocalDateTime.now());
        System.err.println(date3);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

}
