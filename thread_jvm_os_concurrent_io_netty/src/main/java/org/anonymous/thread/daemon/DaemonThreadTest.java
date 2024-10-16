package org.anonymous.thread.daemon;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see Thread#isDaemon()
 * 主线程死亡, JVM 会通知后台线程死亡, 但从它接收到指令到做出响应, 需要一定的时间. 而且要将某个线程设置为后台线程,
 * 必须在该线程启动之前设置, 即 setDaemon 必须在 start 之前调用.
 * @since 2021/1/25 15:11
 */
public class DaemonThreadTest {

    public static void main(String[] args) {
        final Thread daemon = new Thread(() -> task(10000), "守护线程");
        daemon.setDaemon(true);
        daemon.start();

        task(10); // 主线程执行完毕就退出, 守护线程无法执行完实际设置的 10000.
    }

    private static void task(int x) {
        for (int i = 0; i < x; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }

}
