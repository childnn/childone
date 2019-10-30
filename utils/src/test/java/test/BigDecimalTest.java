package test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/11 20:57
 */
public class BigDecimalTest {

    @Test
    public void test() {
        BigDecimal b = new BigDecimal(0.1D);
        System.out.println("b = " + b);
        BigDecimal b1 = BigDecimal.valueOf(0.1D);
        System.out.println("b1 = " + b1);
        BigDecimal b2 = new BigDecimal(Double.toString(0.1D));
        System.out.println("b2 = " + b2);
    }
}
