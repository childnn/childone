package org.anonymous.thread;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see Thread#yield() 与 sleep 类似, 但不会阻塞当前线程, 只是将当前线程转入就绪(RUNNABLE)状态(sleep 是 BLOCKED 状态)
 * 让当前线程暂停一下, 让系统的线程调度器重新调度一次, 完全可能情况是: 当某个线程调用了 yield() 方法暂停后,
 * 线程调度器又将其调度出来重新执行.
 * 实际上, 当某个线程调用 yeild() 方法暂停后, 只有优先级与当前线程相同或优先级更高的处于 RUNNABLE 状态的线程才会
 * 获得执行的机会.
 * @see SleepTest
 * @since 2021/1/25 15:27
 */
public class YieldTest extends Thread {

    public YieldTest(String name) {
        super(name);
    }

    // 可以注释设置 优先级的两行代码, 看结果
    // 在多核机器上, yield 并不明显.
    public static void main(String[] args) {
        final YieldTest t1 = new YieldTest("高级");
        t1.setPriority(Thread.MAX_PRIORITY); //
        t1.start();

        final YieldTest t2 = new YieldTest("低级");
        t2.setPriority(Thread.MIN_PRIORITY); //
        t2.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(getName() + " " + i);

            if (20 == i) {
                Thread.yield();
            }
        }
    }

}
