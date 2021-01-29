package org.anonymous.SynchronizedTest;

/**
 * 2019年2月19日16:19:48
 * 锁是用于通过多个线程控制对共享资源的访问的工具。
 * 通常，锁提供对共享资源的独占访问：一次只能有一个线程可以获取锁，并且对共享资源的所有访问都要求首先获取锁。
 * 同步方法：锁对象是 this（谁调用run，this就是谁）任务类对象调用run，this就是任务类对象自己
 * 证明 this 就是 任务类：
 * 1.在任务类的run 方法中打印 this，在测试类（主方法）中打印 任务类对象名，得到相同的地址
 * 2.在任务类的另一个方法中使用同步代码块而不使用同步方法（把 synchronized 放在方法中，不用来修饰方法）
 * 结果都可以说明：
 * 1.不同的线程，使用同一个任务类对象
 * 2.同步锁 this 就是任务类对象本身
 * 静态同步方法：static 属于类，锁对象是 类的字节码对象 .class 对象
 */
public class SynchronizedTest {
    public static void main(String[] args) {
        MyTarget1 mt = new MyTarget1(); //任务类对象
        System.out.println("mt:" + mt); //SynchronizedTest.MyTarget1@7c30a502
        Thread t1 = new Thread(mt);
        Thread t2 = new Thread(mt);
        t1.start();
        t2.start();
    }
}

class MyTarget1 implements Runnable { //任务类
    private int tickets = 100;

    @Override
    public void run() {
        System.out.println("this:" + Thread.currentThread().getName() + "-->" + this); //SynchronizedTest.MyTarget1@7c30a502
        while (true) { //注意这里的while不能写在下面的同步中，否则一旦有一个线程抓住了锁，就会一直执行循环，直到结束
            //            sell(); //调用同步方法
            see(); //同步代码块方法
        }
    }

    //输出 this 和实现类对象名可以发现，this就是Runnable的实现类对象
    //同步锁的锁对象就是 实现类对象自己
    private synchronized void sell() {
        if (tickets > 0) { //这里的if 不能换成while，否则只会有一个线程卖票（在同步中执行循环，线程抓住同步锁，就不会放开，一直到同步代码执行完毕）
            try {
                Thread.sleep(100); //这里可以选择异常抛出（抛给 run），但是run中也得try..catch，这里可以提前发现异常
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s正在卖出第%d张票\n", Thread.currentThread().getName(), tickets);
            tickets--;
        }
    }

    //证明 this 是任务类对象本身的第二种方式
    private void see() {
        synchronized (this) {
            if (tickets > 0) { //这里的if 不能换成while，否则只会有一个线程卖票（在同步中执行循环，线程抓住同步锁，就不会放开，一直到同步代码执行完毕）
                try {
                    Thread.sleep(100); //这里可以选择异常抛出（抛给 run），但是run中也得try..catch，这里可以提前发现异常
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s正在卖出第%d张票\n", Thread.currentThread().getName(), tickets);
                tickets--;
            }
        }
    }
}