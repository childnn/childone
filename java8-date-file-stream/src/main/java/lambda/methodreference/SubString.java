package lambda.methodreference;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/7 15:26
 */
@FunctionalInterface
public interface SubString {

    String sub(String a, int fromInclude, int endExclude);

}
