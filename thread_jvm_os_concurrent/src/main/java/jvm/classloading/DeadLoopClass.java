package jvm.classloading;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/7 9:48
 */
public class DeadLoopClass {
    static {
        // 如果注释掉 if 语句, 编译器提示
        // "initializer must be able to complete normally"
        if (true) {
            while (true) {

            }
        }
    }

    public static void main(String[] args) {
        Runnable script = () -> {
            System.out.println(Thread.currentThread() + " START");
            // 在初始化中进入死循环.
            new DeadLoopClass();
            System.out.println(Thread.currentThread() + " run over");
        };

        Thread t1 = new Thread(script);
        Thread t2 = new Thread(script);
        t1.start();
        t2.start();
    }
}
