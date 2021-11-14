package test;

import org.junit.jupiter.api.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/7/26 13:51
 */
public class FloatTest {

    /**
     * 十进制的 0.1 无法用 二进制 表示
     * 参见 notes/notes/scratches/notes/notes.txt
     * 十进制 0.1 转换成二进制后, 会变成 0.00011001100...(1100循环) 这样的循环小数.
     * 这和无法用十进制小数表示 1/3 是一样的道理.
     */
    @Test
    void test() {
        float f = 0F;
        for (int i = 0; i < 100; i++) {
            f += 0.1F;
        }
        System.out.println("f = " + f);

        float f1 = 0F;
        for (int i = 0; i < 100; i++) {
            f1 += 1F;
        }
        System.out.println("f1 = " + f1 / 10);

    }

}
