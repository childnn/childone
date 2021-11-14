package util;

import java.util.function.Supplier;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/13 17:31
 * @see java.time.format.DateTimeFormatter#ofPattern(String)
 */
public enum DateTimePattern implements Supplier<String> {

    /**
     * 最常用的模式: 年月日 时分秒
     *
     * @see java.time.LocalDateTime
     */
    PATTERN_DEFAULT("yyyy-MM-dd HH:mm:ss"),

    /**
     * 毫秒
     * 	millisecond (000-999)
     */
    PATTERN_MILLISECOND_3S("yyyy-MM-dd HH:mm:ss SSS"),

    /**
     * 微秒
     * 	microsecond (000000-999999)
     */
    PATTERN_MICROSECOND_6S("yyyy-MM-dd HH:mm:ss SSSSSS"),

    /**
     * 年月日
     *
     * @see java.time.LocalDate
     */
    PATTERN_YEAR_DATE_MONTH("yyyy-MM-dd"),

    /**
     * 时分秒
     *
     * @see java.time.LocalTime
     */
    PATTERN_HOUR_MINUTE_SECOND("HH:mm:ss"),

    /**
     * 时分
     */
    PATTERN_HOUR_MINUTE("HH:mm"),
    ;

    private final String pattern;

    DateTimePattern(String pattern) {
        this.pattern = pattern;
    }

    public String pattern() {
        return pattern;
    }

    @Override
    public String get() {
        return pattern;
    }

}
