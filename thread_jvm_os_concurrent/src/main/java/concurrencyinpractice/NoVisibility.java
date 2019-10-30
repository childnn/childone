package concurrencyinpractice;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/12 12:02
 * NoVisibility 可能会一直保持循环, 因为对于读线程来说, ready 值可能永远不可见.
 * 甚至更奇怪的现象是, NoVisibility 可能会打印 0, 因为早在对 number 赋值前, 主线程
 * 就已经写入 ready 并使之对读取线程可见, 这是一种 "重排序(reordering)" 现象.
 * 在单个线程中, 只要重排序不会对结果产生影响, 那么就不能保证其中的操作一定按照程序写定
 * 的顺序执行--即使重排序对于其他线程来说会产生明显的影响.
 * ---
 * 只有数据需要被跨线程共享，就进行 恰当的同步。
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    public static void main(String[] args) {
        new ReaderThread().start();
        // 主线程可能先写入 ready 值
        number = 42;
        ready = true;
    }

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

}
