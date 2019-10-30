package jvm.polymorphic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/10 13:27
 * MethodHandle 与 Reflection：
 * 1. 从本质上讲，Reflection 和 MethodHandle 机制都是在模拟方法调用，但 Reflection 是
 * 在模拟 Java 代码层次的方法调用，而 MethodHandle 是在模拟字节码层次的方法调用。
 * 在 MethodHandles.lookup 中的 3 个方法 -- findStatic(), findVirtual(), findSpecial() 正是
 * 为了对应于 invokestatic, invokevirtual & invokeinterface 和 invokespecial 这几条字节码指令的
 * 执行权限校验行为，而这些底层细节在使用 Reflection API 时是不需要关心的。
 * 2. Reflection 中的 java.lang.reflect.Method 对象远比 MethodHandle 机制中的 java.lang.invoke.MethodHandle
 * 对象所包含的信息多。前者是方法在 Java 一端的全面映像，包含了方法的签名，描述符以及方法属性表
 * 中各种属性的 Java 端表示方式，还包含执行权限等的运行期信息。而后者仅包含与执行该方法相关的信息。
 * Reflection 是重量级的，MethodHandle 是轻量级的。
 * 3. 由于 MethodHandle 是对字节码的方法指令调用的模拟，所以理论上虚拟机在这方法面做的各种优化，
 * 在 MethodHandle 上也应当可以采用类似思路去支持。而通过反射去调用方法则不行。
 * 另外，Reflection 仅站在 Java 语言的角度来看，而 MethodHandle 则设计成可服务于所有 Java 虚拟机之上
 * 的语言，其中也包括 Java 本身。
 */
public class MethodHandleTest {
    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();

        // 无论 obj 最终 是哪个实现类，下面这句都能正确调用 pringtln 方法
        getPrintlnMH(obj).invokeExact("icyfenix");
    }

    /**
     * 此方法模拟了 invokevirtual 指令的执行过程，只不过它的分派逻辑并非固化在 Class
     * 文件的字节码上，而是通过一个具体方法来实现。而这个方法本身的返回值 MethodHandle 对象，可以视为
     * 对最终调用方法的一个 “引用”。
     */
    static MethodHandle getPrintlnMH(Object receiver) throws NoSuchMethodException, IllegalAccessException {
        // MethodType: 代表 “方法类型”，包含了方法的返回值( methodType() 的第一个参数)
        // 和 具体参数 ( methodType() 第二个及以后的参数)
        MethodType mt = MethodType.methodType(void.class, String.class);
        // 在指定类中查找符合给定的方法名称, 方法类型, 并且符合调用权限的方法句柄.
        // 因为这里调用的是一个虚方法, 按照 Java 语言的规则, 方法第一个参数是隐式的, 代表该方法的接收者
        // 也即 this 指向的对象, 这个参数以前是放在参数列表中进行传递的,而现在提供了 bindTo() 方法来完成这件事情.
        return MethodHandles.lookup()
                .findVirtual(receiver.getClass(), "println", mt)
                .bindTo(receiver);
    }

    static class ClassA {
        void println(String s) {
            System.out.println(s);
        }
    }

}
