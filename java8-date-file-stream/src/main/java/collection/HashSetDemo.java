package cn.itheima;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashSetDemo {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(10);
        set.add(9);
        set.add(9);
        set.add(-8);
        set.add(8);
        //迭代器
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            Integer i = it.next();
            System.out.println(i);
        }
        //增强for
        for (Integer i : set) {
            System.out.println(i);
        }
        Object[] obj = set.toArray();
        for (int i = 0; i < obj.length; i++) {
            System.out.println(obj[i]);
        }
        String s = set.toString();
        System.out.println(s);
        for (int i = 0; i < s.length(); i++) {
            System.out.print(s.charAt(i) + " "); //[ 1 ,   - 8 ,   8 ,   9 ,   1 0 ]
        }
    }
}
