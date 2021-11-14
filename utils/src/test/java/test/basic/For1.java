package test.basic;

public class For1 {

    public static void main(String[] args) {
        int i = 1;
        int sum = 0;

        for (i = 1; i < 101; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}