package test.basic.te;

public class test01 {

    public static void main(String[] args) {

        Father f = new Son();
        f.test();
        f.test2();
        Son s = new Son();
        s.test();
        s.test2();
    }
}

class Father {

    static void test() {
        System.out.println("父类静态");
    }
    void test2() {
        System.out.println("父类非静态");
    }

}

class Son extends Father {
//    @Override
    static void test() {
        System.out.println("子类静态");
    }
    @Override
    void test2() {
        System.out.println("子类非静态");
    }

}
