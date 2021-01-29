package org.anonymous.jvm.oom;

//import jdk.internal.misc.VM; // JDK 1.9 +

//import sun.misc.VM;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/20 11:00
 * 直接内存 DirectMemory 容量通过 -XX: MaxDirectMemorySize 指定, 如果不指定,
 * 则默认与 Java 堆最大值(-Xmx 指定) 一样.
 * 本例越过 DirectByteBuffer 类, 直接通过反射获取 Unsafe 实例进行内存分配(Unsafe 类
 * 的 getUnsafe() 方法限制了只有 引导类加载器 才会返回实例, 也就是设计者希望只有 rt.jar
 * 中的类才能使用 Unsafe 的功能). 因为, 虽然使用 DirectByteBuffer 分配内存也会抛出
 * 内存溢出异常, 但它抛出异常时并没有真正向操作系统申请分配内存, 而是通过计算机得知
 * 内存无法分配, 于是手动抛出异常, 真正申请分配内存的方法是 unsafe.allocateMemory().
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024 * 1024;

    /// 期望结果: java.lang.OutOfMemoryError
    // todo: 由 DirectMemory 导致的内存溢出, 一个明显的特征是在 Heap Dump 文件中
    // 不会看见明显的异常, 如果读者发现 OOM 之后 Dump 文件很小, 而程序中又直接
    // 或间接使用了 NIO, 就可以考虑这方面的原因.
    public static void main(String[] args) throws IllegalAccessException {
        //Field field = Unsafe.class.getDeclaredFields()[0];
        //field.setAccessible(true);
        //Unsafe unsafe = (Unsafe) field.get(null);
        //while (true) {
        //unsafe.allocateMemory(_1MB);
        //}
    }
}

class DirectMemOOM {
    /**
     * 我们使用 NIO 的时候经常需要使用 ByteBuffer 来读取或写入数据，这是一种基于 Channel(通道) 和 Buffer(缓冲区)的 I/O 方式，
     * 它可以使用 Native 函数库直接分配堆外内存，然后通过一个存储在 Java 堆里面的 DirectByteBuffer 对象作为这块内存的引用进行操作。
     * 这样在一些场景就避免了 Java 堆和 Native 中来回复制数据，所以性能会有所提高。
     * <p>
     * Java 允许应用程序通过 Direct ByteBuffer 直接访问堆外内存，许多高性能程序通过 Direct ByteBuffer
     * 结合内存映射文件（Memory Mapped File）实现高速 IO。
     * <p>
     * ByteBuffer.allocate(capability) 是分配 JVM 堆内存，属于 GC 管辖范围，需要内存拷贝所以速度相对较慢；
     * ByteBuffer.allocateDirect(capability) 是分配 OS 本地内存，不属于 GC 管辖范围，由于不需要内存拷贝所以速度相对较快；
     * 如果不断分配本地内存，堆内存很少使用，那么 JVM 就不需要执行 GC，DirectByteBuffer 对象就不会被回收，
     * 这时虽然堆内存充足，但本地内存可能已经不够用了，就会出现 OOM，本地直接内存溢出。
     * ----
     * VM Options：-Xms10m,-Xmx10m,-XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
     * 期望结果： java.lang.OutOfMemoryError: Direct buffer memory
     * ----
     * 解决方案
     * Java 只能通过 ByteBuffer.allocateDirect 方法使用 Direct ByteBuffer，因此，可以通过 Arthas 等在线诊断工具拦截该方法进行排查
     * 检查是否直接或间接使用了 NIO，如 netty，jetty 等
     * 通过启动参数 -XX:MaxDirectMemorySize 调整 Direct ByteBuffer 的上限值
     * 检查 JVM 参数是否有 -XX:+DisableExplicitGC 选项，如果有就去掉，因为该参数会使 System.gc() 失效
     * 检查堆外内存使用代码，确认是否存在内存泄漏；或者通过反射调用 sun.misc.Cleaner 的 clean() 方法来主动释放被 Direct ByteBuffer 持有的内存空间
     * 内存容量确实不足，升级配置
     */
    public static void main(String[] args) {
        //System.out.println("max direct memory: " + VM.maxDirectMemory() / 1024 / 1024 + "MB");
        //ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
