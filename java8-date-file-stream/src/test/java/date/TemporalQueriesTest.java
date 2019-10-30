package date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;

import static org.junit.Assert.assertEquals;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/2 8:24
 */
public class TemporalQueriesTest {

    @Test
    public void queries() {
        assertEquals(ChronoUnit.DAYS, LocalDate.now().query(TemporalQueries.precision()));
        //assertEquals(ChronoUnit.NANOS, LocalDate.now().query(TemporalQueries.precision()));
        //assertEquals(ZoneId.systemDefault(), LocalDate.now().query(TemporalQueries.zone()));
        //assertEquals(ZoneId.systemDefault(), LocalDate.now().query(TemporalQueries.zoneId()));
    }

}
