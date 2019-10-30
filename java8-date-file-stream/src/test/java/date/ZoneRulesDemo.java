package date;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/10 14:18
 */
public class ZoneRulesDemo {

    // 获取指定时区 任意时间(可以是任意地区的任意时间)所使用的偏移量(时间变换规则)
    // 比如 根据美国芝加哥的时区规则(先得到对应的 ZoneRules), 中国在某一时间的时候, 芝加哥对应的时间是多少.
    @Test
    public void rule() {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        // 获取指定时区的时间变换规则
        ZoneRules rules = zoneId.getRules();
        // 指定时间
        LocalDateTime someTime = LocalDateTime.of(1690, 1, 1, 0, 0);
        ZoneOffset offset = rules.getOffset(someTime);
        System.out.println("offset = " + offset);

        ZoneOffset now = rules.getOffset(LocalDateTime.now());
        System.out.println("now = " + now);
    }

    // 同一个地区的不同时间, 时区规则可能不一样(动态获取 ZoneRules)
    @Test
    public void rule1() {
        // 使用当前时区, 当前时间, 得出 "当前瞬间" --- 此时 全世界的毫秒数完全一致(虽然时区不一)
        ZoneRules chinaRules = ZoneId.of("Asia/Shanghai").getRules();
        LocalDateTime chinaTime = LocalDateTime.now();
        ZoneOffset chinaOffset = chinaRules.getOffset(chinaTime);
        // 世界通用的 "瞬间"
        Instant instant = chinaTime.toInstant(chinaOffset);

        // 得到这一 瞬间  芝加哥的时间偏移量
        ZoneRules usaRules = ZoneId.of("America/Chicago").getRules();
        ZoneOffset usaOffset = usaRules.getOffset(instant);
        System.out.println("usaOffset = " + usaOffset);

        // 转换时间
        ZoneOffsetTransition china2usa = ZoneOffsetTransition.of(chinaTime.withNano(0), chinaOffset, usaOffset);
        chinaTime = china2usa.getDateTimeBefore();
        System.out.println("chinaTime = " + chinaTime);
        LocalDateTime usaTime = china2usa.getDateTimeAfter();
        System.out.println("usaTime = " + usaTime);
    }

}
