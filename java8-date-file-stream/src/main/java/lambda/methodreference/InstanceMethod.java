package lambda.methodreference;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/7 15:22
 * 引用实例方法:
 * 引用特定类对象的实例方法: 参数一为调用对象, 后续参数对应方法参数
 */
public class InstanceMethod {

    public static void main(String[] args) {
        // 引用实例方法
        Converter c = from -> "anonymous".indexOf(from); // lambda expression
        c = "anonymous"::indexOf; // method reference

        // 引用特定类对象的实例方法
        SubString s = (a, fromInclude, endExclude) -> a.substring(fromInclude, endExclude); // lambda expression
        s = String::substring; // method reference: 有点类似静态方法引用

    }
}
