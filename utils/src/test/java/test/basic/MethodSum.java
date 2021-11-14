package test.basic;

public class MethodSum {

    public static void main(String[] args) {
        System.out.println("结果是：" + getSum());
    }

    public static int getSum() {
        int sum = 0;

        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
        return sum;
    }
}
