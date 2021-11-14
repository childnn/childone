package test.basic.te;

public class test003 {
    static int sum;

    public static int nSum(int n) {
        sum += n;
        sum = n == 0 ? sum : nSum(--n); // 这里只能用 --n， 而不能用 n--
        return sum;
    }

    public static void main(String[] args) {
        int n = 100;
        System.out.println(nSum(n));
    }

}
