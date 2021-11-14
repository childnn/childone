package test.basic.te;

public class test004 {

    public static void main(String[] args) {
        long num = 2147483647 * 10;
        System.out.println(num); // -10,等号右边默认int，结果超出 int 范围，21亿
        long i = 2147483647L * 10;
        System.out.println(i);
        // 2 的 20 次方
        int sum = 1 << 20;
        System.out.println(sum);

    }

}
