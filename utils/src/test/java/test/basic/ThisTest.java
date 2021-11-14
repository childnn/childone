package test.basic;

public class ThisTest {

    public static void main(String[] args) {
        This t = new This();
        t.name = "Jack"; // 这里的 【name】是【this.name】(即：成员变量 name)
        t.sayHello("缪万");

        System.out.println(t); // （test.basic.This@6a5fc7f7）对象 t 即 this，同地址
    }

}
