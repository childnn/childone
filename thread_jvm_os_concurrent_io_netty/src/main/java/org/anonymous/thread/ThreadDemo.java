package org.anonymous.thread;

/**
 * @author child
 * 2019/7/11 10:56
 */
public class ThreadDemo implements Runnable {

    private static int i = 1;

    public static void main(String[] args) {

    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " 执行了..." + i++);
    }

}
