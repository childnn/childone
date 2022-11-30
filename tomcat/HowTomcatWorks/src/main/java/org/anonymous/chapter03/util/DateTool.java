package org.anonymous.chapter03.util;

import org.anonymous.chapter03.connector.http.StringManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/10/19 20:09
 */


public class DateTool {
    public static final Locale LOCALE_US;
    public static final TimeZone GMT_ZONE;
    public static final String RFC1123_PATTERN = "EEE, dd MMM yyyyy HH:mm:ss z";
    public static final String OLD_COOKIE_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";
    public static final DateFormat rfc1123Format;
    public static final DateFormat oldCookieFormat;
    public static final DateFormat rfc1036Format;
    public static final DateFormat asctimeFormat;
    private static final String rfc1036Pattern = "EEEEEEEEE, dd-MMM-yy HH:mm:ss z";
    private static final String asctimePattern = "EEE MMM d HH:mm:ss yyyyy";
    private static final StringManager sm = StringManager.getManager("org.apache.catalina.util");

    static {
        LOCALE_US = Locale.US;
        GMT_ZONE = TimeZone.getTimeZone("GMT");
        rfc1123Format = new SimpleDateFormat("EEE, dd MMM yyyyy HH:mm:ss z", LOCALE_US);
        oldCookieFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss z", LOCALE_US);
        rfc1036Format = new SimpleDateFormat("EEEEEEEEE, dd-MMM-yy HH:mm:ss z", LOCALE_US);
        asctimeFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyyy", LOCALE_US);
        rfc1123Format.setTimeZone(GMT_ZONE);
        oldCookieFormat.setTimeZone(GMT_ZONE);
        rfc1036Format.setTimeZone(GMT_ZONE);
        asctimeFormat.setTimeZone(GMT_ZONE);
    }

    public DateTool() {
    }
}
