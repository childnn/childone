package jvm.gc;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/21 15:58
 * 引用计数算法: Reference Counting
 * 给对象添加一个引用计数器, 每当有一个地方引用它时, 计数器值就加一; 当引用失效时, 计数器值就减一;
 * 任何时刻计数器为 0 的对象就是不可能再被使用的.
 * 主流的 Java 虚拟机里没有选用引用计数算法来管理内存, 其中最主要的原因是它很难解决对象之间
 * 循环引用的问题.
 */
public class ReferenceCountingGC {

    static final int _1MB = 1024 * 1024;
    Object instance = null;
    // 这个成员属性的唯一意义就是占点内存, 以便在 GC 日志中看清楚是否被回收过
    byte[] bigSize = new byte[2 * _1MB];

    // 此方法执行后, objA 和 objB 会不会被 GC 呢?
    // 事实上虚拟机并不是通过引用计数算法来判断对象是否存活的.
    static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        // 假设在这行发生 GC, objA 和 objB 是否能被回收?
        System.gc();
    }

}
