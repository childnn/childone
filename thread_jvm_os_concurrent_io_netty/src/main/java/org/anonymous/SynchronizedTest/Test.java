package org.anonymous.SynchronizedTest;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019年2月27日12:40:09
 */
public class Test {
    private final List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        /*final */
        Test t = new Test(); //同步中使用的对象 默认final
        //        t = new zTest(); // 本行解注， run() 中的 t 就会报错 （也证明 t 是默认 final ）
        //两个线程，使用同一个 zTest 对象 t,第二个线程执行时，会在第一个线程的内容上追加
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    t.add("A");
                    t.add("B");
                    t.add("C");
                    t.printAll();
                }
            }).start();
        }
    }

    private synchronized void add(String name) {
        list.add(name);
    }

    private synchronized void printAll() {
        for (String name : list) {
            System.out.print(name + " ");
        }
        System.out.println();
    }
}
