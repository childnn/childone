package test.basic;

public class StudentReturn {

    public static void main(String[] args) {
        Student stu = getStudent();  // getStudent() 方法的 返回值 是一个 地址值
        System.out.println(stu.name);
        System.out.println(stu.age);
        stu.eatFood("大米");
    }

    private static Student getStudent(){
        Student stu = new Student();
        stu.name = "小米";
        stu.age = 10;
        stu.eatFood("race");
//        System.out.println("sleep " + stu.sleep() + "hours");

        return stu; // return 对象 stu 指向的地址值
    }

}
