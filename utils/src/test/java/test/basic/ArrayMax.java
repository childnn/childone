package test.basic;

public class ArrayMax {

    public static void main(String[] args) {
        int[] arr = {1, 2, 5, 12, 15, 100, 6, 9, 13};
        int max;

        max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
//            System.out.println("max = " + max);
        }
        System.out.println("max = " + max);

        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        System.out.println("min = " + min);
    }

}
