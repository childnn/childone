package test.basic;

// 比较三个【键盘录入整数】的最大值

import java.util.Scanner;

class test001_ {

    public static void main(String[] args) {
        // 方法一：无参overload方法
        System.out.println("max1 = " + getMax());
        System.out.println("=========first==========");
        // 方法二：一个参数overload方法
        System.out.println("max2 = " + getMax(new Scanner(System.in)));
        System.out.println("=========second==========");
        // 方法三：全参overload方法
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入第一个整数：");
        int i = sc.nextInt();
        System.out.println("请输入第二个整数：");
        int j = sc.nextInt();
        System.out.println("请输入第三个整数：");
        int t = sc.nextInt();
        System.out.println("max3 = " + getMax(i, j, t));
        System.out.println("=========second==========");
        // 方法四：无返回值
        getMax1(sc);

    }

    public static int getMax() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入第一个整数：");
        int i = sc.nextInt();
        System.out.println("请输入第二个整数：");
        int j = sc.nextInt();
        System.out.println("请输入第三个整数：");
        int t = sc.nextInt();

        int max = i > j ? i : j;
        max = max > t ? max : t;

        return max;
    }

    public static int getMax(Scanner sc) {
        System.out.println("请输入第一个整数：");
        int i = sc.nextInt();
        System.out.println("请输入第二个整数：");
        int j = sc.nextInt();
        System.out.println("请输入第三个整数：");
        int t = sc.nextInt();

        int max = i > j ? i : j;
        max = max > t ? max : t;

        return max;
    }

    public static int getMax(int i, int j, int t) {
        int max = i > j ? i : j;
        max = max > j ? max : j;
        return max;
    }

    public static void getMax1(Scanner sc) {
        System.out.println("请输入第一个整数：");
        int i = sc.nextInt();
        System.out.println("请输入第二个整数：");
        int j = sc.nextInt();
        System.out.println("请输入第三个整数：");
        int t = sc.nextInt();

        int max = i > j ? i : j;
        max = max > t ? max : t;

        System.out.println("max4 = " + max);
    }
}
