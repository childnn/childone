package org.anonymous.thread.sleep;

import java.time.LocalDateTime;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.anonymous.thread.yield.YieldTest yield 是进入 RUNNABLE 状态.
 * @since 2021/1/25 15:22
 * 在当前线程调用 {@link java.lang.Thread#sleep} 方法时, 进入 BLOCKED 状态, 在其睡眠时间内, 该线程不会获得执行的机会,
 * 即使系统中没有其他可执行的线程, 处于 sleep 中的线程也不会执行, 因此 sleep 方法常用来 暂停 程序执行.
 */
public class SleepTest {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println("Current time: " + LocalDateTime.now());
            if (i == 3) {
                // the currently executing thread to sleep
                // 当前线程
                Thread.sleep(1000L);
            }
        }
    }

}
