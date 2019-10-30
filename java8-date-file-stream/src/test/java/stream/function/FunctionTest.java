package stream.function;

import org.junit.Test;

import java.util.function.Function;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/7 10:47
 * @see java.util.function.Function
 */
public class FunctionTest {

    @Test
    public void test() {
        Function f = new Function() {
            @Override
            public Object apply(Object o) {
                return null;
            }
        };
    }
}
