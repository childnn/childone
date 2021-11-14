package asm.classloader;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/1 13:40
 */
public class CompileConstantTest {

    public static void main(String[] args) {
        // 访问, 输出 ATest 中的 COMPILE_CONSTANT
        // 可以发现, ATest 的静态代码块没有执行, 即 ATest 并没有被初始化.
        // System.out.println(ATest.COMPILE_CONSTANT);

        // COMPILE_CONSTANT_TIME 必须到运行时才能确定其值, 因此不属于宏变量.
        // 访问该变量会导致该类被初始化.
        System.out.println(ATest.COMPILE_CONSTANT_TIME);
    }

}

class ATest {
    static {
        System.out.println("静态代码块...");
    }

    static final String COMPILE_CONSTANT = "宏变量";
    static final String COMPILE_CONSTANT_TIME = System.currentTimeMillis() + "";
}