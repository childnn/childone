package org.anonymous.zTest;

/**
 * 2019年2月19日20:29:15
 * 编写程序，创建两个线程对象，一根线程循环输出“播放背景音乐”，
 * 另一根线程循环输出“显示画面”，要求线程实现Runnable接口，且使用匿名内部类实现
 */
public class Test07 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        }, "播放BGM").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        }, "显示画面").start();
    }
}
