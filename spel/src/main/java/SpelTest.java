import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/30 11:35
 */
public class SpelTest {

    @Test
    public void test() {
        ExpressionParser pa = new SpelExpressionParser();
        Expression ex = pa.parseExpression("'${test:123}'.length()");
//        Integer value = ex.getValue(Integer.TYPE);
//        System.out.println("value = " + value);
        Class<?> valueType = ex.getValueType();
        System.out.println("valueType = " + valueType);
//        Object value = ex.getValue();
//        System.out.println("value = " + value);
    }
}
