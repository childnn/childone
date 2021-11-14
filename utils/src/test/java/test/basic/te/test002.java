package test.basic.te;

import java.util.ArrayList;

/**
 * 定义“学生类”Student包含空参构造、满参构造和以下成员变量：
 * 名称name（String类型）
 * 年龄 age（int类型）
 * 性别gender（String类型）
 * 生成以上成员变量的set/get方法
 * <p>
 * 定义测试类Test，完成以下要求：
 * ① 定义public static ArrayList<Student> filter(ArrayList<Student> list,int age) {...}方法:
 * 要求：遍历list集合，将list中年龄大于参数age的元素存入到另一个ArrayList<Student> 中并返回
 * ② 在main方法内完成以下要求:
 * a.根据以下内容创建并初始化3个Student对象
 * {"玛尔扎哈",30,"男"}
 * {"迪丽热巴", 18,"女"}
 * {"古力娜扎",20,"女"}
 * b.创建一个ArrayList<Student> list_student，将上面的3个Student对象添加到list_student中，调用filter方法传入list_student和19，根据返回的list集合输出所有元素信息
 */
public class test002 {

    public static void main(String[] args) {
        Student stu1 = new Student("玛尔扎哈", 30, "男");
        Student stu2 = new Student("迪丽热巴", 18, "女");
        Student stu3 = new Student("古力娜扎", 20, "女");
        ArrayList<Student> list_student = new ArrayList<>();
        list_student.add(stu1);
        list_student.add(stu2);
        list_student.add(stu3);
        ArrayList<Student> list = filter(list_student, 19);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName() + list.get(i).getAge() + list.get(i).getGender());
        }
    }

    public static ArrayList<Student> filter(ArrayList<Student> list,int age) {
        ArrayList<Student> stu = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getAge() > age) {
                stu.add(list.get(i));
            }
        }

        return stu;
    }

}
