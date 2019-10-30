package hutools.date;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.Quarter;
import org.junit.jupiter.api.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/18 21:41
 */
public class DateTest {
    @Test
    public void test() {
        DateTime time = new DateTime();
        Quarter quarter = time.quarterEnum();
    }
}
