package test.basic;

public class Array1 {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3}; //数组长度为三，数组内容编号为“ 0，1，2 ”

        System.out.println(arr); // [I@75412c2f : 数组的内存地址，十六进制，哈希值
        // 直接打印数组内容
        System.out.println(arr[0]); // 1
        System.out.println(arr[1]); // 2
        System.out.println(arr[2]); // 3

//        System.out.println(arr[3]); // error，超过数组编号，“ 数组索引越界异常，ArrayIndexOutOfBoundsException ”，数组从零 “0” 开始编号到 “ 数组长度-1 ”

        System.out.println("===============B=================");

        int[] arrI;
        arrI = new int[]{};
//        System.out.println(arrI[0]); //  ArrayIndexOutOfBoundsException

        // 所有【引用类型】都可以赋值 null，表示里面什么都没有
//        数组必须进行 new 初始化才能使用其中的元素。 如果只是赋值了一个 null ，没有进行 new 创建，将会发生 “ 空指针异常：NullPointerException”
       /* int[] arrX = null;      // 必须加上 “ arrX = new int[3]; ”
        System.out.println(arrX[0]);*/

       System.out.println("=================D============");

        int[] arrX = null;
//        System.out.println(arrX[0]); // 【空指针异常：用 null 访问了空间】
//        int i = arrX.length; // 【空指针异常：用 null 调用了属性】
        arrX = arr; // 把第5行 arr 的地址值赋值给 arrX, arrX 指向 arr[i] ，获得其地址值，也即 arrX[i] = arr[i]
        System.out.println(arr[0]); // 1
        System.out.println(arr[1]); // 2
        System.out.println(arr[2]); // 3
        System.out.println("======A======");

        String[] strs = new String[3];
        String str1 = strs[1];
        System.out.println(str1); // null
//        System.out.println(str1.length()); // 【空指针异常：用 null 调用了方法】，“方法名（）” (length()是一个方法)
        //“str1.length()” 即表示 null 调用方法 “length()”,会出现空指针异常
        System.out.println("=========C=========");

        //  将数组内容赋值
        int a = arr[0];
        System.out.println(a);  // 1

        int[] arr1 = new int[3];
        System.out.println(arr1); // 内存地址值
        System.out.println(arr1[0]); // 0
        System.out.println(arr1[1]); // 0
        System.out.println(arr1[2]); // 0
        arr1[1] = 123; // 把 123 赋值给整型数组 arr1[] 的 1 号元素
        System.out.println(arr1[1]); // 123

        // 【注意】对于静态初始化数组，也有默认值的过程，只不过系统马上将大括号中的值赋值给默认值

        byte[] arr2 = new byte[3];
        System.out.println(arr2[0]); // 0
        short[] arr3 = new short[3];
        System.out.println(arr3[0]); // 0
        long[] arr4 = new long[3];
        System.out.println(arr4[0]); // 0

        float[] arr5 = new float[3];
        System.out.println(arr5[0]); // 0.0
        double[] arr6 = new double[3];
        System.out.println(arr6[0]); // 0.0

        char[] arr7 = new char[3];
        System.out.println(arr7[0]); // '\u0000' 不可见
        System.out.println("1" + arr7[1] + "1"); // '\u0000' 不可见
        System.out.println("1" + '\u0000' + "1");

        boolean[] arr9 = new boolean[3];
        System.out.println(arr9[2]); // 默认false

        String[] arr8 = new String[3];
        System.out.println(arr8[0]); // 默认null

        String str = "Hello";
        System.out.println(str); // Hello
    }
}
