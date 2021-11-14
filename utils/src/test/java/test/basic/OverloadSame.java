package test.basic;

public class OverloadSame {

    public static void main(String[] args) {
        byte a = 10;
        byte b = 20;
        System.out.println(isSame(a, b));

        System.out.println(isSame(12, 13));

        System.out.println(isSame((short)10, (short)9));

        System.out.println(isSame(3L, 4L));
    }

    public static boolean isSame(byte a, byte b) {
        System.out.println("两个byte参数的比较：");

        boolean x;

        if (a > b) {
            x = true;
        } else {
            x = false;
        }

        return x;
    }

    public static boolean isSame(int a, int b) {
        System.out.println("两个int参数的比较");

        if (a > b) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSame(short a, short b) {
        System.out.println("两个short参数的比较");

        return a > b ? true : false;
    }

    public static boolean isSame(long a, long b) {
        System.out.println("两个long参数的比较");

        return a > b;
    }
}
