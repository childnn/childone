package test.basic;

public class StudentParam {

    public static void main(String[] args) {
        Student a = new Student();
        a.age = 10;
        a.name = "Jack";
        System.out.println("name:" + a.name);
        System.out.println("age:" + a.age);
        a.eatFood(" banana");
        System.out.println("sleep " + a.sleep() + " hours");
        System.out.println("==========main=============");

        studentParam(a); // 把 对象 a 所指向的地址值赋值给方法【studentParam】-->也即【形参 stu】，此时 对象 a 所指向的地址中的内容由方法确定
        // 这与 基本数据类型 不同 【参见 MethodGet.class】,注意区别

//        Student stu = a;// 与调用方法等价

        System.out.println("===========param============");
        System.out.println("name:" + a.name);
        System.out.println("age:" + a.age);
        a.eatFood(" banana");
        System.out.println("sleep " + a.sleep() + " hours");


    }

    private static void studentParam(Student stu) { // 方法的参数类型为 【Student】类 ，相当于在 main 方法中 new 了一个 Student 【类】 的【对象】，对象名为 stu
        stu.age = 21;
        stu.name = "Rose";
//        System.out.println(stu.name);
//        System.out.println(stu.age);
        stu.eatFood(" pancake");
//        System.out.println("sleep " + stu.sleep() + " hours");

    }

}
