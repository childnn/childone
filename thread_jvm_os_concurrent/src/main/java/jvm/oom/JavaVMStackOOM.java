package jvm.oom;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/20 10:14
 * 在 windows 平台的虚拟机中, Java 线程是映射到操作系统的内核线程上的,
 * 因此本代码执行时有较大风险, 可能会导致操作系统假死.
 * 期望结果:
 * java.lang.OutOfMemoryError: unable to create new native thread
 */
public class JavaVMStackOOM {
    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }

    private void dontStop() {
        while (true) {

        }
    }

    public void stackLeakByThread() {
        while (true) {
            Thread t = new Thread(this::dontStop);
            t.start();
        }
    }
}
