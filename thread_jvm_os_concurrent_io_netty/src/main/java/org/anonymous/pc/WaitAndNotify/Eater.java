package org.anonymous.pc.WaitAndNotify;

/**
 * 2019年2月20日16:50:51
 * 吃货：
 * 判断包子状态：
 * true：有，开吃
 * false：无，等待
 */
public class Eater extends Thread {
    private final BaoZi bz;

    /*public Eater(String name) {
        setName(name);
    }*/
    public Eater(BaoZi bz, String name) {
        this.bz = bz;
        setName(name);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (bz) {
                if (bz.flag) { //如果有包子，吃包子
                    System.out.println(getName() + "正在吃" + bz.pier + bz.xian + "包子");
                    try {
                        Thread.sleep(1000); //吃1秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bz.flag = false; //吃完转变包子状态
                    System.out.println("吃完了");
                    bz.notify(); //吃完包子唤醒包子铺
                }
                //如果没有，唤醒包子铺，等
                try {
                    bz.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
