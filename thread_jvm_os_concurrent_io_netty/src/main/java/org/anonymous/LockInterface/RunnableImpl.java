package org.anonymous.LockInterface;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 2019年2月19日15:43:54
 * 解决线程安全问题的第三种方式：使用 Lock 锁，更为面向对象的同步方法
 * java.util.concurrent.Locks.Lock接口
 * Lock 实现提供了比使用 synchronized 方法和语句可获得的更广泛的锁定操作
 * Lock接口中的方法：
 * void lock()
 * void unlock()
 * Lock 接口的实现类：ReentrantLock
 * 使用步骤：
 * 1.在成员位置创建一个 ReentrantLock 对象
 * 2.在可能会出现安全问题的代码前调用 Lock 接口中的方法 lock 获取锁
 * 3.在可能会出现安全问题的代码后调用 Lock 接口中的方法 unlock 释放锁
 */
public class RunnableImpl implements Runnable { //任务类，分配任务
    //在成员位置创建一个ReentrantLock对象
    private final Lock lock = new ReentrantLock(); //
    //定义一个多线程共享资源
    private int ticket = 100;

    //设置线程任务：卖票
    @Override
    public void run() {
        do {
            lock.lock(); //锁住。注意：锁永远只能放在循环里面，否则直到该线程执行完毕，其他线程也抢不到锁
            //            if (ticket > 0) {
            //提高安全问题出现的概率，让程序睡眠
            try {
                Thread.sleep(500);
                //票存在，卖票，ticket--
                System.out.printf("%s正在卖出第%d张票\n", Thread.currentThread().getName(), ticket);
                ticket--;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally { //无论程序是否异常，finally 中的代码都会执行，提高程序的效率（如果出现异常却没有释放锁，会导致其他同步线程无法执行）
                //在可能会出现安全问题的代码后调用Lock接口的 unlock() 释放锁
                lock.unlock(); //释放锁
            }
            //            }
        } while (ticket > 0);
    }
}

//测试类
class Test {
    public static void main(String[] args) {
        RunnableImpl run = new RunnableImpl();
        Thread t1 = new Thread(run);
        Thread t2 = new Thread(run);
        t1.start();
        t2.start();
    }
}