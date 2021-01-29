package org.anonymous.jvm.oom;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/7/7 20:55
 */
public class ArraySizeLimit {
    // Integer.MAX_VALUE: java.lang.OutOfMemoryError: Requested array size exceeds VM limit
    // Integer.MAX_VALUE - x: java.lang.OutOfMemoryError: Java heap space
    public static void main(String[] args) {
        int[] ints = new int[Integer.MAX_VALUE - 10000];
    }
}
