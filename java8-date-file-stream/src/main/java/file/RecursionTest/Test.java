package file.RecursionTest;

/**
 * 2019年2月22日13:54:51
 * 递归引入：
 * 阶乘
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(jiecheng(5));
        System.out.println(jiechenghe(5));
    }

    private static int jiecheng(int i) {
        if (i == 1)
            return 1;
        return i * jiecheng(i - 1);
    }

    private static int jiechenghe(int i) {
        if (i == 1)
            return 1;
        //如果 n!=1， n 的阶乘到 1 的阶乘和 == n! + (n-1)! 到 1! 和
        return jiecheng(i) + jiechenghe(i - 1);
    }
}
