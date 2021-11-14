package test.basic;

public class Overload {

    public static void main(String[] args) {

    }

    public static int overload() {
        return 0;
    }

    // error,与返回值类型无关
   /* public static void overload() {

    }*/
   // error,与修饰符无关
   /*static int overload() {
       return 0;
   }*/


    public static int overload(int a) {
        return 0;
    }

    // 与形参个数有关
    public static int overload(int a, int b) {
        return 0;
    }

    // 与形参类型有关
    public static int overload(double a) {
        return 0;
    }

    // 与多形参顺序有关
    public static int overload(int a, double b) {
        return 0;
    }
    public static int overload(double a, int b) {
        return 0;
    }
}
