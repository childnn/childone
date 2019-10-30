package org.anonymous;

import org.junit.Test;

/**
 * @author child
 * 2019/7/15 8:36
 * <p>
 * common divisor/measure: 公约数
 * common multiple: 公倍数
 * <p>
 * 最大公约数: 两个整数的公共约数是指两个整数的公共约数(能整除被除数的数) 中最大的数.
 */
public class CommonDivisor {

    /**
     * 辗转相除法: 求最大公约数
     */
    @Test
    public void commonDivisor() {
        int a = 12, b = 42;
        while (a != b) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }
        System.out.println("最大公约数为: " + a);
    }

    /**
     * 最小公倍数: 两个目标整数的乘积除以这两个整数的最大公约数.
     * 没有实际的算法实现
     */
    @Test
    public void commonMultiple() {

    }

}
