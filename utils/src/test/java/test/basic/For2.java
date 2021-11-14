package test.basic;

public class For2 {
    public static void main(String[] args) {
        int sum1 = 0;
        int sum2 = 0;

        for (int i = 0; i < 101; i++) {
            if (1 == i % 2) {
                sum1 += i;
            }
        }

        System.out.println(sum1);

        for (int j = 0; j < 101; j++) {
            if (0 == j % 2) {
                sum2 += j;
            }
        }

        System.out.println(sum2);
    }
}