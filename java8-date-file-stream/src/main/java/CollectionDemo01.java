package cn.itheima;

import java.util.ArrayList;
import java.util.Collection;

/*
    学习容器
        1.如何创建容器
            Collection<E> con = new ArrayList<E>();
                <E>:集合的泛型,泛型是用来约束集合中元素的数据类型
                    泛型也可以具备多态的特点: 当泛型写的是父类型,容器可以存储此类型所有子类对象
        2.增删改查四类方法 CRUD
            增:
                boolean add(E e)  :依次添加元素
            删:
                boolean remove(Object o): 按照元素值删除
                void clear() :清空集合中所有的元素
            改: Collection接口是没有改的方法,因为Collection集合是没有索引的
            查: Collection接口没有根据索引拿元素值的get方法
               boolean contains(Object o):判断集合中是否包含此元素
               boolean isEmpty()  :判断集合是否为空
               int size()  :获取集合中元素个数
        3.遍历容器
            1.转数组  -->  Object[] toArray()
            2.Iterator迭代器：不能用ListIterator列表迭代器
            3.超级for:没有索引，不能用普通for

    Collection<E>接口:单列集合的根节点
 */
public class CollectionDemo01 {
    public static void main(String[] args) {
        //创建集合
        Collection<String> con = new ArrayList<String>();
        System.out.println("con = " + con.toString());//概览
        //添加元素
        con.add("张三丰");
        con.add("张无忌");
        con.add("张翠山");
        System.out.println("con = " + con);
        //删除元素
        System.out.println(con.remove("周芷若"));
        System.out.println(con.remove("张无忌"));
        //con.clear();
        System.out.println("con = " + con);
        System.out.println("--------------------------");
        System.out.println(con.contains("张翠山"));
        System.out.println(con.contains("殷素素"));
        System.out.println(con.isEmpty());
        System.out.println(con.size());
        System.out.println("--------------------------");
        //转数组
        Object[] objs = con.toArray();
        //        String[] objs1 = (String[]) objs;
        for (int i = 0; i < objs.length; i++) {
            //强转
            String str = (String) objs[i];
            System.out.println("str = " + str);
        }
    }
}
