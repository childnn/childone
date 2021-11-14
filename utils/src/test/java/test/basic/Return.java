package test.basic;

public class Return {

    public static void main(String[] args) {
        System.out.println(getMax(12, 13));
    }

    public static int getMax(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }
}
