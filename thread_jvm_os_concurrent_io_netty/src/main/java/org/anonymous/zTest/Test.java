package org.anonymous.zTest;

/**
 * 生产消费（线程通信）简化版
 */
public class Test {
    public static void main(String[] args) {
        final BaoZi bz = new BaoZi();
        new Thread("包子铺：") {
            @Override
            public void run() {
                while (true) {
                    synchronized (bz) {
                        if (bz.flag) { //如果有包子，等
                            try {
                                bz.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //如果没有包子，做包子，并改变包子的状态
                        if (0 == bz.i % 2) {
                            bz.pier = "猪皮";
                            bz.xian = "三鲜馅儿";
                        } else {
                            bz.pier = "蛇皮";
                            bz.xian = "猪肉大葱馅";
                        }
                        bz.i++;
                        System.out.printf("%s正在做第%d个%s%s包子\n", getName(), bz.i, bz.pier, bz.xian);
                        //做包子花费3秒
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(getName() + "包子做好了！");
                        bz.flag = true; //改变包子状态为true
                        //唤醒顾客
                        bz.notify();
                    }
                }
            }
        }.start();
        new Thread("吃货：") {
            @Override
            public void run() {
                while (true) {
                    synchronized (bz) {
                        if (bz.flag) { //如果有包子，直接吃，并改变包子状态
                            System.out.printf("%s正在吃第%d个%s%s的包子~\n", getName(), bz.i, bz.pier, bz.xian);
                            //吃包子花费5秒
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            bz.flag = false; //吃完改变包子状态
                            System.out.println(getName() + "吃完了！我还要！！！");
                            System.out.println("===================="); //每次吃完的分隔
                            bz.notify(); //唤醒包子铺做包子
                        }
                        //如果没有包子，等
                        System.out.println(getName() + "老板，我要买包子！");
                        try {
                            bz.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }
}

class BaoZi {
    String pier;
    String xian;
    boolean flag; //默认false
    int i; //计数包子个数,默认0
}