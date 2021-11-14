package test.basic;

public class Test000 {

    public static void main(String[] args) {
        // 匿名内部类，m 是对象名
        Myinter m = new Myinter() {
            @Override
            public void method() {
                System.out.println("重写接口method方法");
            }

            @Override
            public void get() {
                System.out.println("重写接口get方法");
            }

//            public default void set() { // 只能接口拥有默认方法
//
//            }
        };
        m.set();
//        m.f(); // error
        Myinter.f(); // 静态方法接口名调用

        // 匿名内部类，匿名对象
        new Myinter() {
            @Override
            public void method() {
                System.out.println("匿名内部类的匿名对象");
            }

            @Override
            public void get() {

            }
        }.method();
    }
}

abstract interface Myinter {

    public abstract void method();
    public abstract void get();

    public default void set() {
        System.out.println("接口默认");
    }
    public static void f() {
        System.out.println("接口静态");
    }
    // jdk9
    // private void g() {
    //     System.out.println("接口私有");
    // }
    // private static void h() {
    //     System.out.println("接口静态私有");
    // }

}