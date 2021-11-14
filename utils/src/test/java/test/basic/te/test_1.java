package test.basic.te;

import java.util.Arrays;

class test {

    public static void main(String[] args) {
        String[] arr = new String[3];
        String str = Arrays.toString(arr);
        System.out.println(str); // [null, null, null]
        System.out.println(arr[3]); // error, 索引越界，运行报错

        byte a = 1;
        byte b = 2;
        byte c = 1 + 2; // 等价于 byte c = 3;
        byte d = (byte) (a + b);
//        byte e = a + b; // error, 程序加法开始运行，a，b是变量，其值可以发生改变，程序无法判断 a + b 是否为 byte

//        System.out.println(20+'A'-"A"); // error, 字符串不能做减法
    }

}
