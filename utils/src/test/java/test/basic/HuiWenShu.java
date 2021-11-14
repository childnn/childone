package test.basic;

public class HuiWenShu {

    public static void main(String[] args) {
        int m = 12345;
        int sum = 0;
        int n = m;
//        System.out.println(m);

        while (m > 0) {
            sum = sum * 10 + m % 10;  // 本程序的核心
            System.out.println(sum);
            m /= 10;
        }

        if (sum == n) {
            System.out.println("YES!");
        } else {
            System.out.println("NO!");
        }
    }
}
