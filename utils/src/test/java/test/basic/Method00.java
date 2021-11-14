package test.basic;

// 【任何数据类型】都可以作为【方法】的【参数】和【返回值】
public class Method00 {
    public static void main(String[] args) {
        int i = sum(10, 20); // 赋值调用
        System.out.println("返回值是：" + i);
        System.out.println(sum(20, 40)); // 打印调用
        System.out.println("=================");

        sum1(20, 30); // 调用方法 sum1，由 sum1 方法输出 // 单独调用
    }

    public static int sum(int a, int b) {
        int result = a * b; // 把 result 返回给 main 方法，由 main 方法输出
        return result;
    }

    //    无返回值
    public static void sum1(int a, int b) {
        System.out.println(a * b); // 自己输出，没有返回值，不会返回给 main 方法
        return;
    }
}
