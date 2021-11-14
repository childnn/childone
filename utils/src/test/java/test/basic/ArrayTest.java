package test.basic;

public class ArrayTest {

    public static void main(String[] args) {
        char[] chs = new char[4];
        chs = new char[]{ 'J', 'A', 'V', 'A' };

        int i = chs.length;
        for (int j = 0; j < i; j++) {
            System.out.print(chs[j]);
        }

        System.out.println();

        for (int j = 0; j < chs.length; j++) {
            System.out.println(chs[j]);
        }

        int[] arrA = new int[3];
        int[] arrB = new int[3];

        System.out.println(arrA);
        System.out.println(arrB);
    }

}
