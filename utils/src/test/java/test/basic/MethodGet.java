package test.basic;

public class MethodGet {

    public static void main(String[] args) {
        int a = 10;
        System.out.println("first:" + a);

        get(a);

        System.out.println("third:" + a); // 如果这里也变成 100， 那么说明 整型变量 a 随着【get()】方法变化了，也说明 整型变量 a 指向一个地址。（很显然不是）
//        区别于【引用数据类型】【参见 StudentParam.class】
    }

    public static void get(int a) {
        a = 100;
        System.out.println("second:" + a);
    }
}
