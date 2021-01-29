package cn.itheima;

public class VarParameter {
    public static void main(String[] args) {

    }

    public static void f(int... e) { //可变参数的本质就是一个长度可变的数组

    }

    //    public static void f(int[] e){} //error
    //    public static void g(int... e, double x) {} //可变参数必须位于参数末尾
    public static void f(int i, int... num) {
    } //ok

    public static void f(int e) {

    }
}
