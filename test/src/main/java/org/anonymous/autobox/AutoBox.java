package org.anonymous.autobox;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/15 14:14
 */
public class AutoBox {
    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;

        System.out.println(c == d); // 缓存
        System.out.println(e == f);
        System.out.println(c == (a + b)); // 包装类的 "==" 运算在不遇到算数运算的情况下不会自动拆箱
        System.out.println(c.equals(a + b));
        System.out.println(g == (a + b)); // 自动拆箱
        System.out.println(g.equals(a + b));

    }
}
