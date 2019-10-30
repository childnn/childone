package jvm.oom;

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
