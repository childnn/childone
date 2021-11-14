package test.basic;

public class While1 {
    public static void main(String[] args) {
        int i = 0;
        int sum = 0;

        while (i < 101) {
            if (0 == i % 2) {
                sum += i;
            }
            i++;
        }

        System.out.println(sum);

        int j = 0;
        int sum1 = 0;

        do {
            sum1 += j;
            j++;
        }
        while (j < 101);

        System.out.println(sum1);
    }
}