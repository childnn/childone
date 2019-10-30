package org.anonymous;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/16 14:56
 * 返回 n 的二进制表示中有几个 1
 */
public class HammingWeight {
    // 0001 0111
    public static void main(String[] args) {
        int i = hammingWeight(23);
        System.out.println(i);
    }

    private static int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            n = n & (n - 1);
            res++;
        }
        return res;
    }

}
