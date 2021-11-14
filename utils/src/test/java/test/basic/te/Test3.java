package test.basic.te;

/*
*第三题:
定义五个方法分别求数组的最大值 ,最小值, 总和, 平均值与奇数的个数,
在主方法中定义数组{23,55,32,18,67,75,97,12,78,89}, 调用每个方法完成测试

*
* */
public class Test3 {

    public static void main(String[] args) {
        int[] arr = {23,55,32,18,67,75,97,12,78,89};
        getMax(arr);
        getMin(arr);
        getSum(arr);
        getAvg(arr);
        getJiShu(arr);

    }

    public static void getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        System.out.println("最大值：" + max);
    }

    public static void getMin(int[] arr) {
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        System.out.println("最小值：" + min);
    }

    public static void getSum(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        System.out.println("总和：" + sum);
    }

    public static void getAvg(int[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        double avg =  sum / arr.length;
        System.out.println("均数：" + avg);
    }

    public static void getJiShu(int[] arr) {
        int n = 0;
        for (int i = 0; i < arr.length; i++) {
            if (1 == arr[i] % 2) {
                n++;
            }
        }
        System.out.println("奇数个数：" + n + "个");
    }

}
