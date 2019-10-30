package jvm;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/13 16:20
 * 编译通过，运行异常
 * 运行时异常：代码不运行到该行就没有问题
 * 与运行时异常相对应的是连接时异常，例如 NoClassDefFoundError
 */
public class RuntimeCheck {
    // 运行时异常
    // java.lang.NegativeArraySizeException
    public static void main(String[] args) {
        int[][][] array = new int[1][0][-1];
    }
}
