package file.RecursionTest;

/**
 * 2019年2月22日15:08:53
 * 斐波那契数列:
 * 1,1,2,3,5,8,13....
 */
public class Test02 {
    public static void main(String[] args) {
        System.out.println(f(20));
    }

    //求斐波那契数列中的第 i 个数字
    private static int f(int i) {
        //第 1/2 个数为 1
        if (i == 1 || i == 2) {
            return 1;
        }
        //第 i 个数 = 第（i-1）个数 + 第（i-2）个数
        return f(i - 1) + f(i - 2);
    }
}
