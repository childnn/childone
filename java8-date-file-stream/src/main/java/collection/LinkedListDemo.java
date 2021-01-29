package cn.itheima;

import java.util.LinkedList;

/**
 * LinkedList:List 接口的实现类
 * 底层是链表结构:查询慢,增删快
 * 大量操作首尾元素的方法
 * 注意:不能使用多态
 * public void addFirst(E e) :将指定元素插入此列表的开头。
 * public void push(E e) :将元素推入此列表所表示的堆栈。增加到第一个
 * public void addLast(E e) :将指定元素添加到此列表的结尾。
 * <p>
 * public E getFirst() :返回此列表的第一个元素。
 * public E getLast() :返回此列表的最后一个元素。
 * <p>
 * public E removeFirst() : 移除并返回此列表的第一个元素。
 * public E pop() : 从此列表所表示的堆栈处弹出一个元素。移除第一个
 * public E removeLast() :移除并返回此列表的最后一个元素。
 * <p>
 * public boolean isEmpty() ：如果列表不包含元素，则返回true。
 */
public class LinkedListDemo {
    public static void main(String[] args) {
        //        List<String> Linked = new LinkedList<>(); // error,多态
        LinkedList<String> linked = new LinkedList<>();
        linked.add("a");
        linked.add("b");
        linked.add("c");
        linked.add("d");

        String first = linked.getFirst();
        System.out.println("first" + first);
        String last = linked.getLast();
        System.out.println("last" + last);

    }
    //    public static void
}
