package test.basic;

public class ArrayParam {

    public static void main(String[] args) {
        int[] arr = {10, 20, 30};
        printArray(arr);
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}
