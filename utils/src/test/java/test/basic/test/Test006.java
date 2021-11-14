package test.basic.test;

public class Test006 {
    static int i = 1;

    public static void main(String[] args) {
        Test006 test006 = new Test006();
//        System.out.println(test006); // 重写 toString 方法，打印对象名和对象调用toString 方法等价
//        System.out.println(test006.toString());
        System.out.println("love" + new Test006()); // 先打印 “I”，再返回“java”
        Test006 a = new Test006();
        a.i++; // i = 2;
        System.out.println("me" + a.i);
    }

    @Override
    public String toString() {
        System.out.println("I");
        return "java";
    }
}
