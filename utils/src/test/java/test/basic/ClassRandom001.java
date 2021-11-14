package test.basic;

import java.util.Random;

public class ClassRandom001 {

    public static void main(String[] args) {
        Random r = new Random();
        int x = r.nextInt(5); // 表示输出 [0,5) 长度为 5 的5个随机数
        System.out.println(x);
        System.out.println("====================");

        //  要得到 [1,5] 闭区间的随机数，【在nextInt()方法后面 + 1 】就行。【注意】：此时将取不到 0 （当方法取得值为 0 时，+ 1 = 1）。
        // 如果写成【对象名.nextInt(n + 1);】这样表示将得到[0,n]闭区间的随机数
        for (int i = 0; i < 10; i++) {
            x = r.nextInt(5) + 1; //  如果把这一行注释掉，x 就变成了上面的 x 随机数 + 1（即上面的一旦确定，这里的 x 也会确定，十个一样的数）
            System.out.println(x);
        }
    }
}
