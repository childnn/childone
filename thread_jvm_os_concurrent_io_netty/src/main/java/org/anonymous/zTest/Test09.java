package org.anonymous.zTest;

/**
 * 2019年2月19日20:36:22
 * 请按要求编写多线程应用程序，模拟多个人通过一个山洞：
 * 1.这个山洞每次只能通过一个人，每个人通过山洞的时间为5秒；
 * 2.随机生成10个人，同时准备过此山洞，并且定义一个变量用于记录通过隧道的人数。显示每次通过山洞人的姓名，和通过顺序
 */
public class Test09 {
    //    static int num = 10;
    public static void main(String[] args) {
        Person p = new Person();
        new Thread(p, "欧阳锋").start();
        new Thread(p, "张三丰").start();
        new Thread(p, "杨过").start();
        new Thread(p, "小龙女").start();
        new Thread(p, "吕洞宾").start();
        new Thread(p, "东方不败").start();
        new Thread(p, "狄仁杰").start();
        new Thread(p, "包拯").start();
        new Thread(p, "黄飞鸿").start();
        new Thread(p, "小明").start();
    }
}

//十个人十个线程，同一个任务
class Person implements Runnable {
    private int num = 1; //同一个任务对象，同一个计数器

    @Override
    public void run() {
        synchronized (this) { //同步锁
            try {
                System.out.println(Thread.currentThread().getName() + "正在通过山洞。序号：" + num++);
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}