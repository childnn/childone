package org.anonymous.demo;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/29 10:27
 * 左移: 直接低位补零
 * 右移: 区分算术右移, 逻辑右移
 * 算数右移: 高位根据符号补 0 或 1
 * 逻辑右移: 高位补零
 */
public class SomeTest {
    public static void main(String[] args) {
        // 11110
        System.out.println(30 << 1);
        System.out.println(30 >> 2);
    }
}
