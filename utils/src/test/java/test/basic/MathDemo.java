package test.basic;

/**
 * public static double abs(double num): 绝对值
 * public static double ceil(double num): 向上取整
 * public static double floor(double num): 向下取整
 *     // 或者强转 int，向下取整
 * public static long round(double num): 四舍五入
 */
public class MathDemo {

    public static void main(String[] args) {
        System.out.println(Math.abs(-3.14));
        System.out.println(Math.abs(-0));
        System.out.println("=================");
        System.out.println(Math.sqrt(16));
        System.out.println(Math.ceil(3.0));
        System.out.println(Math.ceil(3.1));
        System.out.println(Math.ceil(3.9));
        System.out.println("================");
        System.out.println(Math.floor(3.9));
        System.out.println(Math.floor(3.0));
        System.out.println(Math.floor(3.1));
        System.out.println((int) 3.9);
        System.out.println("=================");
        System.out.println(Math.round(3.14));
        System.out.println(Math.round(3.49));
        System.out.println(Math.round(3.5));
        System.out.println(Math.round(3.9));
        System.out.println("================");
        System.out.println(Math.PI);
        System.out.println(Math.max(12, 13));


    }

}
