package org.anonymous.demo;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/29 19:38
 */
public class SortDemo {
    public static void main(String[] args) {
        int[] arr = {10, 13, 2, 9, 8, 6};
        select(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void bubble(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            boolean isSort = true;
            // 如果已经排序完成, 不必再进行外层循环了
            for (int j = 0; j < length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSort = false;
                }
            }
            if (isSort) {
                break;
            }
        }
    }

    private static void select(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            int min = i;
            for (int j = 0; j < length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }
}
