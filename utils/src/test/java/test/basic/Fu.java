package test.basic;

public class Fu {

    public Fu() {
        System.out.println("父类无参构造");
    }

    public Fu(int num) {
        System.out.println("父类有参构造" + num);
    }

    public static void get() {
        System.out.println("123");
    }

    public static void main(String[] args) {
//        new Abstract() //  抽象类不可以 new 对象

    }

}
