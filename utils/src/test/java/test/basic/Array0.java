package test.basic;

public class Array0 {
    public static void main(String[] args) {
        // 【重点】【创建；增删改查；遍历】
        int[] arrA = new int[10]; // 动态初始化数组，数组长度为10,数据类型为int。
        double[] arrC = new double[10];
        String[] arrD = new String[5];

        int[] arrB = new int[]{1, 2, 3}; // 静态初始化数组，数据类容确定。【标准格式】
        String[] arrE = new String[]{"Hello", "World", "Java"};

        int[] arrF = {2, 3, 4}; // 静态初始化【简化格式】

        // 区别: 分步的第二步必须要 new ，不能直接在赋值时写大括号
        // 静态初始化标准格式可以拆分成两个步骤
        int[] arrG;
        arrG = new int[]{1, 2, 3};

        // 动态初始化也可以拆分成两个步骤
        int[] arrH;
        arrH = new int[3];

        // 但是静态初始化的省略格式不可以拆分成两个步骤
        int[] arrI;
//        arrI = {1, 2, 3}; // ERROR，int 类型的数组【arrI】只能接收地址值，只有new出来的地址值可以被接收，这里{1, 2, 3} 不表示一个地址值，不能直接赋值给 arrI!
       // System.out.println(arrI); // error, 未初始化

//        int[] arrJ = new int[3]{1, 2, 3}; // error
      /*  arrI = new int[]{};
        System.out.println(arrI[0]); // ArrayIndexOutOfBoundsException, arrI 所指向的地址为null，arrI[] 的内容为空*/

    }
}
