package test.basic;

public class ArrayReturn {

    public static void main(String[] args) {
        int[] arrB = arrayReturn(10, 20, 30);
        System.out.println(arrB[0]);
        System.out.println(arrB[1]);
        System.out.println(arrB + " B");
    }

    public static int[] arrayReturn(int a, int b, int c) {
        int sum = a + b + c;
        int avg = sum / 2;
        int[] arr = {sum, avg};
        System.out.println(arr + " A");

        return arr;
    }

}
