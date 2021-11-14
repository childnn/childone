package stream;

import date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/7/21 14:45
 */
public class MaxTest {

    @Test
    public void max() {
        Stream.of(
                n(LocalDateTime.of(1999, 1, 1, 0, 0)),
                n(LocalDateTime.of(2000, 1, 1, 0, 0)),
                n(LocalDateTime.of(1222, 1, 2, 0, 0)),
                n(null), // 属性空值
                null // entity 空
                // 第一个 nullsFirst 控制 entity 本身空值,
                // 第二个 nullsFirst 控制 属性空值
                // 可以注释相应的 of 中的值与 对应的 nullsFirst 看结果
        ).max(Comparator.nullsFirst(Comparator.comparing(DateEntity::getDate/*, Comparator.nullsFirst(Date::compareTo)*/)))
                .ifPresent(x -> System.out.println(x.getDate()));

    }

    DateEntity n(LocalDateTime ldt) {
        return ldt == null ? new DateEntity(null) : new DateEntity(DateUtil.localDateTimeToDate(ldt));
    }

}

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
class DateEntity {
    private Date date;
}
