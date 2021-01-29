package org.anonymous.thread.threadgroup;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.lang.ThreadGroup#uncaughtException(Thread, Throwable) 处理该线程组内任意线程抛出的未处理异常
 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
 * @see java.lang.Thread#setUncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)
 * @since 2021/1/25 17:38
 */
public class MyThread extends Thread {

    public MyThread(String name) {
        super(name);
    }

    public MyThread(ThreadGroup group, String name) {
        super(group, name);
    }

    public static void main(String[] args) {
        final ThreadGroup main = Thread.currentThread().getThreadGroup();
        System.out.println("Main-group: " + main.getName());
        System.out.println("主线程是否是后台线程组: " + main.isDaemon());

        new MyThread("主线程组的线程").start();

        ThreadGroup tg = new ThreadGroup("新线程组");
        tg.setDaemon(true);

        System.out.println("tg 线程组是否是后台线程组: " + tg.isDaemon());

        new MyThread(tg, "tg 组的线程甲").start();
        new MyThread(tg, "tg 组的线程乙").start();
    }

    @Override
    public void run() {
        final String name = getName();
        for (int i = 0; i < 20; i++) {
            System.out.println(name + " 线程的 i 变量: " + i);
        }
    }
}

class MyExHandler {

    public static void main(String[] args) {
        Thread.currentThread().setUncaughtExceptionHandler((t, e) -> System.out.println(t + " 线程出现了异常: " + e));
        int i = 5 / 0;
        System.out.println("结束");
    }

}