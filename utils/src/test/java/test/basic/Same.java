package test.basic;

// 判断两个数是否相等，boolean类型返回值
public class Same {

    public static void main(String[] args) {
        System.out.println(isSame(10, 20));
        System.out.println(isSame(12, 12));
    }

    public static boolean isSame(int a, int b) {
//        boolean same = a == b; // 可以省略
        return a == b;
    }
}
