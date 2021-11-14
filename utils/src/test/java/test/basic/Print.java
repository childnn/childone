package test.basic;

public class Print {

    public static void main(String[] args) {
        toPrint(6, 5);
    }

    public static void toPrint(int x, int y) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
