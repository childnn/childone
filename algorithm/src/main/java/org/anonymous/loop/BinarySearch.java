package org.anonymous.loop;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/4 13:42
 */
public class BinarySearch {
    public static void main(String[] args) {
        BinarySearch bs = new BinarySearch();

        System.out.println(bs.binarySearch(new int[]{1, 2, 10, 15, 100}, 15));
        System.out.println(bs.binarySearch(new int[]{1, 2, 10, 15, 100}, 11));
        System.out.println(bs.binarySearch(new int[]{}, 15));
        System.out.println(bs.binarySearch(new int[]{15}, 15));
        System.out.println(bs.binarySearch(new int[]{15, 1}, 1));
    }

    /**
     * @param arr a sorted array
     * @param k   the key to find
     * @return index in arr where k is. -1 if not exists.
     */
    public int binarySearch(int[] arr, int k) {
        int a = 0;
        int b = arr.length;
        // loop invariant: [a, b) is a valid range. a <= b
        // k may only be within range [a, b).
        while (a < b) {
            /*if (a == b) {
                return -1;
            }*/
            int m = a + (b - a) / 2; // 防止 a + b 的溢出
            //int m = (a + b) / 2;
            if (k < arr[m]) { // k 在 m 左边
                b = m;
            } else if (k > arr[m]) { //
                a = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }

}
