package test.basic;

/*【成员变量】和【局部变量】的区别：
 1. 在 class 中的位置不同
        成员变量：class 中，method 外
        局部变量：method 中
 2. 在内存中的位置不同：
        成员变量：堆内存
        局部变量：栈内存
 3. 初始化值：
        成员变量：有系统默认值【int 0， double 0.0，char 空字符，String null，boolean false】
        局部变量：未初始化局部变量不可使用
*4. 生命周期不同：
*       成员变量：随着对象的创建而产生，随着对象被垃圾回收而消失
*       局部变量：随着方法的进栈而产生，随着方法的弹栈而消失
*
* */
public class Object {
    private char c;
    public static void main(String[] args) {
       /* this.c = 12;
        System.out.println(this.c);*/
        String a = "我"; // 如果
        String b = a;
        b = "你";
        System.out.println(a);

        String x = new String();
        System.out.println(x);
        System.out.println(x + '1');

        String n = new String("他");
        System.out.println(n); // n 不是一个地址

        String p = new String( );
        System.out.println(p + "1");

        String q = new String(n);
        System.out.println(q);

        String[] i = new String[3];
        System.out.println(i); // [Ljava.lang.String;@48140564
        System.out.println(i[1]); // null

        int[] j = new int[2];
        System.out.println(j); // [I@58ceff1
        System.out.println(j[1]); // 0

    }

}
