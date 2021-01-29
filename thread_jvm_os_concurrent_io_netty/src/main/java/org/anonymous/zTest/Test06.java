package org.anonymous.zTest;

/**
 * 2019年2月19日20:23:26
 * 编写程序，在主线程中，循环输出“主线程执行”；在一条新线程中，循环输出“子线程执行”
 * 死循环，不要运行！
 */
public class Test06 {
    public static void main(String[] args) {
        final int i = 100; //默认 final，可以不写final 但是要知道
        new Thread("子线程") {
            @Override
            public void run() {
                while (i > 0) {
                    System.out.println(getName() + "正在执行");
                    //                    i--; //error
                }
            }
        }.start();
        Thread.currentThread().setName("主线程");
        while (i > 0) {
            System.out.println(Thread.currentThread().getName() + "正在执行");
        }
    }
}
