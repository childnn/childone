package jvm.polymorphic;

import java.lang.invoke.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/10 13:47
 * 每一处含有 invokedynamic 指令的位置都称作 “动态调用点” Dynamic Call Site.
 * 这条指令的第一个参数不再是代表方法符号引用的 CONSTANT_Methodref_info 常量，
 * 而是变为 JDK 1.7 新加入的 CONSTANT_InvokeDynamic_info 常量。从这个常量中可以得到
 * 3 项信息：引导方法（Bootstrap Method, 此方法存放在新增的 BootstrapMethods 属性中），
 * 方法类型（MethodType）和名称。引导方法是有固定的参数，并且返回值是 java.lang.invoke.CallSite 对象,
 * 这个代表真正要执行的目标方法调用. 根据 CONSTANT_InvokeDynamic_info 常量中提供的信息,
 * 虚拟机可以找到并且执行引导方法, 从而获得一个 CallSite 对象,最终调用要执行的目标方法.
 */
public class InvokeDynamicTest {
    public static void main(String[] args) throws Throwable {
        INDY_BootstrapMethod().invokeExact("icyfenix");
    }

    static void testMethod(String s) {
        System.out.println("hello string: " + s);
    }

    static CallSite BootstrapMethod(MethodHandles.Lookup lookup, String name, MethodType mt) throws NoSuchMethodException, IllegalAccessException {
        return new ConstantCallSite(lookup.findStatic(InvokeDynamicTest.class, name, mt));
    }

    /**
     * 关于方法描述符的介绍,详见 src/main/resources/jvm.txt
     *
     * @return
     * @see #BootstrapMethod(MethodHandles.Lookup, String, MethodType) 的 MethodType
     */
    static MethodType MT_BootStrapMethod() {
        // 方法描述符:  CallSite methodName(Lookup, String, MethodType)
        return MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;" +
                "Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;", null);
    }

    static MethodHandle MH_BootstrapMethod() throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.lookup().findStatic(InvokeDynamicTest.class, "BootstrapMethod", MT_BootStrapMethod());
    }

    static MethodHandle INDY_BootstrapMethod() throws Throwable {
        return ((CallSite) MH_BootstrapMethod().invokeWithArguments(MethodHandles.lookup(), "testMethod",
                MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V", null)))
                .dynamicInvoker();
    }

}
