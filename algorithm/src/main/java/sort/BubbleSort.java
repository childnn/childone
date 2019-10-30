package sort;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/5 8:50
 */
public class BubbleSort {
    public static void bubbleSort(int[] arr, int n) {
//        boolean change = true;
        for (int i = 1; i < n /*&& change*/; i++) {
//            change = false;
            for (int j = 0; j < n - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
//                    change = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {48, 62, 35, 77, 55, 14, 35, 98, 22, 40};
        bubbleSort(arr, arr.length);
        String s = Arrays.toString(arr);
        System.out.println("s = " + s);
    }
}
