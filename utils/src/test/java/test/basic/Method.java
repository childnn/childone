package test.basic;

public class Method {

    public static void main(String[] args) {
       printMethod(10);
    }

    public static void printMethod(int num) {
        for (int i = 1; i <= num; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        for (int i = num - 1; i >= 0 ; i--) {
            for (int j = 1 ; j <= i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        System.out.println("num = " + num); // 快捷键soutv
}


}
