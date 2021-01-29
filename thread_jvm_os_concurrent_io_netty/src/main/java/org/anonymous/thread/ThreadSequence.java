package org.anonymous.thread;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see Thread.State#NEW new Thread(); 新建/初始化, 分配内存.
 * @see Thread.State#RUNNABLE t.start(); 就绪/等待执行, 创建方法调用栈, 程序计数器. 等待 JVM 线程调度器的调度.
 * @see Thread.State#BLOCKED 阻塞, 等待同步锁
 * 阻塞的情形:
 * 1. 线程调用 {@link Thread#sleep} 方法(等待但不释放锁)
 * 2. 线程调用阻塞IO 方法, 在该方法返回之前, 该线程被阻塞
 * 3. 线程视图获得一个同步监视器(monitor), 但该同步监视器(monitor)正在被其他线程所持有.
 * 4. 线程等待 {@link Object#notify()}/{@link Object#notifyAll()}
 * 5. 程序调用线程的 {@link Thread#suspend()} (该方法存在死锁倾向, 已经废弃)
 * 阻塞解除后, 重新进入就绪状态, 等待线程调度器调度.
 * 1. 调用 {@link java.lang.Thread#sleep} 方法的线程结果指定时间;
 * 2. 线程调用的阻塞时 IO 方法已经返回;
 * 3. 线程成功获得视图取得的同步监视器;
 * 4. 线程正在等待某个 notify 时, 其他线程发出了一个 notify;
 * 5. 处于 suspend 的线程被调用 {@link Thread#resume()} 方法恢复.
 * 注: 调用 {@link Thread#yield()} 方法, 线程转入就绪状态, 而非阻塞状态.
 * @see java.lang.Thread.State#WAITING
 * @see java.lang.Thread.State#TIMED_WAITING
 * @see Thread.State#TERMINATED
 * 1. {@link Runnable#run()} {@link java.util.concurrent.Callable#call()} 执行完毕, 线程正常结束
 * 2. 线程抛出一个未捕获的 异常
 * 3. 直接调用该线程的 {@link Thread#stop}, 该方法存在死锁倾向, 不推荐使用.
 * 当主线程结束时, 其他线程不受任何影响, 并不会随之结束. 一旦子线程启动起来后, 它就拥有和主线程相同的地位, 不受主线程影响.
 * @see Thread#isAlive() 当线程处于 RUNNABLE/BLOCKED/running 状态时, 返回 true; 处于 NEW/TERMINATED 状态时, 返回 false.
 * 不要试图对一个已经死亡的线程调用 {@link Thread#start()} 方法使它重启, 死亡的线程不可再次作为线程执行.
 * 也即, 不要对同一个线程的 start 方法调用超过一次.
 * --------
 * @see Thread#join 阻塞调用线程, 直到被 join 方法加入的 join 线程执行完毕为止.
 * joint 方法通常由使用线程的程序调用, 以将大问题划分为许多小问题, 每个小问题分配一个线程. 当所有的小问题都得到处理后, 再调用主线程来进一步处理.
 * ----
 * 守护线程/精灵线程: 在后台运行, 为其他线程提供服务. 如 JVM 的垃圾回收线程就是典型的后台线程.
 * 如果所有的前台线程都是死亡, 则后台线程会自动死亡.
 * @see Thread#setDaemon(boolean) true 将指定线程设置为后台线程.
 * @since 2020/3/13 10:51
 */
public class ThreadSequence {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> System.out.println(Thread.currentThread().getName() + " 正在运行"), "线程一");
        t1.start();
        Thread t2 = new Thread(() -> System.out.println(Thread.currentThread().getName() + " 正在运行"), "线程二");
        t2.start();
        Thread t3 = new Thread(() -> System.out.println(Thread.currentThread().getName() + " 正在运行"), "线程三");
        t3.start();
        Thread t4 = new Thread(() -> System.out.println(Thread.currentThread().getName() + " 正在运行"), "线程四");
        t4.start();
        Thread t5 = new Thread(() -> System.out.println(Thread.currentThread().getName() + " 正在运行"), "线程五");
        t5.start();
        Thread t6 = new Thread(() -> System.out.println(Thread.currentThread().getName() + " 正在运行"), "线程六");
        t6.start();
        // t6.start(); // java.lang.IllegalThreadStateException
    }

}
