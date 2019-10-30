package date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/2 10:39
 */
public class PeriodTest {
    @Test
    public void test() {
        LocalDate electionDay = LocalDate.of(2020, Month.NOVEMBER, 3);
        LocalDate now = LocalDate.now();
        Period until = now.until(electionDay);
        System.out.println("until = " + until);
        Period between = Period.between(electionDay, now);
        System.out.println("between = " + between);

        int months = until.getMonths();
        System.out.println("months = " + months);
        int days = until.getDays();
        System.out.println("days = " + days);
    }
}
