package jvm.oom;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/20 10:08
 * 栈容量有 -Xss 参数设定.
 * 关于虚拟机栈和本地方法栈, 在 Java 虚拟机规范中描述了两种异常:
 * 如果线程请求的栈深度大于虚拟机所允许的最大深度, 将抛出 StackOverflowError 异常.
 * 如果虚拟机在扩展栈时无法申请到足够的内存空间, 则抛出 OutOfMemoryError 异常.
 */
public class JavaVMStackSOF {
    private int stackLength = 1;

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length: " + oom.stackLength);
            //e.printStackTrace();
        }
    }

    // VM Args: -Xss128k
    //期望结果: java.lang.StackOverflowError
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

}
