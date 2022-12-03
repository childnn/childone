package regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/12/16
 */
public class RegexTest {

    @Test
    public void test() {
        Pattern pp = Pattern.compile("a*", CASE_INSENSITIVE);
        System.out.println(pp.matcher("AAAA").matches());
    }

}
