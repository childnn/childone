package test.basic;

import org.junit.jupiter.api.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/9 14:59
 */
public class DoubleTest {

    @Test
    public void test() {
        int i = 1;
        int x = 2;
        System.out.println(getTotalPage(i, x));
        System.out.println(i / x);
        System.out.println(i % x);
    }

    private static int getTotalPage(int size, int pageSize) {
        // int div = Math.floorDiv(size, pageSize);
        // return size % pageSize == 0 ? div : div + 1;
        double v = 1.0D * size / pageSize;
        return (int) Math.ceil(v);
    }

}
