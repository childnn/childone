package test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @see java.math.BigDecimal#BigDecimal(double) The results of this constructor can be somewhat unpredictable.
 * 如果为 double 类型, 则应使用 {@link java.math.BigDecimal#valueOf(double)}
 * @since 2019/9/11 20:57
 * 如果 {@link java.math.BigDecimal#BigDecimal(String)} 构造器的结果是可预知的, 为 String 类型数字, 可以使用构造器
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
