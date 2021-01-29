package org.anonymous.zTest;

/**
 * 2019年2月19日20:32:39
 * 编写一个Java程序，要求在同一个类中除main线程外，再开启一个线程，2个线程都循环执行20次。
 */
public class Test08 {
    public static void main(String[] args) {
        new Thread("子线程：") {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    System.out.println(getName());
                }
            }
        }.start();

        for (int i = 0; i < 20; i++) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
