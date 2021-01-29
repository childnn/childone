package org.anonymous.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/20 10:01
 * Java 堆溢出:
 * Java 堆用于存储对象实例, 只要不断地创建对象, 并且保证 GC Roots 到对象之间有可达路径
 * 来避免垃圾回收机制清楚这些对象, 那么在对象数量达到最大堆的容量限制后就会产生内存溢出异常.
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 将堆的最小值 -Xms 参数与最大值 -Xmx 参数设置为一样即可避免堆自动扩展, 通过参数 -XX:+HeapDumpOnOutOfMemoryError
 * 可以让虚拟机在出现内存溢出异常时 Dump 出当前的内存堆转储快照以便事后进行分析.
 * Java 堆内存的 OOM 异常是实际应用中常见的内存溢出异常情况. 当出现 Java 堆内存溢出时, 异常堆栈信息 "java.lang.OutOfMemoryError"
 * 会跟着进一步提示 "Java heap space".
 * ----------------------------------------------
 * 2.2 原因分析
 * 请求创建一个超大对象，通常是一个大数组
 * 超出预期的访问量/数据量，通常是上游系统请求流量飙升，常见于各类促销/秒杀活动，可以结合业务流量指标排查是否有尖状峰值
 * 过度使用终结器（Finalizer），该对象没有立即被 GC
 * 内存泄漏（Memory Leak），大量对象引用没有释放，JVM 无法对其自动回收，常见于使用了 File 等资源没有回收
 * 2.3 解决方案
 * 针对大部分情况，通常只需要通过 -Xmx 参数调高 JVM 堆内存空间即可。如果仍然没有解决，可以参考以下情况做进一步处理：
 * 如果是超大对象，可以检查其合理性，比如是否一次性查询了数据库全部结果，而没有做结果数限制
 * 如果是业务峰值压力，可以考虑添加机器资源，或者做限流降级。
 * 如果是内存泄漏，需要找到持有的对象，修改代码设计，比如关闭没有释放的连接
 * <p>
 * ---------------------------------
 * 内存泄露和内存溢出
 * 内存溢出(out of memory)，是指程序在申请内存时，没有足够的内存空间供其使用，出现out of memory；
 * 比如申请了一个 Integer，但给它存了 Long 才能存下的数，那就是内存溢出。
 * 内存泄露( memory leak)，是指程序在申请内存后，无法释放已申请的内存空间，一次内存泄露危害可以忽略，
 * 但内存泄露堆积后果很严重，无论多少内存，迟早会被占光。
 * <p>
 * memory leak 最终会导致 out of memory！
 */
public class HeapOOM {

    // 期望效果: java.lang.OutOfMemoryError: Java heap space
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }

    static class OOMObject {

    }

}
