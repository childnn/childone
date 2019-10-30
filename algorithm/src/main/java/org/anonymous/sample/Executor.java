package org.anonymous.sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池构造的过程
 * <p>
 * fixed线程池，搞了一堆固定数量的线程，配合了一个无界队列来处理你提交的任务，最多无论什么时候，只能有你指定数量的一些线程来处理任务。
 * 如果线程池里所有的线程都在繁忙的过程中，处理任务。
 * <p>
 * 此时的话呢，就只能再次提交任务的时候，把任务给压入无界队列中等待
 * <p>
 * 如果线程池里的某个线程挂掉了，此时他会自己启动一个新的线程加入到线程池里去
 * <p>
 * 线程池里的线程会一直存活在线程池里，等待处理新提交过来的任务，直到你关闭这个线程池
 * <p>
 * ThreadPoolExecutor才是真正代表的是线程池管理器，管理了一个线程池，内部持有一个线程池，相当于创建了一个线程池
 * <p>
 * java.util.concurrent.ThreadPoolExecutor.ThreadPoolExecutor(
 * int corePoolSize,
 * int maximumPoolSize,
 * long keepAliveTime,
 * TimeUnit unit,
 * BlockingQueue<Runnable> workQueue
 * )
 * <p>
 * corePoolSize：线程池里应该有多少个线程
 * <p>
 * maximumPoolSize：如果线程池里的线程不够用了，等待队列还塞满了，此时有可能根据不同的线程池的类型，可能会增加一些线程出来，但是最多把线程数量增加到maximumPoolSize指定的数量
 * <p>
 * keepAliveTime + TimeUnit：如果你的线程数量超出了corePoolSize的话，超出corePoolSize指定数量的线程，就会在空闲keepAliveTime毫秒之后，就会自动被释放掉
 * <p>
 * workQueue：你的线程池的等待队列是什么队列
 * <p>
 * threadFactory：在线程池里创建线程的时候，你可以自己指定一个线程工厂，按照自己的方式创建线程出来
 * <p>
 * RejectedExecutionHandler：如果线程池里的线程都在执行任务，然后等待队列满了，此时增加额外线程也达到了maximumPoolSize指定的数量了，这个时候实在无法承载更多的任务了，此时就会执行这个东西
 */
public class Executor {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        fixedThreadPool.submit(new LongTimeTask(1));
//        for (int i = 0; i < 10; i++) {
//            final int index = i;
//            fixedThreadPool.execute(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        System.out.println(index);
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }

    }
}
