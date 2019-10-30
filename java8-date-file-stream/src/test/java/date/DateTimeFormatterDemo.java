package date;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see DateTimeFormatter#ofPattern(String)
 * @see DateTimeFormatter#ofPattern(String, Locale)
 * 关于 pattern 中各个字母的含义, 参见 {@code java.time.format.DateTimeFormatterBuilder#FIELD_MAP}
 * @since 2020/1/7 13:47
 */
public class DateTimeFormatterDemo {

    @Test
    public void test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
//        String s = formatter.format(LocalDateTime.now());
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        ZonedDateTime time = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        String s = formatter.format(time);
        System.out.println("s = " + s);
    }

    /**
     * @see Locale#CHINESE
     * @see Locale#PRC {@link Locale#SIMPLIFIED_CHINESE}
     * @see Locale#CHINA {@link Locale#SIMPLIFIED_CHINESE}
     * @see Locale#SIMPLIFIED_CHINESE
     * @see Locale#TRADITIONAL_CHINESE
     * @see Locale#TAIWAN {@link Locale#TRADITIONAL_CHINESE}
     */
    @Test
    public void tes2() {
        Locale chinese = Locale.CHINESE;
        System.out.println("chinese = " + chinese);
        Locale simplifiedChinese = Locale.SIMPLIFIED_CHINESE;
        System.out.println("simplifiedChinese = " + simplifiedChinese);
        Locale traditionalChinese = Locale.TRADITIONAL_CHINESE;
        System.out.println("traditionalChinese = " + traditionalChinese);
        Locale aDefault = Locale.getDefault();
        System.out.println("aDefault = " + aDefault);
    }

    @Test
    public void test1() {
        Locale defaultLocale = Locale.getDefault();
        System.out.println("defaultLocale = " + defaultLocale);
        Locale chinese = Locale.CHINESE;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss:n zzz", defaultLocale);
//        String s = formatter.format(LocalDateTime.now());
        String s = formatter.format(ZonedDateTime.now());
        System.out.println("s = " + s);
    }

}
