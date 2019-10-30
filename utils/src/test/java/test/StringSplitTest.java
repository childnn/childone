package test;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/11 21:02
 */
public class StringSplitTest {
    @Test
    public void test() {
        String[] split = "1|2|3".split("\\|");
        System.out.println(Arrays.toString(split));
    }
}
