package org.anonymous.blockqueue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/25 17:13
 * 期望的结果: 由于队列长度为 1, 必须等待消费者消费完毕, 生产者才能继续执行
 * 改变生产者/消费者等待时间长短, 可能得到乱序的结果. 有可能与多核环境有关, 不确定.
 */
public class BlockingQueueTest2 {

    public static void main(String[] args) {
        // 容量为 1 的 BQ
        final ArrayBlockingQueue<String> bq = new ArrayBlockingQueue<>(1);

        // 启动 3 个生产者线程
        new Producer(bq).start();
        new Producer(bq).start();
        new Producer(bq).start();

        // 启动一个消费者
        new Consumer(bq).start();
    }

}

class Producer extends Thread {

    private static final List<String> list = Arrays.asList("Java", "Struts", "Spring");
    private final BlockingQueue<String> bq;

    Producer(BlockingQueue<String> bq) {
        this.bq = bq;
    }

    @Override
    public void run() {
        final String name = getName();

        for (int i = 0; i < 999999999; i++) {
            System.out.println(name + " Producer 准备生产集合元素!");
            try {
                Thread.sleep(200L);

                // 尝试放入元素, 如果队列已满, 则线程被阻塞
                bq.put(list.get(i % 3));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + " 生产完成: " + bq);
        }
    }
}

class Consumer extends Thread {
    private final BlockingQueue<String> bq;

    Consumer(BlockingQueue<String> bq) {
        this.bq = bq;
    }

    @Override
    public void run() {
        final String name = getName();

        while (true) {
            System.out.println(name + " Consumer 准备消费集合元素!");
            try {
                Thread.sleep(2000L);

                // 尝试取出元素, 如果队列为空, 则阻塞
                bq.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + " 消费完成: " + bq);
            System.out.println("=============================");
        }
    }
}