package test.basic;

public class Test {
    public static void main(String[] args) {
        InterA iA = a -> System.out.println("lambda " + a);
        iA.g(100); //lambda 100
        iA = T::m;
        iA = a -> T.m(a); //上下等价
        iA.g(19); //static 19

        //三者等价
        iA = new T()::f;
        iA = a -> new T().f(a); //方法调方法:参数传递
        iA = new InterA() {
            @Override
            public void g(int a) {
                new T().f(a);
            }
        };
        iA.g(1); //non-static 1
    }
}

@FunctionalInterface
interface InterA {
    void g(int a);
}

class T {
    public static void m(int a) {
        System.out.println("static " + a);
    }
    public void f(int a) {
        System.out.println("non-static " + a);
    }
}
