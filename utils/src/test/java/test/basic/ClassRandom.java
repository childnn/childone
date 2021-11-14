package test.basic;

// 【1.导包】：import java.util.Random;
import java.util.Random;
public class ClassRandom {

    public static void main(String[] args) {
        // 【2.创建对象】
        Random r = new Random();
        int num = r.nextInt();
        System.out.println("对象的地址：" + r);
        System.out.println("随机数是：" + num); // 每次运行会有不同的【随机数】，且是【不同的对象】，指向地址不同
    }

}
