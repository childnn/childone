package test.basic;

public class Method1 {

    public static void main(String[] args) {
        boolean equal = isEqual(10, 10); // isEqual(10, 10).var;   赋值快捷键: 方法名（参数列表）.var
        boolean equal1 = isEqual(10, 20);
        System.out.println("equal1 = " + equal1);
        System.out.println("equal = " + equal);
        System.out.println(1 / 10);
        boolean equal2 = isEqual(12, 13);
    }

    public static boolean isEqual(int i, int j) {
        return i == j;
    }

  /*  public static boolean isEqual1(int i, int j) {
        if (i == j) {
            return false;
        } else {
            return true;
        }
    }*/

}
