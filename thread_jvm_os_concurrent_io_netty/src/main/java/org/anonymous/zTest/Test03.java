package org.anonymous.zTest;

/**
 * 2019年2月19日19:30:32
 * 请编写程序，分别打印主线程的名称和子线程的名称。
 * 要求使用两种方式实现：
 * 第一种方式：继承Thread类。
 * 第二种方法：实现Runnable接口。
 */
public class Test03 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        //Thread类的子类，匿名内部类
        new Thread("子线程一") {
            @Override
            public void run() {
                System.out.println(getName());
            }
        }.start();
        //Runnable 的子类，匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }, "子线程二").start();
    }
}
