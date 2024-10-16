package org.anonymous.jvm.gc;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/27 12:18
 */
public class MinorGC {

    private static final int _1MB = 1024 * 1024;

    /**
     * VM 参数；-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:PrintGCDetails -XX:SurvivorRatio=8
     */
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[2 * _1MB]; // 出现一次 Minor GC
    }

}
