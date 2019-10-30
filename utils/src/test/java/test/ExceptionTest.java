package test;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/12 8:55
 */
public class ExceptionTest {

    @Test
    public void test() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            // e.printStackTrace();
            System.out.println("异常...");
        }
        System.out.println("true = " + true); // 会执行.
    }

    @Test
    public void test1() {
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.MONTH, -1);
        // ca.get()
    }

}
