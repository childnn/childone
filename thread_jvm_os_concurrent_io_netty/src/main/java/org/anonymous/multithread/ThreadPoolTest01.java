package org.anonymous.multithread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/9/24 20:56
 */
public class ThreadPoolTest01 {
    /*
    线程池的核心线程数（corePoolSize）、最大线程数（maximumPoolSize）、线程的存活时间（keepAliveTime）共同管理的线程的创建与销毁。
        当前线程数 < 核心线程数：来一个任务创建一个线程
        当前线程数 = 核心线程数：来一个任务就会将其加入到队列中
        当前线程数 > 核心线程数：此时有一个前提条件就是队列已满，才会新建线程，此时就会开启线程的活性检查，
                             对于设置为 keepAliveTime 时间没有活动的线程将会被回收
     */

    public static void main(String[] args) {
        // corePoolSize 设置为 0: 线程就会动态的进行创建了，闲的时候没有线程，忙的时候再在线程池中创建线程。
        // 只有在队列满的情况下才会新建线程, 使用 LinkedBlockingQueue 无界队列, 实际上相当于单线程.
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                0, Integer.MAX_VALUE, 1,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1");
            });
        }
    }


    @Test
    public void test() {
        // 无法执行多线程任务。。
    }
}

class ThreadPoolTest02 {
    public static void main(String[] args) {
        // 使用 SynchronousQueue
        // SynchronousQueue并不是一个真正的队列，而是一种管理直接在线程间移交信息的机制，
        // 这里可以简单将其想象成一个生产者生产消息交给SynchronousQueue，而消费者这边如果有线程来接收，那么此消息就会直接交给消费者，反之会阻塞。
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                0, Integer.MAX_VALUE, 1,
                TimeUnit.SECONDS, new SynchronousQueue<>(), new CustomerThreadFactory("customerThread"));
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": 1");
                //System.out.println("1");
            });
        }
    }
}


class CustomerThreadFactory implements ThreadFactory {

    private final String name;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    CustomerThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, name + threadNumber.getAndIncrement());
        return thread;
    }
}