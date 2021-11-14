package test;

import org.junit.Test;

/**
 * @author child
 * 2019/7/15 9:15
 * 素数: 质数 只能被 1 和 自身整除的数
 */
public class PrimeNumber {

    @Test
    public void test() {
        int a = 91;
        String msg = "是素数";
        for (int i = 2; i < a; i++) { // 从 2 开始
            if (a % i == 0) {
                msg = "非素数";
                break;
            }
        }
        System.out.println(a + msg);
    }

}
