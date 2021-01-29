package mw;

/**
 * 2019年3月4日19:59:35
 */
public class BinaryTest {
    public static void main(String[] args) {
        int a = 3; //0011
        int b = 6; //0110
        int c = a | b; //0111 == 7
        int d = a & b; //0010 == 2
        int e = a ^ b; //0101 == 5
        int f = ~a; //1100 == 负数,取反加一  -4
        System.out.println(c); //7
        System.out.println(d); //2
        System.out.println(e); //5
        System.out.println(f); //-4
    }
}
