package org.anonymous.SynchronizedTest;

/**
 * 2019年2月20日10:18:59
 * 静态同步方法：
 * 锁对象是 实现类的字节码对象
 */
public class SynchronizedTest01 {
    public static void main(String[] args) {
        RunnableImpl run = new RunnableImpl();
        //        System.out.println(run);
        Thread t1 = new Thread(run);
        Thread t2 = new Thread(run);
        t1.start();
        t2.start();
    }
}

class RunnableImpl implements Runnable {
    private static int tickets = 100; //对于同一个任务类对象的不同线程，这里本来不用加 static 但是因为同步方法是static的，所以这里必须加static

    //静态同步方法的同步锁是 类的字节码对象：即 Runnable.class 文件对象
    private static synchronized void get() throws InterruptedException {
        if (tickets > 0) {
            Thread.sleep(1000); //throws 抛给调用者（有run来处理）
            System.out.println(Thread.currentThread().getName() + ":正在卖出第" + tickets + "张票");
            tickets--;
        }
    }

    //证明静态同步锁不是this 的方法二（很简单，this和static不能共存）
    private static void getter() throws InterruptedException {
        /*synchronized (this) { //error,编译报错

        }*/
        synchronized (RunnableImpl.class) { //实现类的字节码对象
            if (tickets > 0) {
                Thread.sleep(1000); //throws 抛给调用者（有run来处理）
                System.out.println(Thread.currentThread().getName() + ":正在卖出第" + tickets + "张票");
                tickets--;
            }
        }
    }

    @Override
    public void run() {
        System.out.println(RunnableImpl.class); //class SynchronizedTest.RunnableImpl
        while (tickets > 0) {
            try {
                //                get(); //静态同步方法
                getter(); //静态方法 + 同步代码块
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}