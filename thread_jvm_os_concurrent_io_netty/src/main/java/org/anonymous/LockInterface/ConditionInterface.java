package org.anonymous.LockInterface;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2019年2月20日15:02:27
 * 等待唤醒的另一种实现
 * Condition 接口
 * 实现买卖的流畅进行
 * <p>
 * Lock接口的实现类 ReentrantLock 的方法：
 * Condition newCondition()：返回用来与此 Lock 实例一起使用的 Condition 实例。
 * Condition 实例用来 实现此线程等待/唤醒彼线程
 */
public class ConditionInterface {

    /**
     * @see ConditionTest#main
     */
    public static void main(String[] args) {
        // 定义锁对象
        final Lock lock = new ReentrantLock();
        // 定义两个Condition
        final Condition cond1 = lock.newCondition();
        final Condition cond2 = lock.newCondition();

        new Thread("老板：") {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        cond1.await(); // 老板在等待顾客来买包子
                        System.out.println(getName() + "正在做包子");
                        Thread.sleep(5000L);
                        System.out.println(getName() + "包子做好了");
                        cond2.signal(); // 包子做好，唤醒顾客
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }

                }
            }
        }.start();

        new Thread("顾客：") {
            @Override
            public void run() {
                while (true) {
                    System.out.println(getName() + "买包子");
                    try {
                        Thread.sleep(1000); // 减缓程序
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.lock();
                    try {
                        cond1.signal(); // 唤醒老板
                        cond2.await(); // 等待老板把包子做好
                        System.out.println(getName() + "吃包子");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("============");
                        lock.unlock();
                    }
                    try {
                        Thread.sleep(1000); // 吃完一次，睡一下
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
