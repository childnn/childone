package test.basic;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayTest2 {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6};

        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            if (i < arr.length - 1) {
                System.out.print(arr[i] + ", ");
            } else {
                System.out.println(arr[i] + "]");
            }
        }
        System.out.println("=====================");

        System.out.print("[");
        int i;
        for (i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ", ");
        }

        i = arr.length - 1;
        System.out.println(arr[i] + "]");

        System.out.println("===================");

        System.out.println(Arrays.toString(arr));
        System.out.println(Array.get(arr,2)); // 3

    }

}
