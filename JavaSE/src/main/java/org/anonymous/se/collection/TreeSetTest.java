package org.anonymous.se.collection;

import java.util.TreeSet;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.TreeSet
 * @see java.util.TreeMap#put(java.lang.Object, java.lang.Object)
 * 1. 元素必须实现 {@link java.lang.Comparable} 接口
 * 2.
 * @since 2021/1/13 16:16
 */
public class TreeSetTest {

    public static void main(String[] args) {
        TreeSet<X> set = new TreeSet<>();
        X x = new X(6);
        set.add(x);

        // 重写 compareTo 方法, 对象自比也不等
        // 添加重复元素
        System.out.println(set.add(x));

        System.out.println("set = " + set);

        set.first().setI(9);
        System.out.println("set = " + set);
    }

}

class X implements Comparable<X> {

    private int i;

    public X(int i) {
        this.i = i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "X{" +
                "i=" + i +
                '}';
    }

    /**
     * 返回 1 表示不等.
     */
    @Override
    public int compareTo(X o) {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}