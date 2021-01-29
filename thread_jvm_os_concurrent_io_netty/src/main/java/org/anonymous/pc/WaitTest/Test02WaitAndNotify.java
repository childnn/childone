package org.anonymous.pc.WaitTest;

/**
 * 2019年2月19日16:38:04
 * 进入到 TimeWaiting (计时等待)的方式：
 * 1.sleep(Long ms):“抱着锁睡”，若有锁对象，不会释放锁资源。到时间，该线程进入 Runnable/Blocked 状态
 * 2.wait(Long ms)：“不抱锁睡”，在等待时，释放锁资源。可以在到时间之前被 【其他线程】的 notify/notifyAll 唤醒，或者在到时间之后，自动醒来
 */
public class Test02WaitAndNotify {
    public static void main(String[] args) {
        //定义共用锁对象
        /*final */
        Object obj = new Object(); //对象在堆中
        //两个顾客线程
        new Thread("顾客1：") {
            @Override
            public void run() {
                while (true) {
                    synchronized (obj) {
                        System.out.println(Thread.currentThread().getName() + "买包子");
                        try {
                            obj.wait(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.printf("%s正在吃包子", Thread.currentThread().getName());
                    }
                    System.out.println("吃完了");
                    System.out.println("==========");
                }
            }
        }.start();
        new Thread("顾客2：") {
            @Override
            public void run() {
                while (true) {
                    synchronized (obj) {
                        try {
                            obj.wait(1000); //限时等待，且不抱锁，释放cpu资源
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.printf("%s正在吃包子\n", Thread.currentThread().getName());
                    }
                    System.out.println("吃完了");
                    System.out.println("==========");
                }
            }
        }.start();
        //一个老板线程，用来唤醒
        new Thread("老板：") {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000); //sleep不放在同步中
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (obj) {
                        System.out.println("包子做好了");
                        //                        obj.notify(); //随机唤醒一个
                        obj.notifyAll(); //唤醒所有线程
                    }
                }
            }
        }.start();
    }
}
