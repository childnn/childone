package org.anonymous.concurrencyinpractice;

import org.anonymous.concurrencyinpractice.annotation.NotThreadSafe;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/12 12:02
 * NoVisibility 可能会一直保持循环, 因为对于读线程来说, ready 值可能永远不可见.
 * 甚至更奇怪的现象是, NoVisibility 可能会打印 0, 因为早在对 number 赋值前, 主线程
 * 就已经写入 ready 并使之对读取线程可见, 这是一种 "重排序(reordering)" 现象.
 * 在单个线程中, 只要重排序不会对结果产生影响, 那么就不能保证其中的操作一定按照程序写定
 * 的顺序执行--即使重排序对于其他线程来说会产生明显的影响.(这看起来可能是个失败的设计, 但是它意味着 JVM 能充分利用
 * 现代硬件的多核处理器的性能. 例如在没有同步的情况下, JMM-Java 存储模型 允许编译器重排序操作, 在寄存器中缓存数值,
 * 还允许 CPU 重排序, 并在处理器特有的缓存中缓存数值).
 * 当主线程写入 number, 然后在没有同步的情况下继续执行, 读线程看到的顺序可能与发生写入的顺序正好相反, 或者完全不同.
 * 在没有同步的情况下, 编译器, 处理器, 运行时安排操作的执行顺序可能完全出人意料. 在没有进行适当同步的多线程程序中, 尝试
 * 推断那些 "必然" 发生在内存中的动作时, 你总会判断错误.
 * ---
 * 只要数据需要被跨线程共享，就进行 恰当的同步.
 * ---
 * 过期数据
 *   当 read-thread 检查 ready 变量时, 它可能看到一个过期的值. 除非每次访问变量都同步的, 否则很可能得到变量的过期值.
 *   更坏的情况是, 过期既不会发生在全部变量上, 也不会完全不出现: 一个线程可能会得到一个变量最新的值, 但也可能得到另一个
 *   变量先前写入的过期值.
 */
@NotThreadSafe
public class NoVisibility {
    private static boolean ready;
    private static int number;

    /**
     * main-thread 和 read-thread 访问共享变量 ready, number.
     * main-thread 启动 read 线程, 然后把 number 的值设置为 42, ready 赋值为 true.
     * 读线程进行循环, 直到发现 ready 值变为 true, 然后打印 number 的值.
     * 虽然看起来 会输出 42, 但事实上, 它很有可能打印出 0, 或者根本不会终止. 这是因为它没有使用恰当的
     * 同步机制, 没能保证主线程写入 ready, number 的值对 read-thread 可见.
     */
    public static void main(String[] args) {
        new ReaderThread().start();
        // 主线程可能先写入 ready 值
        number = 42;
        ready = true;
    }

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

}
