package org.anonymous.se.collection;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;

enum Season {

    SPRING,

    SUMMER,

    FALL,

    WINTER,
    ;
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/14 8:56
 */
public class EnumSetTest {

    @Test
    public void test1() {
        EnumSet<Season> seasons = EnumSet.allOf(Season.class);
        System.out.println("seasons = " + seasons);

        EnumSet<Season> seasons1 = EnumSet.noneOf(Season.class);
        System.out.println("seasons1 = " + seasons1);
        seasons1.add(Season.FALL);
        seasons1.add(Season.SPRING);
        System.out.println("seasons1 = " + seasons1);

        System.out.println("================================");

        EnumSet<Season> es3 = EnumSet.of(Season.SUMMER, Season.WINTER);
        System.out.println("es3 = " + es3);

        // EnumSet<Season> range = EnumSet.range(Season.WINTER, Season.SPRING); // java.lang.IllegalArgumentException: WINTER > SPRING
        EnumSet<Season> es4 = EnumSet.range(Season.SUMMER, Season.WINTER);
        System.out.println("es4 = " + es4);

        // es4 的 “补集”： es4 + es5 = Season 枚举类的全部枚举值
        EnumSet<Season> es5 = EnumSet.complementOf(es4);
        System.out.println("es5 = " + es5);
    }

    // copy 时, 必须保证 Collection 中的元素为同种类型的的枚举值
    @Test
    public void test2() {
        Collection c = new HashSet();
        c.clear();
        c.add(Season.FALL);
        c.add(Season.SPRING);

        // 复制 Collection 集合中所有元素来创建 EnumSet 集合
        EnumSet es = EnumSet.copyOf(c);
        System.out.println("es = " + es);

        c.add(""); // 添加非枚举类型

        // EnumSet.copyOf(c); // java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Enum
    }

}