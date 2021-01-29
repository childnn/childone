package lambda.methodreference;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/7 15:22
 * 引用类方法: 静态方法
 */
public class StaticMethod {

    public static void main(String[] args) {

        Converter c = from -> Integer.valueOf(from); // lambda
        c = Integer::valueOf; // method reference

    }
}
