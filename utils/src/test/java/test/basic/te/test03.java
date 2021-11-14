package test.basic.te;

/**
 * 数组名的含义：数组第一个元素（0号索引）的地址值
 */
public class test03 {

    public static void main(String[] args) {

        int[] arr = new int[5];
        System.out.println(arr); // 地址
        System.out.println(arr[0]);
        int ar[] = {1, 2, 3};
        System.out.println(ar);
        System.out.println(ar[0]);
        System.out.println('\0' + "1");
        System.out.println('\u0000' + "1");
        System.out.println(' ' + 0); //32, 空格字符的ASCII值
//        int i = arr - ar; // error
        System.out.println(Integer.MAX_VALUE); // 2^31－1, int 类型最大值
        System.out.println(Integer.MIN_VALUE); // -2^31, int 类型最小值
        System.out.println(Double.MAX_VALUE); // 1.7976931348623157E308
        System.out.println(Double.MIN_VALUE); // 4.9E-324

    }

}
