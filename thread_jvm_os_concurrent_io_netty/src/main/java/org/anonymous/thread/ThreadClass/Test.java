package org.anonymous.thread.ThreadClass;

/**
 * extends Thread
 */
public class Test {
    public static void main(String[] args) {
        A aa = new A();
        aa.start(); //子线程进入准备状态，run() 随时加入线程，准备运行
        aa.setName("zi"); //给子线程命名
        while (true) {
            System.out.println("当前线程：" + Thread.currentThread().getName()); //获取主线程的名字
            System.out.println("主线程：哈哈哈哈哈哈哈哈哈");
            System.out.println(); //执行一次主线程换行
        }
    }
}

class A extends Thread {
    public void run() {
        while (true) {
            System.out.println("当前线程：" + getName()); //获取子线程的名字，继承了父类 Thread 的方法
            System.out.println("子线程：XXXXXXXXXXXXXXXX");
            System.out.println(); //执行一次子线程换行
        }
    }
}