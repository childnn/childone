package test;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/28 17:25
 */
public class Demo {
    @SafeVarargs // unsafe.
    static void m(List<String>... stringLists) {
        Object[] array = stringLists;
        List<Integer> tmpList = Collections.singletonList(42);
        array[0] = tmpList; // Semantically invalid, but compiles without warnings
        String s = stringLists[0].get(0); // Oh no, ClassCastException at runtime!
    }

    @Test
    public void test() {
        int a = 0x20;
        System.out.println("a = " + a);
    }

    public static void main(String[] args) {
        // divide-scale: 保留小数位数
        BigDecimal divide = BigDecimal.valueOf(1.5).divide(BigDecimal.valueOf(2), 0, RoundingMode.FLOOR);
        System.out.println("divide = " + divide);
        System.out.println(divide.equals(BigDecimal.ZERO));
    }

    @Test
    public void test1() {
        String s = "1313";
        System.out.println(Arrays.stream(s.split(",")).collect(Collectors.toList()));
    }

}
