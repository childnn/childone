package test.basic;

import java.util.ArrayList;

/**
 * 第三题:
 1.创建事务描述类Dog,包含空参构造、满参构造和以下成员变量:
 品种 kind  String类型
 年龄 age   int类型
 花色 color String类型
 生成以上成员的get/set方法

 在测试类中完成如下需求:
 2.根据以下信息通过满参构造创建三个对象,并将他们装入集合
 秋田犬-5-黄色  二哈-3-黑白  单身狗-23-黄色

 3.定义public static ArrayList<Dog> filter(ArrayList<Dog> list) {...}方法:
 要求：遍历list集合，将list中年龄大于10的元素存入到另一个ArrayList<Dog> 中并返回

 4.在main方法内调用filter方法传入2中的集合，根据返回的list集合输出元素信息
 示例如下：
 单身狗-23-黄色
 */
public class test73 {

    public static void main(String[] args) {
        Dog a = new Dog("秋田犬", 5, "黄色");
        Dog b = new Dog("二哈", 3, "黑白");
        Dog c = new Dog("单身狗", 23, "黄色");
        ArrayList<Dog> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);

        for (int i = 0; i < filter(list).size(); i++) {
            System.out.println(filter(list).get(i).getKind() + "-" + filter(list).get(i).getAge() + "-" + filter(list).get(i).getColor());
        }

    }

    public static ArrayList<Dog> filter(ArrayList<Dog> list) {
        ArrayList<Dog> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
            if (list.get(i).getAge() > 10) {
                list1.add(list.get(i));
            }
        }

        return list1;
    }

}
