package org.anonymous.se;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.lang.System#identityHashCode(Object) natvie 方法
 * 此方法无视 hashCode() 的重写, 而是根据该对象的地址计算得到的 hashCode 值.
 * 如果两个对象的 identityHashCode 值相同, 则两个对象绝对是同一个对象.
 * 任意两个对象的 identityHashCode 值总是不等
 * @since 2021/1/8 9:50
 */
public class SystemTest {
    public static void main(String[] args) {
        String h1 = "Hello";
        String h2 = "Hello";
        System.out.println(h1.hashCode() + "----------" + h2.hashCode());
        System.out.println(System.identityHashCode(h1) + "--------" + System.identityHashCode(h2));
    }
}
