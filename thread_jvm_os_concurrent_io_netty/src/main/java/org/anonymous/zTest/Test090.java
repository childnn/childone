package org.anonymous.zTest;

//2019年2月19日21:29:25
//无法实现 Test09
public class Test090 {
    private static int i = 10;
    private static int j = 1;

    public static void main(String[] args) {
        Object obj = new Object();
        //人，睡，等待唤醒
        new Thread(() -> {
            while (true) {
                if (j == 10) {
                    break;
                }
                synchronized (obj) {
                    try {
                        System.out.printf("第%d个人正在通过山洞\n", j);
                        Thread.sleep(5000);
                        //                        obj.wait();
                        j++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }).start();

        //山洞，用来唤醒人
        new Thread(() -> {
            while (true) {
                if (i == 0) {
                    break;
                }
                synchronized (obj) {
                    System.out.println("山洞没人");
                    i--;
                        /*try {
//                            Thread.sleep(5000);
//                            obj.notify();
                            i--;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                }
            }
        }).start();
    }
}
