package org.anonymous.pc.WaitTest;

/**
 * 2019年2月19日17:42:10
 * 等待唤醒案例：线程之间的通信(不同的线程对象，不同的任务)
 * wait() 和 notify()：锁对象是连接这两个方法的纽带，是线程之间通信的桥梁
 * 1.这两个方法必须要在对应线程的 synchronized 同步代码块中（保证同一个时刻内，等待和唤醒只能有一个在执行）：否则会运行报错
 * 2.这两个方法必须要通过同一锁对象（的引用）调用，才会产生关联（通信）
 * 3.在线程通信中，不可以用 this 关键字作为锁对象，因为 this 表示调用该run方法的对象，在本例中即每个线程自己（线程和任务合并）
 * 问题：可以把 synchronized 同步代码块换成 Lock 锁吗？(答案是不可以！！！)
 * IllegalMonitorStateException ： if the current thread is not the owner of the object's monitor.
 */
public class Test03WaitAndNotify {
    public static void main(String[] args) {
        final Object obj = new Object();
        //Thread 的匿名子类对象
        new Thread("顾客") { //Thread 类中的 run() 没有抛出异常，其子类重写 run() 后不能抛出异常，必须子类自己 try..catch
            @Override
            public void run() {
                while (true) {
                    synchronized (obj) {
                        try { //必须try ..catch
                            System.out.println("顾客：买包子");
                            /*obj.*/
                            wait(); //无限等待，等待被唤醒
                            //                    Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "-->吃包子");
                        System.out.println("===============");
                    }
                }
            }
        }.start();
        new Thread("老板") {
            @Override
            public void run() {
                while (true) {
                    System.out.println("等着做包子");
                    try {
                        Thread.sleep(1000); //做包子
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (obj) {
                        System.out.println(Thread.currentThread().getName() + "：包子做好了");
                        /*obj.*/
                        notify(); //唤醒 正在 wait() 的线程
                    }
                }
            }
        }.start();
    }
}
