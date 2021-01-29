package org.anonymous.thread.pool;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author child
 * 2019/7/3 21:53
 * <p>
 * 线程相关:
 * @see java.lang.Thread
 * @see java.lang.Runnable
 * @see java.util.concurrent.Callable<V>
 * @see java.util.concurrent.Future<V>
 * @see java.util.concurrent.RunnableFuture<V> extends Runnable, Future<V> //同时实现 Runnable/Future 接口
 * @see java.util.concurrent.FutureTask<V> implements RunnableFuture<V> //主要方法 get(), 获取实现了 Callable 接口的子线程任务返回的结果
 * <p>
 * 线程池相关:
 * @see java.util.concurrent.Executor  // 线程池的顶层(函数式)接口 {@link Executor#execute(java.lang.Runnable)}, 该方法用来提交任务
 * @see java.util.concurrent.ExecutorService extends Executor // 扩展了 Executor 接口, 提供了异步执行和关闭线程池的方法
 * @see java.util.concurrent.Executors // 工具类: 创建不同类型的 线程池
 * <p>
 * 创建线程方式:
 * 1. 实现(implements)方式: {@link java.lang.Runnable} 接口
 * 2. 继承(extends)方式: {@link java.lang.Thread} 类
 * Thread 本质上就是实现类 Runable 接口的一个实现类, 代表一个线程的实例.
 * 无论是 继承/实现 方式, 都必须重写 run() 方法, 并通过 start() 方法开启新线程(开启线程的唯一方法)
 * 注: (关于 start 和 run 方法)
 * 1). run() 是 Runnable 接口的方法, -- Runnable 接口是一个 函数式接口
 * start() 是 Thread 类的方法
 * 2). run() 中的定义的代码就是新线程中的任务
 * start() 方法: 开启新线程(让此线程进入 runnable 状态, 并不是立即运行), 并在新线程中执行 run() 方法
 * 3). synchronized void start() 是同步方法, 其中调用了一个 本地方法 (private native void start0();)
 * (由 JVM 创建新线程)
 * 4). 当目标类已经 extends 了另一个类, 就无法再 extends Thread, 必须去实现 Runnable 接口
 * 所以, 由于 接口多实现的特性, 一般都会使用 implements Runnable 接口的方式
 * 另, 一个 Runnable 接口的实现类就相当于一个 任务(target), 当希望同一个任务被多个线程处理时,
 * 使用 implements Runnable 的方式
 * <p>
 * 另: (创建线程的方式三)
 * 3. {@link java.util.concurrent.Callable} 接口 (jdk 1.5+)
 * 类似 {@link java.lang.Runnable} 接口
 * {@code Callable} 接口的方法 {@link java.util.concurrent.Callable#call()}
 * {@code V call() throws Exception;} 有返回值, 可以抛出异常 // 可以看作是对 Runnable 接口的扩展
 * 注: 关于 异常: 由于 Runnable 接口的 run() 方法没有异常,
 * 所以, 实现 run() 方法后, 其中异常只能 try..catch (子类抛出的异常范围只能小于等于父类/接口)
 * 而 Callable 接口的的 call() 方法提供了 异常的抛出, 实现 call 方法后, 对异常的处理更为灵活.
 * <p>
 * 关于 {@link java.util.concurrent.Future} 接口的基本思想: 一个方法的执行过程可能非常耗时, 不能一直等着方法返回执行结果,
 * 因此, 可以在方法调用时返回结果: {@link java.util.concurrent.ExecutorService#submit(java.util.concurrent.Callable)}
 * submit() 方法 返回值就是 封装了 子线程任务执行所得结果对象(Future), 但是, 必须等待子线程实际执行完毕 才能通过
 * {@link Future#get()} 方法获取 需要的结果.
 * <p>
 * sleep 与 wait: {@link Object#wait()} {@link Thread#sleep(long)}
 * sleep: Thread 类的 静态方法, 不会释放对象锁
 * sleep 在给定参数的时间后, 自我唤醒
 * wait: Object 类的方法, 会释放对象锁, 针对此对象调用 {@link Object#notify()} 方法后本线程才进入 runnable 状态, 等待获取 CPU 资源.
 * wait 方法不给参数, 就是 无线等待, 必须等待 notify 唤醒
 * <p>
 * 线程池: (一般的有四种类型)
 * {@link java.util.concurrent.Executors} 提供了工厂方法创建不同类型的线程池
 * {@link Executors#newFixedThreadPool(int)}  // 创建 可重用的固定大小的线程池
 * {@link Executors#newSingleThreadExecutor()} // 创建 只有一个线程的线程池, 相当于调用 newFixedThreadPool(1);
 * {@link Executors#newCachedThreadPool()} // 一个根据需要创建新线程的线程池, 这些线程将会被缓存在线程池中
 * {@link Executors#newScheduledThreadPool(int)} // 创建一个具有指定线程数的线程池, 可以在给定延迟止后执行任务,
 * corePoolSize 指池中所保存的线程数, 即使线程是空闲的也被保存在线程池内.
 * {@link ScheduledExecutorService#schedule(java.lang.Runnable, long, java.util.concurrent.TimeUnit)}
 * {@link java.util.concurrent.Executors#newSingleThreadScheduledExecutor} 创建只有一个线程的线程池, 可以在指定延迟后执行线程任务
 * Java8
 * {@link java.util.concurrent.Executors#newWorkStealingPool} 创建持有足够的线程的线程池来支持给定的并行级别, 该方法还会使用多个队列来减少竞争.
 * 不指定参数则使用默认的 CPU 核数.
 * ---
 * @see java.util.concurrent.ExecutorService 代表尽快执行线程的线程池(只要线程池中有空闲线程, 就立即执行线程任务), 程序只要将一个 Runnable 对象或
 * Callable 对象(即线程任务)提交给该线程池, 该线程池就会尽快执行该任务.
 * @see java.util.concurrent.ExecutorService#submit(Runnable)
 * 返回值 Future 对象, 成功则为 f.get() 返回 null (Runnable 无返回值)
 * 可以调用 {@link java.util.concurrent.Future#isDone()} {@link java.util.concurrent.Future#isCancelled()} 获取 Runnable 对象执行状态.
 * @see java.util.concurrent.ExecutorService#submit(java.util.concurrent.Callable)
 * 返回 Callable 执行完毕的返回值
 * @see java.util.concurrent.ExecutorService#submit(Runnable, Object)
 * 返回指定的 result 值
 * @see java.util.concurrent.ScheduledExecutorService#schedule(Runnable, long, java.util.concurrent.TimeUnit)
 * 指定 delay 后执行
 * @see java.util.concurrent.ScheduledExecutorService#schedule(java.util.concurrent.Callable, long, java.util.concurrent.TimeUnit)
 * 指定 delay 后执行
 * @see java.util.concurrent.ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, java.util.concurrent.TimeUnit)
 * 定时重复执行(任务开始的间隔) the period between successive executions
 * @see java.util.concurrent.ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, java.util.concurrent.TimeUnit)
 * 定时重复执行(任务结束的间隔)the delay between the termination of one execution and the commencement of the next
 * 任意一次异常则终止, 否则只能显示取消/终止该任务.
 * ---
 * @see java.util.concurrent.ExecutorService#shutdown() 关闭序列, 不再接收新任务, 但已提交的任务会继续执行完毕.
 * @see java.util.concurrent.ExecutorService#shutdownNow() 立即关闭, 停止所有正在执行的活动任务,暂停处理正在等待的任务, 返回等待执行的任务列表
 * <p>
 * <p>
 * <p>
 * 线程生命周期:
 * 新建: new
 * 就绪: runnable
 * 运行: running
 * 阻塞: blocked
 * 死亡: dead
 * 注: blocked 状态不可能直接进入 running 状态, 任何线程要想进入 running 状态, runnable 是必经的状态
 * <p>
 * 乐观锁与悲观锁(java 与 数据库都有,类似)
 * 简单的理解:
 * 悲观锁 认为 数据经常会被改变, 当一个线程操作数据时, 当前数据就会被锁住, 不允许其他线程同时操作数据
 * 乐观锁 认为 数据不太经常被修改, 允许多个线程同时操作同一数据. 一般会使用 版本号(version) 来控制数据的修改,
 * 每次数据更新准备提交时, 会判断在当前线程修改数据期间, 版本号是否发生了变化(每一次成功提交的修改, 都伴随着版本号的更新)
 * 如果在此期间, 版本号前后一致, 则允许当前更新的提交; 否则, 就说明在此期间数据已经被修改过了, 不允许此次提交(执行回滚操作, 提示重新操作).
 * <p>
 * Synchronized 与 ReentrantLock (了解)
 * 1. Synchronized 隐式(自动) 获得/释放锁;  ReentrantLock 显式(手动) 获得/释放锁
 * {@link java.util.concurrent.locks.ReentrantLock#lock()/unlock()}
 * 2. Synchronized 是 JVM 级别的;  ReentrantLock 是 API 级别的
 * 3. Synchronized 是同步阻塞的, 使用 悲观并发策略; lock 是 同步非阻塞, 采用 乐观并发策略
 * 4. Synchronized 是 关键字, 使用内置语言实现;  ReentrantLock 是实现了 {@link java.util.concurrent.locks.Lock} 接口
 * 5. Synchronized 在发生异常时, 会自动释放线程占有的锁资源, 不会发生死锁现象
 * Lock 在发生异常时, 如果没有主动通过 unlock() 方法主动 释放锁, 很可能发生死锁现象,
 * 因此, 在使用 Lock 时, 必须使用 try..catch..finally 或 try..finally, 在 finally 中 主动调用 unlock 方法释放锁.
 * <p>
 * JUC 包: java.util.concurrent 并发相关的工具包
 * java.util.concurrent.atomic 包, 其中有各种实现 原子操作的类
 */
public class ACall implements Callable<String> {

    private static int i = 0, j = 0;

    public static void main(String[] args) {
        // 线程池: 固定大小, 可重用
        ExecutorService pool = Executors.newFixedThreadPool(10);
        //创建有返回值的 任务
        ArrayList<Future<String>> list = new ArrayList<>(); //用来存放 子线程的执行结果
        for (int i = 0; i < 10; i++) {
            //循环一次, 创建一个任务, 开启一个线程
            Callable<String> task = new ACall();
            //执行任务并获取 Future 对象: Future 对象就是封装了 call 方法执行结果(返回值)的对象
            Future<String> f = pool.submit(task);
            //把得到的 future 对象放入集合
            list.add(f);
        }
        //关闭线程池
        pool.shutdown();

        //获取所有并发任务的运行结果
        for (Future<String> f : list) {
            //从 Future 对象上获取任务的返回值, 并输出
            try {
                String result = f.get();
                System.err.println(Thread.currentThread().getName() + " result = " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " call " + (++j));
        return "hello " + (++i);
    }
}

class CallMe implements Callable<String> {

    public static void main(String[] args) {
        Callable<String> task = new CallMe();
        FutureTask<String> futureTask = new FutureTask<>(task); //参数可以是 Callable/也可以是 Runnable
        String result;
        try {
            //判断 子线程的任务是否执行完毕
            boolean done = futureTask.isDone();
            if (done) {
                //如果执行完毕: 在 子线程 执行之前获取结果
                // 这里如果不判断 直接获取结果, 子线程就会卡住,一直等待结果, 而实际上子线程还执行
                result = futureTask.get();
                System.out.println("result--1 = " + result);
            }
            //开启新线程, 执行任务
            new Thread(futureTask, "子线程").start();
            //在 子线程 执行之后获取结果
            result = futureTask.get();
            System.out.println(Thread.currentThread().getName() + " result--2 = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String call() {
        System.err.println(Thread.currentThread().getName() + " 我是新线程的任务...");
        return "我是返回的结果...";
    }
}

class AnExecutor implements Executor {

    public static void main(String[] args) {
        Executor executor = new AnExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " executor = " + executor);
            }
        });
    }

    @Override
    public void execute(Runnable r) {
        //开启新线程, 并执行任务 task
        new Thread(r).start();
    }
}