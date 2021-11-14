package test.basic;

public class Sum {
    public static void main(String[] args) {
        System.out.println(getSum(2, 3));
    }

    public static int getSum(int a, int b) {
        int sum = a * b;
        return sum;
    }
}
