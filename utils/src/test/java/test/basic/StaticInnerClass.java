package test.basic;

public class StaticInnerClass {

    public static void main(String[] args) {
        Outer.Inner inner = new Outer.Inner(); // 外部类静态成员，直接外部类类名调用内部类的构造方法，区别于非静态内部类的对象创建方式
        OuterClass.InnerClass innerClass = new OuterClass().new InnerClass(); // 先有外部类的对象，在通过外部类对象调用内部类对象
        // 可以分为下面两个步骤
        OuterClass outerClass = new OuterClass();
        OuterClass.InnerClass innerClass1 = outerClass.new InnerClass();
    }
}

class Outer {
    int num = 10;
    static class Inner {
        int num = 20;
        public void getIn() {
            int num = 30;
            System.out.println("in");
            System.out.println(num);
            System.out.println(this.num);
//            System.out.println(Outer.this.num); //error, 静态不能使用非静态 对于重名的变量，非静态内部类可以这样写：外部类名.this.变量名
        }
    }

    public void getOut() {
        System.out.println("out");
    }
}

class Father {
    Father() {
        f();
    }
    void f() {
        System.out.println("father");
    }

    static class Son extends Father {
        @Override
        void f() {
            System.out.println("son");
        }
    }

    public static void main(String[] args) {
        Father fa = new Son(); //静态优先加载: son
        fa.f(); //son
    }
}