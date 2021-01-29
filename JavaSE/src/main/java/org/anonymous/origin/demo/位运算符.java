package org.anonymous.origin.demo;

/**
 * 1 & 1 == 1       1 | 1 == 1      1 ^ 1 == 0      ~ 1 == 0
 * 1 & 0 == 0       1 | 0 == 1      1 ^ 0 == 1      ~ 0 == 1
 * 0 & 1 == 0       0 | 1 == 1      0 ^ 1 == 1
 * 0 & 0 == 0       0 | 0 == 0      0 ^ 0 == 0
 * <p>
 * ~ 按位取反: 所有二进制位取反
 * ^ 按位异或: 相同为0,不同为1
 * << 按位左移
 * i<<n, 把i的所有二进制位左移n位, 相当于i乘以2的n次方
 * >> 按位右移
 * i>>n, 除以2的n次方
 * <p>
 * 位运算符的意义:
 * 可以把数据的运算精确到位
 */
public class 位运算符 {
    public static void main(String[] args) {
        int i = 5; //0101
        int j = 8; //1000
        int k;

        k = i & j; //0000
        System.out.println(k); //0

        //        boolean b = i && j;
        j = 7; //0111
        k = i & j; //0101
        System.out.println(k);
        j = 21; //0001 0101
        System.out.printf("%#X, %d\n", j, j);
        k = i & j; //0000 0101
        System.out.println(k);

        k = i | j; //0001 0101
        System.out.println(k);

        k = ~i; //1010 取反加一转十进制,负数
        System.out.println(k);

        k = i ^ j; //0001 0000
        System.out.println(k);

        int a, b;
        a = b = 0;


    }
}
