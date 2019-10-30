package org.anonymous.sample;

public class 二分查找BinarySearch {

    public static int banarySearch(int[] args, int key, int low, int high) {
        if (low > high || key < args[low] || key > args[high]) return -1;

        int mid = (low + high) / 2;

        if (args[mid] < key) {
            return banarySearch(args, key, mid + 1, high);
        } else if (args[mid] > key) {
            return banarySearch(args, key, low, mid - 1);
        } else {
            return mid;
        }

    }

    public static void main(String[] args) {

        int[] arr = {1, 3, 5, 7, 9, 11};
        int key = 4;

        System.out.println(banarySearch(arr, key, 0, 5));

        key = 5;
        System.out.println(banarySearch(arr, key, 0, 5));

        key = 11;
        System.out.println(banarySearch(arr, key, 0, 5));

    }

}


