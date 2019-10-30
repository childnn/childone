package org.anonymous.util;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/27 11:11
 */
public class DecimalFormatTest {
    @Test
    public void test() {
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(new BigDecimal("2.222222"));
        System.out.println("format = " + format);
    }
}
