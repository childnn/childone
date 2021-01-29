package org.anonymous.jvm.oom;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/7/7 20:38
 */
public class UnableCreateNativeThread {
    /**
     * VM-args：-Xms10m
     * 期望结果：
     * java.lang.OutOfMemoryError: unable to create new native thread
     * -----
     * JVM 向 OS 请求创建 native 线程失败，就会抛出 Unable to create new native thread，常见的原因包括以下几类：
     * 线程数超过操作系统最大线程数限制（和平台有关）
     * 线程数超过 kernel.pid_max（只能重启）
     * native 内存不足；该问题发生的常见过程主要包括以下几步：
     * JVM 内部的应用程序请求创建一个新的 Java 线程；
     * JVM native 方法代理了该次请求，并向操作系统请求创建一个 native 线程；
     * 操作系统尝试创建一个新的 native 线程，并为其分配内存；
     * 如果操作系统的虚拟内存已耗尽，或是受到 32 位进程的地址空间限制，操作系统就会拒绝本次 native 内存分配；
     * JVM 将抛出 java.lang.OutOfMemoryError:Unable to create new native thread 错误。
     * ---
     * 解决方案
     * 想办法降低程序中创建线程的数量，分析应用是否真的需要创建这么多线程
     * 如果确实需要创建很多线程，调高 OS 层面的线程最大数：执行 ulimia-a 查看最大线程数限制，使用 ulimit-u xxx 调整最大线程数限制
     */
    public static void main(String[] args) {
        while (true) {
            new Thread(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
