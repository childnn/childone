package thread;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
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

    }

}
