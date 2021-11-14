package stream;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/6/7 9:35
 */
public class ReduceTest {

    @Test
    public void test() {
        BigDecimal reduce = Stream.of(BigDecimal.valueOf(1L), BigDecimal.valueOf(2L), BigDecimal.valueOf(2L))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("reduce = " + reduce);
    }

}
