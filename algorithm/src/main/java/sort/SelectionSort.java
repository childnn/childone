package sort;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/5 17:07
 */
public class SelectionSort {

    public static void main(String[] args) {
        selectionSort(new int[]{}, 0);
        selectionSort(new int[]{1}, 1);
        selectionSort(new int[]{10, 3, 7, -1, 4, 9}, 6);
    }

    //static int[] selectionSort(int[] unSortedArr) {
    //    return
    //}

    // 选择排序: 选择最小/大 元素的索引
    // 选择未排序的列表中最小的元素，放在排序列表末尾
    static void selectionSort(int[] arr, int length) {
        // 最后一个未排序的元素就不用排序了```````````
        for (int i = 0; i < length - 1; i++) {
            int smallIndex = i;
            // 找到未排序列表中最小数所在的索引
            for (int j = i + 1; j < length; j++) {
                if (arr[smallIndex] > arr[j]) {
                    smallIndex = j;
                }
            }
            // 将最小值放在已排序末尾
            if (smallIndex != i) {
                int temp = arr[smallIndex];
                arr[smallIndex] = arr[i];
                arr[i] = temp;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

}
