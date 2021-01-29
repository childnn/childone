package org.anonymous.pc.WaitTest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2019年2月20日13:41:09
 * 在等待唤醒案例中，可以把 synchronized 同步代码换成 lock/unlock 吗？（不能）
 * 此代码不能实现从 synchronized 到 lock/unlock 的转化
 */
public class LockAndWait {
    public static void main(String[] args) {
        final Object obj = new Object();
        //多态，创建锁对象
        final Lock lock = new ReentrantLock();
        //使用 Thread 的构造方法： Thread(Runnable target, String name)
        new Thread(() -> { //这是Runnable接口的匿名实现类对象
            while (true) {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "买包子"); //非 Thread 子类对象，不能直接用getName()方法
                try {
                    //                    obj.wait(); //无限等待
                    //                    Thread.sleep(5000);
                    lock.wait();
                    System.out.println("吃包子");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }, "顾客：").start();
        new Thread("老板：") { //这是 Thread 的匿名子类对象
            @Override
            public void run() {
                while (true) {
                    System.out.println("做包子");
                    try {
                        Thread.sleep(5000); //睡，做包子中...
                        //                        System.out.println("包子做好了");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.lock();
                    System.out.println(getName() + "包子做好了"); //Thread 子类，可以直接使用getName() 方法
                    //                    obj.notify(); //唤醒
                    lock.notify();
                    lock.unlock();
                }
            }
        }.start();
    }
}
