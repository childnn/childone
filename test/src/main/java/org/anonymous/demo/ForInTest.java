package org.anonymous.demo;

import java.util.Arrays;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/14 11:07
 */
public class ForInTest {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        // 编译后文件
        //        Iterator var2 = list.iterator();
        //
        //        while(var2.hasNext()) {
        //            Integer i = (Integer)var2.next();
        //            System.out.println("i = " + i);
        //        }
        for (Integer i : list) {
            System.out.println("i = " + i);
        }
    }

}
