package cn.itheima;

import java.util.ArrayList;

/**
 * 2019年2月14日11:44:58
 * 集合的toArray()方法返回值是Object[],无法强制转换
 * 在编译之后，集合的具体泛型将不会进入字节码文件，.java --> .class。JVM无法知道具体Object[] 对应什么数组类型
 * 1.除非父引用本身就指向子对象，如Test01所示
 * 2.使用toArray(T[] arr)方法
 */
public class Test00 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        list.add("123");
        list.add("123");

        Object[] objects = list.toArray();
        //        String[] s = (String[]) objects; //运行错误，ClassCastException
        //        String[] str = (String[]) new ArrayList<String>().toArray();  //ClassCastException
        String[] arr = list.toArray(new String[list.size()]); //ok
    }
}

class Test01 {
    public static void main(String[] args) {
        Object[] obj = new String[20];
        String[] str = (String[]) obj; //ok
    }
}