package org.anonymous.se.collection;

import java.util.LinkedHashSet;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/13 15:36
 */
public class LinkedHashSetTest {

    /**
     * This technique is particularly useful if a module takes a set on input,
     * copies it, and later returns results whose order is determined by that of
     * the copy.
     */
    public static void main(String[] args) {
        // 存储顺序与添加顺序严格一致
        LinkedHashSet<Object> set = new LinkedHashSet<>();
        set.add(1);
        set.add(3);
        set.add(2);
        System.out.println("set = " + set);
        // 如果元素已经存在, 重新添加的元素不会覆盖原有的值, 不会改变原位置.
        set.add(1);
        System.out.println("set = " + set);

        System.out.println("通话".hashCode() == "重地".hashCode());

        set.add("通话");
        set.add("重地");
        System.out.println("set = " + set);
    }

}
