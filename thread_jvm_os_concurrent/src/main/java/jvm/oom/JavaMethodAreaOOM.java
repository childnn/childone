package jvm.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/20 10:47
 * 使用 CGLIB 这类字节码技术对类进行增强时, 增强的类越多, 就需要越大的方法区
 * 来保证动态生成的 Class 可以加载入内存. JVM 上的动态语言(如 Groovy 等)通常都会
 * 持续创建类来实现语言的动态性, 随着这类语言的流行, 也越来越容易遇到移除场景.
 * ---
 * 方法区溢出也是一种常见的内存溢出异常, 一个类要被垃圾收集器回收, 判定条件是比较苛刻的.
 * 在经常动态生成大量 Class 的应用中, 需要特别注意类的回收状况.
 * 这类场景除了上面提到的程序使用了 CGLIB 字节码增强和动态语言之外, 常见的还有:
 * 大量 JSP 或动态 JSP 文件的应用(JSP 第一次运行时需要编译为 Java 类), 基于 OSGi 的应用
 * (即使是同一个类文件, 被不同的加载器加载也会视为不同的类)等.
 */
public class JavaMethodAreaOOM {
    // VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
    // 借助 CGLIB 使方法区出现内存溢出异常
    // 期望结果: java.lang.OutOfMemoryError: PermGen space
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) ->
                    proxy.invokeSuper(obj, args1));
        }
    }

    private static class OOMObject {
    }
}
