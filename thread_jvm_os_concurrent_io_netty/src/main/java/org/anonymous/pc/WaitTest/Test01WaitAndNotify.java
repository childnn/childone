package org.anonymous.pc.WaitTest;

/**
 * 2019年2月19日16:15:48
 * Object 类的方法：
 * void notify​() 唤醒正在等待对象监视器的单个线程。(若有多个线程在 WAITING 无线等待，则【随机】唤醒一个线程)
 * void notifyAll​() 唤醒正在等待对象监视器的所有线程。
 * void wait​() 导致当前线程等待，直到另一个线程调用该对象的 notify()方法或 notifyAll()方法。
 * void wait​(long timeout) 导致当前线程等待，直到另一个线程调用该对象的 notify()方法或 notifyAll()方法，或指定的时间已过。
 * <p>
 * 等待唤醒案例：线程之间的通信
 * 创建一个顾客线程（消费者）：wait()方法，放弃cpu执行权，进入 WAITING （无限等待）
 * 创建一个老板线程（生产者）：notify()方法，唤醒 WAITING 状态的线程，继续执行 wait() 之后的代码
 */
public class Test01WaitAndNotify {
    public static void main(String[] args) {
        //创建锁对象，保证唯一
        /*final */
        Object obj = new Object();
        //        obj = new Object(); //这一行加上，下面的 obj 全报错，证明上面的对象默认 final 不可变
        //创建消费者
        new Thread(() -> {
            while (true) {
                //保证等待和唤醒的线程只能有一个执行，需要使用同步技术
                synchronized (obj) {
                    System.out.println("买包子");
                    try {
                        obj.wait(); //调用wait()，【释放 锁，注意与 Thread.sleep(Long ms)不释放锁的区别】，进入无限等待，直到另一个线程 notify 唤醒
                        // 父类Thread 的 run() 方法没有抛异常，子类不能抛.只能 try..catch
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //唤醒之后执行的代码
                    System.out.println("吃包子");
                }
            }
        }).start();

        //创建生产者
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    //等待2秒
                    try {
                        Thread.sleep(2000); //没有在锁中，如果放在 锁中，会导致 notify()执行后，本线程仍然拥有 锁，其他线程的同步代码无法执行
                    } catch (InterruptedException e) { //但是没有看出区别
                        e.printStackTrace();
                    }
                    synchronized (obj) { //因为 wait() 在无限等待时，会释放锁对象，所以这里可以掌握锁的控制权
                       /* try {
                            Thread.sleep(5000); //没有在锁中，如果放在 锁中，会导致 notify()执行后，本线程仍然拥有 锁，其他线程的同步代码无法执行
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                        System.out.println("包子做好了");
                        //包子做好了，调用 notify() 唤醒 消费者线程
                        obj.notify();
                        System.out.println("吃包子吧");
                    }
                    System.out.println("老板");
                }
            }
        }.start();
    }
}
