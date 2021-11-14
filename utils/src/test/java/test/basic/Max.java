package test.basic;

public class Max {
    public static void main(String[] args) {
        int a = 15;
        int b = 13;
        int c = 13;
        int m;

        if (a < b) {
            m = a;
            a = b;
            b = m;
        }

        if (a < c) {
            m = a;
            a = c;
            c = m;
        }

        if (b < c) {
            m = b;
            b = c;
            c = m;
        }
        System.out.println(a + "," + b + "," + c);

        int[] arr = arrMax(1, 2, 3);
    }

    public static int[] arrMax(int a, int b, int c) {
        int[] arr = {a, b, c};
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }

        return arr;

    }
}
