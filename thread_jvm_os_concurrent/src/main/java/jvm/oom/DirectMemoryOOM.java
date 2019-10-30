package jvm.oom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

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
        Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }
    }
}
