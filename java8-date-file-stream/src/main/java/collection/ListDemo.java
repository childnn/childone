package cn.itheima;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * List接口:是 Collection 的子接口
 * 特点:存取有序
 * 有索引
 * 元素可重复
 * <p>
 * public void add(int index, E element) : 将指定的元素，添加到该集合中的指定位置上。
 * public E get(int index) :返回集合中指定位置的元素。
 * public E remove(int index) : 移除列表中指定位置的元素, 返回的是被移除的元素。
 * public E set(int index, E element) :用指定元素替换集合中指定位置的元素,返回值的更新前的元素。
 */
public class ListDemo {
    public static void main(String[] args) {
        // 多态
        List<String> list = new ArrayList<>();
        // 直接添加元素
        list.add("a");
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        System.out.println(list); // 重写了toString方法,[a, a, b, c, d]
        // 添加元素到指定索引
        list.add(3, "itheima");
        System.out.println(list); // [a, a, b, itheima, c, d]
        //直接移除元素:第一次出现的位置
        boolean a = list.remove("a");
        System.out.println(list); // [a, b, itheima, c, d]
        // 移除索引对应的元素
        String removeE = list.remove(1);
        System.out.println("被移除的元素是:" + removeE); // 被移除的元素是:b
        System.out.println(list); // [a, itheima, c, d]
        // 替换指定索引处的元素为指定元素
        list.set(3, "X");
        System.out.println(list); // [a, itheima, c, X]

        // 遍历集合
        // for循环加get方法
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
        // 迭代器
        Iterator<String> it = list.iterator();
        ListIterator<String> sit = list.listIterator(); // ListIterator<E>是List的子接口,listIterator()是List接口的方法
        while (sit.hasNext()) { // 用it,sit,都可以
            String s = sit.next();
            System.out.print(s + " ");
        }
        System.out.println();
        // 增强for
        for (String s : list) {
            System.out.print(s + " ");
        }
    }
}
