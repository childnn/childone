package org.anonymous.LockInterface;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.concurrent.locks.Condition
 * {@link #wait} {@link #notify()} {@link #notifyAll()} 必须与同步监视器同用,
 * 即与 synchronized 关键字同用.
 * Condition 即使替代上述三个方法, 与 {@link java.util.concurrent.locks.Lock} 同用
 * @see java.util.concurrent.locks.Lock#newCondition()
 * 为每个对象提供多个 等待集(wait-set).
 * @see java.util.concurrent.locks.Condition#await 类似 {@link #wait}, 当前线程等待
 * 直到其他线程调用该 Condition 的 {@link java.util.concurrent.locks.Condition#signal}
 * 或 {@link java.util.concurrent.locks.Condition#signalAll()}
 * @see java.util.concurrent.locks.Condition#awaitNanos
 * @see java.util.concurrent.locks.Condition#awaitUninterruptibly
 * @see java.util.concurrent.locks.Condition#awaitUntil
 * --
 * @see java.util.concurrent.locks.Condition#signal() 唤醒在此 Lock 对象上等待的单个线程.
 * 如果所有线程都在该 Lock 对象上等待, 则唤醒任意一个线程. 只有在当前线程放弃对该 Lock 对象的锁定后(调用 await() 方法)
 * 才可以执行被唤醒的线程.
 * @see java.util.concurrent.locks.Condition#signalAll() 唤醒在此 Lock 对象上等待的所有线程.
 * 只有当前线程放弃对该 Lock 对象的锁定后, 才可以执行被唤醒的线程.
 * @since 2021/1/25 16:22
 */
public class ConditionTest {

    static boolean exits = false;


    /**
     * @see ConditionInterface#main
     */
    public static void main(String[] args) {
        // 定义锁对象
        final Lock lock = new ReentrantLock();
        // 定义两个Condition
        final Condition cond = lock.newCondition();

        new Thread("老板：") {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        if (exits) {
                            cond.await(); // 老板在等待顾客来买包子
                        } else {
                            System.out.println(getName() + "正在做包子");
                            Thread.sleep(5000L);
                            exits = true;
                            System.out.println(getName() + "包子做好了");
                            cond.signal(); // 包子做好，唤醒顾客
                        }
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
                        if (!exits) {
                            cond.await(); // 等待老板把包子做好
                        } else {
                            System.out.println(getName() + "吃包子");
                            exits = false;
                            cond.signal();
                        }
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
