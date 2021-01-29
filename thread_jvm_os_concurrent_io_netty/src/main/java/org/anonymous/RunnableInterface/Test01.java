package org.anonymous.RunnableInterface;

/**
 * 线程和卖票对象分离
 * 同一卖票对象
 * 不同线程对象
 */
public class Test01 {
    public static void main(String[] args) {
        Tickets tickets1 = new Tickets(); //同一个对象，创建两个线程，卖的是同100张票
        Thread t1 = new Thread(tickets1);
        t1.setName("武汉");
        t1.start();
        Thread t2 = new Thread(tickets1);
        t2.setName("哈尔滨");
        t2.start();
    }
}

class Tickets implements Runnable {
    String str = "1";
    private int tickets = 1000;

    @Override
    public void run() {
        while (tickets > 1) {
            synchronized (str) {
                System.out.printf("%s线程正在卖出第%d张票\n", Thread.currentThread().getName(), tickets);
                tickets--;
            }
        }
    }
}