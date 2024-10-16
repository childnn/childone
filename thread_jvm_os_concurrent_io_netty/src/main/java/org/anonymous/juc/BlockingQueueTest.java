package org.anonymous.juc;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @see java.util.concurrent.BlockingQueue
 * <p>
 * BlockingQueue的意思就是说，当一个线程调用这个TaskQueue的getTask()方法时，该方法内部可能会让线程变成等待状态，
 * 直到队列条件满足不为空，线程被唤醒后，getTask()方法才会返回。
 * @see java.util.concurrent.ArrayBlockingQueue
 * @since 2024/9/18
 */
public class BlockingQueueTest {
}
