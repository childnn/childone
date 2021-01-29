package org.anonymous.thread;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.lang.Thread#MAX_PRIORITY
 * @see java.lang.Thread#MIN_PRIORITY
 * @see java.lang.Thread#NORM_PRIORITY
 * 优先级: 1-10.
 * @since 2021/1/25 15:49
 */
public class ThreadPriority extends Thread {

    public ThreadPriority(String name) {
        super(name);
    }

    public static void main(String[] args) {
        Thread.currentThread().setPriority(6);

        for (int i = 0; i < 30; i++) {
            if (10 == i) {
                final ThreadPriority low = new ThreadPriority("低级");
                low.start();
                System.out.println("low 创建之初的优先级: " + low.getPriority());

                // 设置最低优先级
                low.setPriority(Thread.MIN_PRIORITY);
            }

            if (20 == i) {
                final ThreadPriority high = new ThreadPriority("高级");
                high.start();
                System.out.println("high 创建之初的优先级: " + high.getPriority());

                high.setPriority(Thread.MAX_PRIORITY);
            }
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(getName() + ", 优先级是: " + getPriority() + ", 循环变量的值为: " + i);
        }
    }

}
