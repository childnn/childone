package org.anonymous.sample;

public class LongTimeTask implements Runnable {
    private int num;

    public LongTimeTask(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "线程正在执行第" + num + "个任务");
            Thread.sleep(1000L);     //模拟耗时操作
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
