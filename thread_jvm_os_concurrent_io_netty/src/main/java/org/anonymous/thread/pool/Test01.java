package org.anonymous.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2019年2月20日19:50:36
 * java.util.concurrent
 * Class Executors:线程池的工厂类 -- 用来生产接口的实现类对象
 * static ExecutorService newFixedThreadPool​(int nThreads) 创建一个可重用固定线程数的线程池，以共享的无界队列方式来运行这些线程。
 * 参数：创建线程池的数量
 * 返回值：ExecutorService接口，返回的是其实现类对象（可以用接口接收返回值，多态）
 * java,util.concurrent.ExecutorService:线程池接口
 * 用来从线程池中获取线程，调用start()方法，执行线程任务
 * submit(Runnable target) 提交一个 Runnable 任务并自动调用start()执行任务
 * 关闭/销毁线程池的方法
 * void shutdown()
 * 线程池使用步骤：
 * 1.使用线程池工厂类 Executors 的静态方法获取一个指定数量的线程池 ExecutorService 的实现类对象
 * 2.创建一个类，实现 Runnable 接口，重写 run() 设置任务
 * 3.调用 ExecutorService 中的方法 submit，传递线程任务，开启线程，执行run()
 * 4.调用 ExecutorService 中的方法 shutdown 销毁线程池（不建议使用）
 * 线程池中每一个线程执行完毕会自动返回线程池，可以被再次调用
 */
public class Test01 {
    public static void main(String[] args) {
        //创建线程池对象
        ExecutorService es = Executors.newFixedThreadPool(2);
        //
        for (int i = 0; i < 10; i++) {
            es.submit(new Runnable() { //每一个线程使用完毕之后会返回线程池，可以继续被调用，线程池会一直开启
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "正在运行");
                }
            });
            //            es.submit(() -> System.out.println(Thread.currentThread().getName() + "lambda"));
        }
        //任务调用完毕，销毁异常
        es.shutdown();
        //        es.submit(() -> System.out.println(1)); //Exception，线程池已被销毁，不可再使用
    }
}
