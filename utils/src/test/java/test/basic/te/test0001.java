package test.basic.te;

public class test0001 {

    private static int x = 100;

    public static void main(String[] args) {

        test0001 t1 = new test0001();
        t1.x++;
        test0001 t2 = new test0001();
        t2.x++;
        t1 = new test0001();
        t1.x++;
        test0001.x--;
        System.out.println(x);

        byte a = 3;
        byte b = 4;
//        byte c = a + b; // error
        byte d = 3 + 4;
    }

}
