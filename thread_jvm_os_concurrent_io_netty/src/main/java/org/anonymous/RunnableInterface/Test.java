package org.anonymous.RunnableInterface;

/**
 * implements Runnable
 */
public class Test {
    public static void main(String[] args) {
        A aa = new A();
        Thread t = new Thread(aa);
        t.setName("子");
        t.start();

        System.out.println("当前执行：" + Thread.currentThread().getName()); //main
    }
}

class A implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("子线程");
        }
    }
}