package org.anonymous;

import org.junit.Test;

/**
 * @author child
 * 2019/7/15 9:18
 * 鸡兔同笼问题
 * <p>
 * x + y = 10    鸡兔共 10 只
 * 2x + 4y = 32  脚共计 32 只
 */
public class XY {

    @Test
    public void test() {
        int num = 10;
        // x: 鸡, y: 兔
        for (int x = 0; x <= num; x++) { // 鸡兔可能的数目都为 [0, 10]
            for (int y = 0; y < num; y++) {
                int a = x + y;
                int b = 2 * x + 4 * y;
                if (a == 10 && b == 32) {
                    System.out.println("鸡 = " + a + ", 兔 = " + b);
                }
            }
        }
    }

}
