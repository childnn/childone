package test.basic;
/*
* 第二题:
	1.定义一个包含十个元素的int类型的数组。数组元素自己给出
	2.定义一个方法,求出该数组的平均数,并返回
	3.在main方法中将该数组中大于平均数的元素打印到控制台上(平均数用2中定义的方法得到)
* */
public class test2 {

    public static void main(String[] args) {
        int[] arr = {1, 2 ,3, 4, 5, 6, 7, 8, 9, 10};
        double avg = avgArray(arr);
        System.out.println(avg);
        System.out.println("======================");
        for (int i = 0; i < arr.length; i++) {
            if (avg < arr[i]) {
                System.out.println(arr[i]);
            }
        }
    }

    public static double avgArray(int[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
//        System.out.println(sum);
//        System.out.println(sum / arr.length);
        double avg = sum / arr.length;
//        System.out.println(avg[0]);

        return avg;
    }

}
