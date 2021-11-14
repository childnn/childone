package test.basic;

/*
* 第三题:
	1.定义一个包含五个元素的int类型的数组。数组元素自己给出
	2.将数组当中数据小于10的打印出来
        如果存在，则打印出来，具体的数据
        如果不存在，则打印出来，未找到合适的数据
*
* */
public class test3 {

    public static void main(String[] args) {
        int[] arr = {12, 10, 10, 20, 30};

//      如果存在比 10 小的数，则 if 会执行，
//      否则 if 不会执行
        boolean b = false; // 【重点】一定要理解这里定义的含义，注意数据类型的灵活应用
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 10) {
                System.out.println(arr[i]);
                b = true; // 【关键】
            }
        }

        if (!b) {
            System.out.println("未找到合适的数据");
        }

        System.out.println("=============");

//        先求最小值，在用最小值跟10比
        int min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        if (min >= 10) {
            System.out.println("未找到合适的数据");
        }
//        System.out.println(min);
    }

}
