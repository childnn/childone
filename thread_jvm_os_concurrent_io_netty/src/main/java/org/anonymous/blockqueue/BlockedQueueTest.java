package org.anonymous.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.util.concurrent.BlockingQueue 主要作用不是作为容器, 而是作为线程同步的工具.
 * BlockingQueue 具有一个特征: 当生产者线程试图向 BlockingQueue 中放入元素时, 如果该队列已满,
 * 则该线程被阻塞; 当消费者线程试图从 BlckingQueue 中取出元素时, 如果该队列已空, 则该线程被阻塞.
 * 交替向 BlockingQueue 中放入/取出元素, 可以很好的控制线程通信.
 * A {@code BlockingQueue} does not accept {@code null} elements.
 * 参见: BlockedQueue.html
 * --
 * @see java.util.concurrent.ArrayBlockingQueue 基于数组的 BQ
 * @see java.util.concurrent.LinkedBlockingQueue 基于链表的 BQ
 * @see java.util.concurrent.PriorityBlockingQueue 总是优先取出/删除最小元素: 大小按 Comparable/Comparator 比较逻辑
 * @see java.util.concurrent.SynchronousQueue 同步 BQ. 对该队列的存取必须交替进行
 * @see java.util.concurrent.DelayQueue 基于 PriorityBlockingQueue 实现. 不过, DelayQueue 要求集合元素都实现
 * {@link java.util.concurrent.Delayed} 接口, 后根据 {@link java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)}
 * 返回值进行排序.
 * @since 2020/6/3 11:06
 */
public class BlockedQueueTest<T> {

    final Lock lock = new ReentrantLock();
    // 条件变量：队列不满
    final Condition notFull = lock.newCondition();
    // 条件变量：队列不空
    final Condition notEmpty = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        // 长度为 2 阻塞队列
        final ArrayBlockingQueue<String> bq = new ArrayBlockingQueue<>(2);
        bq.put("Jack"); // add/offer 等价
        bq.put("Java"); // 加满队列

        // 对满队列操作:
        // bq.put("Java"); // 阻塞
        // bq.add("Java"); // java.lang.IllegalStateException: Queue full
        // bq.offer("Java"); // 直接返回 false
        bq.offer("Java", 5, TimeUnit.SECONDS); // 等待返回 false

        System.out.println("=====");

        // 空队列
        final ArrayBlockingQueue<Integer> eq = new ArrayBlockingQueue<>(2);

        // 对空队列操作:
        // eq.take(); // 空则阻塞
        // eq.remove(); // java.util.NoSuchElementException
        // eq.remove(1); // false
        // eq.poll(); // null
        eq.poll(1, TimeUnit.SECONDS); // TIMED_WAITING: null

    }

    // 入队
    //void enq(T x) {
    //    lock.lock();
    //    try {
    //        while (队列已满) {
    //            // 等待队列不满
    //            notFull.await();
    //        }
    //        // 省略入队操作...
    //        // 入队后, 通知可出队
    //        notEmpty.signal();
    //    } finally {
    //        lock.unlock();
    //    }
    //}
    //
    //// 出队
    //void deq() {
    //    lock.lock();
    //    try {
    //        while (队列已空) {
    //            // 等待队列不空
    //            notEmpty.await();
    //        }
    //        // 省略出队操作...
    //        // 出队后，通知可入队
    //        notFull.signal();
    //    } finally {
    //        lock.unlock();
    //    }
    //}
}
