package jvm.gc;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/27 12:27
 */
public class PretenureSizeThreshold {

    private static final int _1MB = 1024 * 1024;

    /**
     * VM 参数： -verbose:gc -Xms10M -Xmx20M -Xmn10M -XX:PrintGCDetails -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=3145728 (3M)
     * 虚拟机提供了一个 -XX:PretenureSizeThreshold 参数, 令大于这个设置值的对象直接在
     * 老年代分配. 这样做的目的是避免在 Eden 区及两个 Survivor 区之间发生大量的内存复制
     * 执行一下方法后, Eden 空间几乎没有被使用, 而老年代 10MB 空间被使用了 40%, 也就是 4MB 的
     * allocation 对象直接分配在老年代中, 这是因为 PretenureSizeThreshold 被设置为 3MB, 因此超过 3MB 的对象
     * 都会直接在老年代进行分配.
     * PretenureSizeThreshold 参数只对 Serial 和 ParNew 两款收集器有效.
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB]; // 直接分配在老年代中
    }
}
