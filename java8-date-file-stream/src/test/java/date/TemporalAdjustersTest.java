package date;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/2 7:22
 */
public class TemporalAdjustersTest {

    @Test
    public void adjusters() {
        LocalDateTime start = LocalDateTime.of(2017, Month.FEBRUARY, 2, 11, 30);
        System.out.println("start = " + start);
        DayOfWeek dayOfWeek = start.getDayOfWeek();
        System.out.println("dayOfWeek = " + dayOfWeek);
        LocalDateTime end = start.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("end = " + end);

        System.out.println("===============================");

        end = start.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
        System.out.println("end = " + end);

        System.out.println("===============================");

        end = start.with(TemporalAdjusters.previousOrSame(DayOfWeek.THURSDAY));
        System.out.println("end = " + end);
    }

}
