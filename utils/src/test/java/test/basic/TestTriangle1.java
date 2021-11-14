package test.basic;

public class TestTriangle1 {

    public static void main(String[] args) {
      /*  int a = 3;
        int b = 4;
        int c = 5;
//        Triangle.ZhouChang(a, b, c); // error
        Triangle.Area(a, b, c);*/
        test.Triangle.Area(3, 4, 5);

        test.Triangle1 x = new test.Triangle1(1); // 为什么？【this】
        B i = new B(1);

    }

}
