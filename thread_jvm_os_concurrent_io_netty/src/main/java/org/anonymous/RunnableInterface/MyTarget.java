package org.anonymous.RunnableInterface;

/**
 * 2019年2月19日11:16:19
 * 线程开启方式：实现
 * 优点：
 * 1.避免了单继承的局限性
 * 实现接口的同时还可以继承其他类，实现其他接口
 * 2.增强了程序的扩展性，降低了程序的耦合性（解耦）（高内聚，低耦合）
 * 实现接口，把设置线程任务和开启线程分离（run() 和 start() 分离）
 * 3.增加代码健壮性
 * 4. 线程池只能放入实现Runnable或callable类线程，不能直接放入继承Thread的类。
 */
public class MyTarget implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            System.out.println(Thread.currentThread().getName() + "Java:" + i);
        }
    }
}

class Testt {
    public static void main(String[] args) {
        //方法一：通过任务实现类创建对象
        MyTarget mt = new MyTarget();
        Thread t = new Thread(mt, "线程一:");
        t.start();
        //方法二：匿名内部类（Runnable接口的匿名实现类对象）
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    System.out.println(Thread.currentThread().getName() + "C#:" + i);
                }
            }
        }, "线程二:");
        t.start();
        //方法三：双匿名内部类，匿名线程对象+匿名任务对象，直接调用线程对象的start()方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    System.out.println(Thread.currentThread().getName() + "Python:" + i);
                }
            }
        }, "线程三:").start();
        //方法四：lambda表达式
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                System.out.println(Thread.currentThread().getName() + "WEB:" + i);
            }
        }, "线程四:").start();

    }
}