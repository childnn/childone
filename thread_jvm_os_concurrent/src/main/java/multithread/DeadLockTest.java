package multithread;

import org.junit.jupiter.api.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/1 21:01
 * 死锁的四个条件:
 * 1. 互斥条件: 该资源任意一个时刻只由一个线程占用;
 * 2. 请求与保持条件: 一个进程因请求资源而阻塞时, 对已获得的资源保持不放;
 * 3. 不剥夺条件: 线程已获得的资源在未使用完之前不能被其他线程强行剥夺, 只有自己使用完毕后才释放资源;
 * 4. 循环等待条件: 若干进程之间形成一种头尾相接的循环等待资源关系.
 */
public class DeadLockTest {
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    @Test
    public void test() {
        // junit 测试无效.--死锁自动退出.
        main("o");
    }

    public static void main(String... args) {
        new Thread(() -> {
            synchronized (resource1) {
                Thread thread = Thread.currentThread();
                System.out.println(thread/*.getName()*/ + " gets resource1");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread + " is waiting for resource2");
                synchronized (resource2) {
                    System.out.println(thread + " gets resource2");
                }
            }

        }, "我是线程-1").start();

        new Thread(() -> {
            synchronized (resource2) {
                Thread thread = Thread.currentThread();
                System.out.println(thread/*.getName()*/ + " gets resource2");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(thread + " is waiting for resource1");
                synchronized (resource1) {
                    System.out.println(thread + " gets resource1");
                }
            }
        }, "我是线程-2").start();
    }
}
