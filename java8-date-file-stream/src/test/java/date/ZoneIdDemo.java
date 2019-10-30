package date;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.TimeZone
 * @see ZoneId
 * @see ZoneOffset
 * @since 2020/3/10 14:06
 */
public class ZoneIdDemo {

    @Test
    public void zoneId() {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        System.out.println("zoneId = " + zoneId);
        ZoneOffset zoneOffset = ZoneOffset.of("+8");
        System.out.println("zoneOffset = " + zoneOffset);
    }

    @Test
    public void zero() {
        ZoneId zeroId = ZoneId.of("Europe/London");
        System.out.println("zeroId = " + zeroId);
        LocalDateTime now = LocalDateTime.now(zeroId);
        System.out.println("now = " + now);

        // Z: 零时区. 偏移量 zero
        ZoneOffset z = ZoneOffset.of("Z");
        System.out.println("z = " + z);
        ZoneOffset z0 = ZoneOffset.of("+0");
        System.out.println("z0 = " + z0);
        ZoneOffset z1 = ZoneOffset.of("-0");
        System.out.println("z1 = " + z1);
    }

    @Test
    public void trans() {
        // 参数一: 带转换的时间, 参数二: 该时间对应的偏移量, 参数三: 转换后的偏移量
        ZoneOffsetTransition zot = ZoneOffsetTransition.of(LocalDateTime.now().withNano(0), ZoneOffset.of("+8"), ZoneOffset.of("-6"));
        LocalDateTime dateTimeBefore = zot.getDateTimeBefore();
        System.out.println("dateTimeBefore = " + dateTimeBefore);
        LocalDateTime dateTimeAfter = zot.getDateTimeAfter();
        System.out.println("dateTimeAfter = " + dateTimeAfter);
        Duration duration = zot.getDuration();
        System.out.println("duration = " + duration.toMinutes());
    }
}
