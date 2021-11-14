package test.basic.te;

/*
* 第四题:
定义一个方法printNum, 打印1-500(包含1和500)能同时被2，5，7整除的所有数, 并统计满足条件的个数, 在主方法中调用此方法完成测试.

* */
public class Test4 {

    public static void main(String[] args) {
        printNum();
    }

    public static void printNum() {
        int n = 0;
        System.out.print("满足条件的有：");
        for (int i = 1; i <= 500; i++) {
            if ((0 == i % 2) && (0 == i % 5) && (0 == i % 7)) {
                System.out.print(i + "  ");
                n++;
            }
        }
        System.out.println();
        System.out.println("满足条件的个数为：" + n);
    }

}
