package org.anonymous.RunnableInterface;

/**
 * 2019年2月19日14:10:27
 * 同步代码块
 * synchronized(锁对象) {}
 * 使用【一个】锁对象：（也叫同步锁，锁对象，对象监视器）
 * 任意引用数据类型，一般直接写 this 关键字，谁启用run(),this 就是谁
 * 同步技术原理：（只有一个锁对象）
 * 多个线程抢夺cpu的执行权，谁抢到了谁执行 run() 方法
 * t1线程抢到了CPU执行权，执行 run() 方法时遇到了 synchronized 代码块，这时t1会检查（判断） 同步代码块 是否有锁对象
 * 如果有，t1会获取锁对象，进入到同步代码块中，执行同步代码块中代码，【直到走出同步代码块，才会归还锁】
 * 若此时，t2线程抢到了cpu的执行权，执行 run() 方法，遇到了 synchronized 代码块，这时 t2 会检查同步代码块是否有锁对象
 * 发现没有，t2会进入【阻塞】状态，一直等待 拥有锁对象的 t1 归还锁对象（即 t1 执行完同步代码块中的代码，将锁对象归还给同步代码块）
 * 这时，t2才会进入【就绪】状态
 * 至此，一次“抢锁”完成，各个线程回到同一起跑线，开始竞争锁对象
 * 总结：同步中的线程，没有执行完毕不会释放锁；同步外的线程，没有锁进不去同步
 * 同步方法的锁对象是 this
 * 静态同步方法的锁对象是 方法锁属类的字节码对象 .class
 * <p>
 * 被同步后的代码执行效率变低了：多了加锁解锁的过程
 * 同步代码块的好处：
 * 防止执行相同任务的多个线程同时进入任务，导致任务冲突的问题，保证程序的安全
 */
public class Ticket00 implements Runnable {
    final private Object obj = new Object();
    private int tickets = 100;
    //    obj = new Object(); //error

    @Override
    public void run() {

        while (true) {
            synchronized (obj) { //在 tickets > 0 之前 “加锁”（该锁有且只有一个，被所有对象共享），一旦有一个线程抓住了“锁”
                //其他线程将无法访问“锁”中代码
                if (tickets > 0) {
                    /*try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    System.out.printf("%s正在卖出第%d张票\n", Thread.currentThread().getName(), tickets);
                    tickets--;
                }
            }
        }
    }
}

class TicketsTest {
    public static void main(String[] args) {
        Ticket00 ticket = new Ticket00();
        Thread t1 = new Thread(ticket, "线程一");
        Thread t2 = new Thread(ticket, "线程二");
        t1.start();
        t2.start();
        /**
         * 在局部位置的变量，如果不被final修饰，会随着main方法的加载（或者方法的调用）进栈
         * 随着main方法的执行完毕而随着main方法弹栈（此时这个 非final 的局部变量消失了）（多线程有多个栈，main线程有可能先弹栈），
         * 但是匿名内部类如果还要使用此变量，将无法获取（所以必须被 final修饰，变成常量，进入常量池，不会随着）
         */
        /*final */
        Object obj = new Object(); //默认final，jdk 1.8
        //        obj = new Object(); //error,匿名内部类中的同步锁的锁对象必须是 final 的（默认final）
        new Thread(new Runnable() {
            private final int tickets = 100;

            @Override
            public void run() {
                while (true) {
                    synchronized (obj) {
                        if (tickets > 0) {

                        }
                    }
                }
            }
        }, "线程三");

        for (int i = 0; i < 100; i++) {
            System.out.print("");
        }
    }
}