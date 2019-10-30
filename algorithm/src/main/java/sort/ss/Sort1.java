package sort.ss;

import java.util.Arrays;

import static sort.ss.Sort.arr;
import static sort.ss.Sort.swap;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/7 17:09
 */
public class Sort1 {

    public static void main(String[] args) {
        //bubbleSort0(arr, arr.length);
        qSort(arr, 0, arr.length - 1);
        //insertSort(arr);
        //heapSort(arr);
        System.out.println(Arrays.toString(arr));
        shellSort(new int[]{}, 0);
        shellSort(new int[]{1}, 1);
    }

    /**
     * 基本思想: 先比较距离远的元素,而不是像简单交换排序算法那样先比较相邻的元素.
     * 这样可以快速减少大量的无序情况,从而减轻后续的工作.被比较的元素之间的距离逐步减少,直到减少为 1,
     * 这时排序变成了相邻元素的互换.
     *
     * @param arr
     * @param length
     */
    static void shellSort(int[] arr, int length) {
        int gap, i, j, temp;
        // 外层循环控制两个被比较元素之间的距离,从 n/2 开始,逐步进行对折,直到距离为 0.
        for (gap = length / 2; gap > 0; gap /= 2) {
            // 中间层循环用于在元素间移动位置.
            for (i = gap; i < length; i++) {
                // 最内层循环用于比较各对相距 gap 个位置的元素, 当这两个元素逆序时把它们互换过来.
                // 由于 gao 的值最终要递减为 1, 因此所有元素最终都会位于正确的排序位置上.
                for (j = i - gap; j >= 0 && arr[j] > arr[j + gap]; j -= gap) {
                    temp = arr[j];
                    arr[j] = arr[j + gap];
                    arr[j + gap] = temp;
                }
            }
        }
    }

    static void bubbleSort(int[] a, int n) {
        int i, j;
        for (i = n - 1; i > 0; i--) {
            // 将 a[0..i] 中最大的数据放在末尾
            // 第 n - i 次排序, 后 n - i 位完成升序
            for (j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                }
            }
        }
    }

    static void bubbleSort0(int[] a, int n) {
        int i, j;
        for (i = n - 1; i > 0; i--) {
            boolean isSorted = true; // 默认已经排序完成
            // 将 a[0..i] 中最大的数据放在末尾
            // 第 n - i 次排序, 后 n - i 位完成升序
            for (j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                    isSorted = false; // 发生交换后, 说明非有序
                }
            }

            if (isSorted) {
                break;
            }
        }
    }

    /**
     * 对于一个给定的数组,从中选择一个元素,以该元素为界将其余元素划分为两个子集,
     * 一个子集中的所有元素都小于该元素,另一个子集中的所有元素都大于或等于该元素.
     * 对这样两个子集递归执行这一过程,当某个子集中的元素数小于 2 时,这个子集就不需要再次排序,
     * 终止递归.
     *
     * @param arr
     * @param leftIndex  数组左边界
     * @param rightIndex 数组右边界
     */
    static void quickSort(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int i, j, x;
            i = leftIndex;
            j = rightIndex;
            x = arr[i];

            while (i < j) {
                while (i < j && arr[j] > x) {
                    j--; // 从右往左找第一个小于 x 的数
                }
                if (i < j) {
                    arr[i++] = arr[j];
                }
                while (i < j && arr[i] < x) {
                    i++; // 从左往右找第一个大于 x 的数
                }
                if (i < j) {
                    arr[j--] = arr[i];
                }
            }
            arr[i] = x;
            quickSort(arr, leftIndex, i - 1);
            quickSort(arr, i + 1, rightIndex);
        }
    }

    // sort arr[left] ... arr[right] into increasing order.
    static void qSort(int[] arr, int left, int right) {
        int i, last;
        // do nothing if array contains fewer than two elements.
        if (left >= right) {
            return;
        }
        // move partition element to arr[0];
        swap(arr, left, (left + right) / 2);
        last = left;
        // partition
        for (i = left + 1; i <= right; i++) {
            if (arr[i] < arr[left]) {
                swap(arr, ++last, i);
            }
        }
        // restore partition element
        swap(arr, left, last);
        qSort(arr, left, last - 1);
        qSort(arr, last + 1, right);
    }

    static void insertSort(int[] a) {
        int length = a.length; // 数组长度，将这个提取出来是为了提高速度。
        int insertNum; // 要插入的数
        for (int i = 1; i < length; i++) { // 插入的次数
            insertNum = a[i]; // 要插入的数
            int j = i - 1; // 已经排序好的序列元素个数
            while (j >= 0 && a[j] > insertNum) { // 序列从后到前循环，将大于insertNum的数向后移动一格
                a[j + 1] = a[j]; // 元素移动一格
                j--;
            }
            a[j + 1] = insertNum; // 将需要插入的数放在要插入的位置。
        }
    }

    public static void heapSort(int[] a) {
        System.out.println("开始排序");
        int arrayLength = a.length;
        //循环建堆
        for (int i = 0; i < arrayLength - 1; i++) {
            //建堆
            buildMaxHeap(a, arrayLength - 1 - i);
            //交换堆顶和最后一个元素
            swap(a, 0, arrayLength - 1 - i);
            System.out.println(Arrays.toString(a));
        }
    }

    //对data数组从0到lastIndex建大顶堆
    private static void buildMaxHeap(int[] data, int lastIndex) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            //k保存正在判断的节点
            int k = i;
            //如果当前k节点的子节点存在
            while (k * 2 + 1 <= lastIndex) {
                //k节点的左子节点的索引
                int biggerIndex = 2 * k + 1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if (biggerIndex < lastIndex) {
                    //若果右子节点的值较大
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                if (data[k] < data[biggerIndex]) {
                    //交换他们
                    swap(data, k, biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k = biggerIndex;
                } else {
                    break;
                }
            }
        }
    }

}
