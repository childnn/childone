package org.anonymous.sample;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierUserCase {

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" 开会了，996工作要开始了！");
        });

        for (int i = 0; i < 5; i++) {
            new Thread(new Worker(barrier, "程序员" + i)).start();
        }
    }

}

class Worker implements Runnable {
    // 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)
    private CyclicBarrier barrier;
    private String name;

    public Worker(CyclicBarrier barrier, String name) {
        this.barrier = barrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " 准备好了...");
            // barrier的await方法，在所有参与者都已经在此 barrier 上调用 await 方法之前，将一直等待。
            barrier.await();
            System.out.println(name + " 走到这3！...");

        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
