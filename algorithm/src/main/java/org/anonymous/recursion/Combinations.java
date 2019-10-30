package org.anonymous.recursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/4 10:39
 * 将给定数据中的 n 个元素组合, 输出组合的所有情况.
 */
public class Combinations {

    public static void main(String[] args) {
        Combinations c = new Combinations();
        c.combinations(new ArrayList<>(), Collections.emptyList(), 0);
        System.out.println("==================================");
        c.combinations(new ArrayList<>(), Collections.emptyList(), 1);
        System.out.println("==================================");
        c.combinations(new ArrayList<>(), Collections.singletonList(1), 1);
        System.out.println("==================================");
        c.combinations(new ArrayList<>(), Arrays.asList(1, 2, 3, 4, 5), 2);
        System.out.println("==================================");
        c.combinations(new ArrayList<>(), Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), 4);
    }

    /**
     * Generates all combinations and output them,
     * selecting n elements from data.
     *
     * @param data
     * @param n
     */
    public void combinations(List<Integer> selected, List<Integer> data, int n) {
        // initial value for recursion
        // how to select the zero element?
        // hwo to output?
        if (n == 0) {
            // output empty list
            // output all selected elements
            for (Integer i : selected) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
            return;
        }
        if (data.isEmpty()) {
            /*if (n == 0) {
                // output empty list
            }*/
            return;
        }
       /* if (n < 0) {
            return;
        }*/
        /*if (n == 0) {
            // output empty list
            return;
        }*/

        // select element 0
        selected.add(data.get(0));
        combinations(selected, data.subList(1, data.size()), n - 1);

        // un-select element 0
        selected.remove(selected.size() - 1);
        combinations(selected, data.subList(1, data.size()), n);
    }

}
