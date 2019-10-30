package date;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/2 7:39
 * 员工每月发两次工资，发薪日每月 15 日和最后一日；
 * 如果发薪日为周末，则提前至周五。
 */
public class PayDayAdjuster implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        LocalDate date = LocalDate.from(temporal);
        int day;
        if (date.getDayOfMonth() < 15) {
            day = 15;
        } else {
            day = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        }
        date = date.withDayOfMonth(day);

        if (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            date = date.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        }
        return temporal.with(date);
    }

    @Test
    public void test() {
        DayOfWeek dayOfWeek = LocalDate.of(2017, Month.JULY, 1).getDayOfWeek();
        System.out.println("dayOfWeek = " + dayOfWeek);
        DayOfWeek dayOfWeek1 = LocalDate.of(2017, Month.JULY, 31).getDayOfWeek();
        System.out.println("dayOfWeek1 = " + dayOfWeek1);

    }

    @Test
    public void payDay() {
        PayDayAdjuster adjuster = new PayDayAdjuster();

        IntStream.rangeClosed(1, 14)
                .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
                .forEach(date -> assertEquals(14, date.with(adjuster).getDayOfMonth()));

        IntStream.rangeClosed(15, 31)
                .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
                .forEach(date -> assertEquals(31, date.with(adjuster).getDayOfMonth()));
    }

    // 方法引用
    @Test
    public void payDay1() {
        IntStream.rangeClosed(1, 14)
                .mapToObj(day -> LocalDate.of(2017, Month.JULY, day))
                .forEach(date -> assertEquals(14, date.with(Adjusters::adjustInto).getDayOfMonth()));
    }

    static class Adjusters {
        public static Temporal adjustInto(Temporal input) {
            // 可以把类中的方法逻辑写在这里
            return new PayDayAdjuster().adjustInto(input);
        }
    }
}
