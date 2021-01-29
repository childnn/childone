package org.anonymous.thread.ThreadClass;

/**
 * 2019年2月18日17:05:34
 * 线程和卖票对象结合
 * 不同线程对象
 * 不同卖票对象
 * 看起来是不同的线程各自卖自己的票，但是 票 是static的，所以共用“一个票”
 */
public class Test01 {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("武汉");
        MyThread t2 = new MyThread("哈尔滨");
        t1.start();
        t2.start();
        /*for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }*/
    }
}

class MyThread extends Thread {
    private static int tickets = 100; //静态，所有对象共用,如果去掉，表示每个线程对象在卖各自的100张票

    public MyThread() {
    }

    public MyThread(String name) { //创建线程对象的同时给该线程命名
        super(name);
    }

    @Override
    public void run() {
        while (tickets > 1) {
            synchronized ("1") {
                System.out.printf("%s线程正在卖出第%d张票\n", getName(), tickets);
                tickets--;
            }
        }
    }
}