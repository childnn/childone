package test.basic;

/*
* 第五题:
	1.现存在如下两个数组:
		int[] arr1 = {1,2,3};
		int[] arr2 = {4,5,6};
	2.要求定义一个方法,将上面两个数组传入,在方法中将两个数组合并,形成一个新的数组,并返回.
		新的数组: arr3 = {1,2,3,4,5,6}
	3.在main方法中调用该方法,将返回的数组遍历打印
* */

public class test5 {

    public static void main(String[] args) {
//        ArrayParam arr = new ArrayParam();
        int[] arr4 = new int[3];

        ArrayParam.printArray(arr4);
//        arr.printArray(arr4);
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {4, 5, 6};
        int[] arrP = arrPlus(arr1, arr2);

        for (int i : arrP) {
            System.out.println(i);
        }
        //上下等价
        /*for (int i = 0; i < arrP.length; i++) {
            System.out.println(arrP[i]);
        }*/

    }

    public static int[] arrPlus(int[] arr1, int[] arr2) {
        int[] arr3 = new int[arr1.length + arr2.length];

        for (int i = 0; i < arr1.length; i++) {
            arr3[i] = arr1[i];
        }

        for (int i = 0; i < arr2.length; i++) {
            arr3[arr1.length + i] = arr2[i];
        }

        return arr3;
    }

}
