package org.anonymous.sample;

/**
 * Created with IntelliJ IDEA.
 * Date: 19-12-24
 * Time: 上午9:54
 */
public class 打印abc {
    public static void main(String[] args) {
        String s = "abc";
        char[] chs = s.toCharArray();
        handlde(chs);
    }

    public static void handlde(char[] chs) {
        int len = chs.length;
        int nbits = 1 << len;
        for (int i = 0; i < nbits; ++i) {
            int t;
            for (int j = 0; j < len; j++) {
                t = 1 << j;
                if ((t & i) != 0) {
                    System.out.print(chs[j]);
                }
            }
            System.out.println();
        }
    }
}
