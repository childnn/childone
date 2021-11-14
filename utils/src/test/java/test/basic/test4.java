package test.basic;

/*
* 第四题:
	1.定义一个包含十个元素的int类型的数组。数组元素自己给出
	2.统计该数组中有多少个大于平均值的元素,在控制台上打印出来.并求和.
* */
public class test4 {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
           sum += arr[i];
        }

        double avg = sum * 1.0 / arr.length; // 【类型转换】
//        System.out.println(avg);

        int sum2 = 0;
        int a = 0;

        System.out.print("数组中大于均数的是：");
        for (int i = 0; i < arr.length; i++) {
            if (avg < arr[i]) {
                System.out.print(arr[i] + " ");
                sum2 += arr[i];
                a++;
            }
        }
        System.out.println();
        System.out.println("数组中大于均数的个数为：" + a);
        System.out.println("数组中大于均数的元素之和为：" + sum2);
    }

}
