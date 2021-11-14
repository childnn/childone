package test.basic;

// 获取数组长度：“ 数组名称.length ”
// 数组一旦定义，就在内存中获取了相应长度的内存空间（一个地址），长度不可变化
public class ArrayLength {

    public static void main(String[] args) {
        int[] arrA = new int[3];
        int[] arrB = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 11, 13, 15};
        System.out.println(arrA); // [I@50cbc42f
        System.out.println(arrB); // [I@75412c2f

        int i = arrB.length;
        System.out.println("arrB的长度是：" + i); // 11

        arrB = arrA; // 把 arrA 的地址值赋值给 arrB,此时 arrB 指向 arrA[], arrB 的数组长度也跟随 arrA 变化了
        // 这里不可能把数组 arrA 数组地址所在的数组内容赋值给 arrB 数组地址,因为数组长度不同。
        int j = arrB.length;
        System.out.println(arrA); // [I@50cbc42f
        System.out.println(arrB); // [I@50cbc42f
        System.out.println("arrB的长度是：" + j); // 3，数组 arrA 引用给 arrB
        System.out.println(arrB[2]); // 0
    }

}
