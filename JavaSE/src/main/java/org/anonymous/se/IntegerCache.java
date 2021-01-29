package org.anonymous.se;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.anonymous.autobox.AutoBox
 * @since 2021/1/6 20:22
 */
public class IntegerCache {
    public static void main(String[] args) {
        Integer i1 = new Integer(3); // not cache
        Integer i2 = Integer.valueOf(3); // from cache
        Integer i3 = Integer.valueOf(3);
        int i4 = 3;
        System.out.println(i1 == i2);
        System.out.println(i2 == i3);
        System.out.println(i1 == i4);
        System.out.println(i3 == i4);
    }
}
