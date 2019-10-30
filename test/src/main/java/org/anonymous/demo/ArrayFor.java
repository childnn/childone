package org.anonymous.demo;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 11:13
 */
public class ArrayFor {

    public static void main(String[] args) {
        // 编译后.....
        //  int[] arr = new int[]{1, 2, 3, 4};
        //        int[] var2 = arr;
        //        int var3 = arr.length;
        //
        //        for(int var4 = 0; var4 < var3; ++var4) {
        //            int i = var2[var4];
        //            System.out.println("i = " + i);
        //        }
        int[] arr = {1, 2, 3, 4};
        for (int i : arr) {
            System.out.println("i = " + i);
        }
    }
}
