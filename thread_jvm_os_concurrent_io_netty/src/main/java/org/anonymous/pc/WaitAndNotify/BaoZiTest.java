package org.anonymous.pc.WaitAndNotify;

/**
 * 2019年2月21日11:06:59
 * 本例的关键在于：
 * 对于有不同任务的不同线程，使用同一锁资源，达到线程通信，并交替执行线程的目的
 * 这与D:\Develope\J2EE\Day06ThreadAndRunnable\src\cn\itheima\WaitTest\Test01WaitAndNotify.java
 * 的区别在于后者只有一对等待/唤醒 通信关系
 * 而今天所学的使用到了两对 等待/唤醒 通信关系（循环执行），更具有逻辑合理性，避免了线程之间的无序抢夺
 */
public class BaoZiTest {
    public static void main(String[] args) {
        final BaoZi baoZi = new BaoZi(); //同一个锁对象控制两个任务线程，两对通信关系
        new BaoZiPu(baoZi, "包子铺").start();
        new Eater(baoZi, "吃货").start();
    }
}
