package test.basic;

public class Static001Test {

    public static void main(String[] args) {
        Static001 a = new Static001();
        Static001 b = new Static001();
        Static001 c = new Static001(10);
        Static001 d = new Static001(10,20);
//        Static001 e = new Static001(10,20,30);
        int count = d.getCount();
        System.out.println(count); // 已创建对象的个数
    }
}
