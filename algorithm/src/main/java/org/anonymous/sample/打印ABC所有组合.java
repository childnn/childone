package org.anonymous.sample;

import java.util.ArrayList;
import java.util.List;

public class 打印ABC所有组合 {
    public static void main(String[] args) {
        String s = "ABC";
        char[] c = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= c.length; i++) {
            permutation(c, 0, i, sb);
        }

    }

    private static void permutation(char[] c, int begin, int len, StringBuffer sb) {
        if (len == 0) {                //当都选择结束时打印sb内容
            System.out.println(sb + " ");
            return;
        }

        if (begin == c.length)
            return;


        sb.append(c[begin]);                //取
        permutation(c, begin + 1, len - 1, sb);    //剩下的里面选len-1个
        sb.deleteCharAt(sb.length() - 1);    //不取
        permutation(c, begin + 1, len, sb);    //剩下的里面选len个

    }

    List<List<Integer>> handle(int[] nums) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();

        int size = nums.length;
        for (int i = 0; i < (1 << size); i++) {
            List<Integer> ax = new ArrayList<Integer>();
            for (int j = 0; j < size; j++) {
                if ((i & (1 << j)) != 0) {
                    ax.add(nums[j]);
                }
            }
            list.add(ax);
        }

        return list;
    }
}
