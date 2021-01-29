package org.anonymous.pc.WaitAndNotify;

/**
 * 线程类一：生产者
 * 包子铺
 * 对包子状态判断
 * true：有包子
 * 包子铺进入 wait(),
 * false:
 * 包子铺生产包子
 * 包子生产好了
 */
public class BaoZiPu extends Thread {
    private final BaoZi bz;
    //    public final Object obj = new Object(); //锁对象

    private int i = 0;

    /* public BaoZiPu(String name) {
         setName(name);
     }*/
    public BaoZiPu(BaoZi bz, String name) {
        super(name); //若使用父类构造方法，必须放在第一句代码
        //        setName(name); //可以直接通过父类 Thread 的有参构造设置名字，也可以使用setName方法
        this.bz = bz;
    }

    @Override
    public void run() {
        while (true) {
            //            int i = 0;
            synchronized (bz) {
                if (bz.flag) { //如果有包子，wait
                    try {
                        bz.wait(); //等待
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //如果没有包子，直接做包子
                if (0 == i % 2) {
                    bz.pier = "猪皮";
                    bz.xian = "豆沙馅";
                    System.out.printf("%s正在生产第%d个%s包子\n", getName(), ++i, bz.pier + bz.xian);
                } else {
                    bz.pier = "蛇皮";
                    bz.xian = "三鲜馅儿";
                    System.out.printf("%s正在卖第%d个%s包子\n", getName(), ++i, bz.pier + bz.xian);
                }
                //做一个包子3秒
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bz.flag = true;
                bz.notify(); //唤醒顾客
            }
        }
    }
}
