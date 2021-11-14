package test.basic;

public class Triangle {
// 成员变量
    /*private*/ int a, b, c;
    /*public void main(String[] args) {

    }*/

    double dou;
    String str;
    char cha;
    boolean boo;
// 成员方法
    public void ZhouChang(int a, int b, int c) {
        if ((a + b) > c && (a + c) > b && (b + c) > a && (a - b) < c && (a - c) < b && (b - c) < a) {
            System.out.print("三角形周长：");
            System.out.println(a + b + c);
        } else {
            System.out.println("所给三个数字不能构成三角形");
        }
    }
// 有 “static” 的就不是成员方法？ static方法的调用：可以直接【类名.方法名（）;】
    public static void Area(int a, int b, int c) {
        double p = 1.0 * (a + b + c) / 2;
        double area = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        if ((a + b) > c && (a + c) > b && (b + c) > a && (a - b) < c && (a - c) < b && (b - c) < a) {
            System.out.println("三角形面积是：" + area);
        } else {
            System.out.println("所给三个数字不能构成三角形");
        }
    }

}
