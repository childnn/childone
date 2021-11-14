package test.basic;

// 对象的名称也叫对象的【引用名】（引用数据类型）
public class StudentTest {

    public static void main(String[] args) {
        Student stu = new Student(); // new 一个 Student 【类】 中的 【对象】 stu ,内存给对象 stu 分配一块地址，【固定不变】
        System.out.println(stu); // test.basic.Student@1c53fd30，新建对象，这个地址会发生变化
        System.out.println(stu.age); // 0
        System.out.println(stu.name); // null
        System.out.println(stu.c); // 空字符
        System.out.println(stu.dou); // 0.0
        System.out.println(stu.stu); // null

        stu.age = 24;
        stu.name = "缪万";
        System.out.println(stu); // test.basic.Student@1c53fd30
        System.out.println(stu.age); // 24
        System.out.println(stu.name); // 缪万

        stu.eatFood("葡萄");
//        System.out.println(stu.eatFood(""));
        System.out.println("每天睡"+ stu.sleep() + "小时");

        System.out.println("=======================");
// 新对象
        Student stu1 = new Student();
//        int[] arr = new int[3];
        System.out.println(stu1); // test.basic.Student@39ba5a14,同样的类，新的对象，有不同的地址值

        stu1.age = 32;
        stu1.name = "小明";
        System.out.println(stu1.age);
        System.out.println(stu1.name);

        System.out.println("======================");
// 把对象 stu1 中保存的地址 赋值 给 对象 stu，此时 对象 stu 也拥有 stu1 中的地址
        stu = stu1;
        System.out.println(stu); // test.basic.Student@39ba5a14

        System.out.println(studentReturn()); //

        studentParam(stu); // 调用studentParam() 方法

//        studentParam();

    }

    public static Student studentReturn() {
        Student stu = new Student(); // Student 类中又一个新的对象 stu， 地址也跟上面的 main 方法中的 两个对象不同
        return stu;
    }

    public static void studentParam(Student a) { // 形参【a】 接受到 main 方法中的 对象 stu 中的地址（也即 stu1 中的地址），指向 stu1 中保存的地址值所在处
        System.out.println("小明"); // 小明
        System.out.println(a.age); // 32
        System.out.println(a.name); // 小明
    }
}
