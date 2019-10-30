package multithread;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/28 14:40
 */
public class VolatileTest {
    public volatile int inc = 0;

    public static void main(String[] args) {
        final VolatileTest test = new VolatileTest();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++)
                    test.increase();
            }).start();
        }

        // 活动线程实际上不止主线程一个,
        // the current thread is willing to yield its current use of a processor
        while (Thread.activeCount() > 2) { // 保证前面的线程都执行完
            //System.out.println(Thread.activeCount());
            Thread.yield();
        }
        System.out.println(test.inc);
    }

    // volatile 对 ++ 操作并不会保证原子性
    public void increase() {
        inc++;
    }
}
