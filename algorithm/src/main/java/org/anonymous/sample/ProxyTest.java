package org.anonymous.sample;

import java.util.ArrayList;
import java.util.List;

public class ProxyTest {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        System.out.println(list);

        List<Integer> list2 = list;
        list2.add(5);
        System.out.println(list);
        System.out.println(list2);
    }

}
