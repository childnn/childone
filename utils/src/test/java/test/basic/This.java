package test.basic;

// 当方法中的【局部变量】与类中的【成员变量】重名时，根据就近原则：【局部变量】会【屏蔽】【成员变量】，
// 要想访问本垒中的成员变量，需要使用【this.成员变量名】
// 通过谁调用方法，谁就是 this，把 this 看成 对象名
public class This {
    String name; // 成员变量：赋值对方的名字

    public void sayHello(String name) { // 形参：自己的名字
        System.out.println(this.name + ",你好！我是" + name);
        System.out.println(this.name);
        System.out.println(this); // test.basic.This@6a5fc7f7
    }

}
