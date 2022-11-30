package test.basic;

public class TestTriangle {

    public static void main(String[] args) {
        Triangle t = new Triangle();

//      成员变量默认值与数组相应的默认值相同
//         System.out.println(t.a); // int 成员变量默认值: 0
//         System.out.println(t.dou); // double 成员变量默认值： 0.0
//        System.out.println(Triangle.str); // error
//         System.out.println(t.str); // String 成员变量默认值：null
//         System.out.println(t.cha); // '\u0000' 空字符
//         System.out.println(t.boo); // false

//      给成员变量赋值
//         t.a = 3;
//         t.b = 4;
//         t.c = 5;
//        Triangle.ZhouChang(t.a, t.b, t.c); //error
//         t.ZhouChang(t.a, t.b, t.c);

        // t.Area(t.a, t.b, t.c);
        // Triangle.Area(t.a, t.b, t.c); // Area是静态（static）方法,可以直接【类名称.方法名】：

        // System.out.println(t); // test00.Triangle@1e643faf，【包名.类名@对象地址】

//        Triangle a = new ZhouChang(3, 4, 7);

//        ZhouChang a = new

      /*  int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        System.out.println(arr1.equals(arr2)); // false
        System.out.println(arr1.hashCode() == arr2.hashCode()); // false*/
    }

}
