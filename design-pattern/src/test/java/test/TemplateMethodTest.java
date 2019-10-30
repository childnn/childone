package test;

import org.anonymous.template.method.Tea;
import org.junit.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/8 11:47
 */
public class TemplateMethodTest {
    @Test
    public void test1() {
        Tea tea = new Tea();
        tea.prepareRecipe();
    }
}
