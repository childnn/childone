package test.basic;

public class StudentPrivateTest {

    public static void main(String[] args) {
        // 定义对象【i】
        StudentPrivate i = new StudentPrivate();

        // 给对象的【全局变量】赋值：【对象名.set全局变量名（首字母大写）(实参列表)
        i.setName("缪万");
        i.setAge(24);
        i.setMale(true);

        // 输出对象的【属性】：【对象名.get全局变量名（首字母大写）()】
        System.out.println(i.getName() + i.getAge() + "岁" + "，male：" + i.isMale());

        // 通过对象调用方法：【对象名.方法名(实参类表)】
        i.eat("葡萄"); // 直接调用【eat】方法
        System.out.println("睡觉" + i.sleep() + "小时"); // 输出调用【sleep】方法
    }

}
