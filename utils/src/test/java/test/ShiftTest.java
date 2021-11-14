package test;

import org.junit.jupiter.api.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/7/23 17:05
 */
public class ShiftTest {

    @Test
    public void test() {
        System.out.println(eq(1));
        System.out.println(eq(-4));
        System.out.println(-4 >>> 2);
    }

    boolean eq(int i) {
        System.out.println((-i << 4) - 1);
        System.out.println(i >>> 2);
        return i < 0 ? -i >> 2 - 1 == i >>> 2 : (i >> 2 == i >>> 2);
    }

}
