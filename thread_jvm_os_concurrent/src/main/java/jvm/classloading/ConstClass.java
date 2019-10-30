package jvm.classloading;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/30 9:59
 */
public class ConstClass {
    static final String HELLO_WORLD = "hello world";

    static {
        System.out.println("ConstClass");
    }

}

// 非主动使用类字段
class NotInitialization1 {
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO_WORLD);
    }
}