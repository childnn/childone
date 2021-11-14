package test.basic;

/*
第一题:
	1.定义一个包含十个元素的int类型的数组。数组元素自己给出
	2.遍历打印出数组元素
	3.求出数组当中的最小值打印出来
	4.求出数组当中的最大值打印出来
*/
public class test1 {

    public static void main(String[] args) {
        int[] arr = {1, 3, 12, 21, 33, 23, -20, 7, 9, 20};

        int min = arr[0];
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
//            System.out.println("==========================");

            if (min > arr[i]) {
                min = arr[i];
            }

            if (max < arr[i]) {
                max = arr[i];
            }
        }
        System.out.println();
        System.out.println(min);
        System.out.println(max);


       /* int min = arr[0];
        for (int i = arr.length - 1; i >= 0; i--) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        System.out.println(min);
        System.out.println("============");

        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        System.out.println(max);*/
    }

}
