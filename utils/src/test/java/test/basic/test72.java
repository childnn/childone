package test.basic;

import java.util.ArrayList;

/**
 *第二题:
 1.创建事务描述【类Person】,包含【空参构造】、【满参构造】和以下【成员变量】:
 学号 id    int类型
 姓名 name  String类型
 年龄 age   int类型
 生成以上成员的get/set方法

 2.根据以下信息创建三个对象,并将他们装入【集合】
 1-马尔扎哈-45  2-塔利斯塔-36  3-迪丽热巴-25

 3.遍历集合,将其中岁数小于30的对象删除,将余下的对象按照如下格式打印出来
 1-马尔扎哈-45
 */
public class test72 {

    public static void main(String[] args) {
        Person a = new Person(1, "马尔扎哈", 45);
        Person b = new Person(2, "阿利斯塔", 36);
        Person c = new Person(1, "迪丽热巴", 25);
        ArrayList<Person> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
            if (list.get(i).getAge() < 30) {
                list.remove(i);
                i--; // 重点
            } else {
                System.out.println(list.get(i).getId() + "-" + list.get(i).getName() + "-" + list.get(i).getAge());
            }
        }
    }

}
