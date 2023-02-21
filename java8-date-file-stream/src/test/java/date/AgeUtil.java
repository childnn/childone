package date;

import org.junit.Test;

import java.time.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.time.Duration A time-based amount of time, such as '34.5 seconds'.
 * @see java.time.Period A date-based amount of time in the ISO-8601 calendar system, such as '2 years, 3 months and 4 days'.
 * @since 2022/09/27
 */
public class AgeUtil {

    @Test
    public void period() {
        Period period = Period.between(LocalDate.of(2022, Month.APRIL, 21), LocalDate.now());
        int years = period.getYears();
        System.out.println("years = " + years);
        int months = period.getMonths();
        System.out.println("months = " + months);
        int days = period.getDays();
        System.out.println("days = " + days);
    }

    @Test
    public void duration() {
        Duration duration = Duration.between(
                LocalDateTime.now().plusSeconds(100L), LocalDateTime.now());
        long seconds = duration.getSeconds();
        System.out.println("seconds = " + seconds);
        int nano = duration.getNano();
        System.out.println("nano = " + nano);
    }

}
