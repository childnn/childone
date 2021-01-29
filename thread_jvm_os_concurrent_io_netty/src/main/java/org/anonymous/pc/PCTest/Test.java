package org.anonymous.pc.PCTest;

/**
 * 2019年2月18日22:49:10
 * 生产消费
 */
public class Test { //仓库
    private final int[] arr = new int[1000];
    private int cnt;

    public void push(int i) {
        //        if (i < arr.length) {
        ++cnt;
        arr[cnt - 1] = i;
        //        }
    }

    public int[] getArr() {
        return arr;
    }

    public int getCnt() {
        return cnt;
    }

    public int pop() {
        //        if (cnt > 0) {
        --cnt; //cnt = cnt - 1
        return arr[cnt];
        //        }
    }
}

class Producer implements Runnable {
    private final Test t;

    public Producer(Test t) {
        this.t = t;
    }

    @Override
    public void run() {
        while (t.getArr().length > t.getCnt()) { //如果仓库产品个数小于仓库容量
            try {
                if (t.getCnt() > 50) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                //                e.printStackTrace();
            }
            t.push(1); //把一个产品放入仓库
            System.out.printf("正在生产第%d个产品\n", t.getCnt());
        }
    }
}

class Consumer implements Runnable {
    private final Test t;

    public Consumer(Test t) {
        this.t = t;
    }

    @Override
    public void run() {
        while (t.getCnt() > 0) {
          /*  try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }*/
            int pop = t.pop();
            System.out.printf("消费第%d个产品:%d\n", t.getCnt(), pop);
        }
    }
}

class TestConsumer implements Runnable {
    private static Test t; //默认null

    public TestConsumer(Test t) {
        TestConsumer.t = t;
    }

    public static void main(String[] args) throws InterruptedException {
        Test t = new Test();
        Producer p = new Producer(t);
        TestConsumer c = new TestConsumer(t);
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);
        t1.start();
        t2.start();
        for (int i = 0; i < 1000; i++) {
            System.out.println(Thread.currentThread().getName() + i);
            Thread.sleep(1000);

        }
    }

    @Override
    public void run() {
        while (t.getCnt() > 1) {
            int pop = t.pop();
            System.out.printf("消费第%d个产品:%d\n", t.getCnt(), pop);
        }
    }
}

class MainTest {
    public static void main(String[] args) throws InterruptedException {
        Test t = new Test();
        Producer p = new Producer(t);
        Consumer c = new Consumer(t);
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);
        t1.start();
        t2.start();
        for (int i = 0; i < 1000; i++) {
            System.out.println(Thread.currentThread().getName() + i);
            Thread.sleep(1000);

        }


    }
}