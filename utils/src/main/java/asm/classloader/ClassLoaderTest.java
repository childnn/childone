package asm.classloader;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/1 14:03
 * @see java.lang.ClassLoader#loadClass(String)
 * 该方法只会 "加载" 该类, 而不会执行 "初始化".
 * @see java.lang.Class#forName(String) 方法会强制初始化该类.
 * @see java.lang.ClassLoader#loadClass(String, boolean) ClassLoader 的入口, 根据指定名称来加载类,
 *      系统就是调用 ClassLoader 的该方法来获取指定类对应的 Class 对象.
 * @see java.lang.ClassLoader#findClass(String) 根据指定名称来查找类.
 * 如果需要实现自定义的 ClassLoader, 则可以通过重写以上两个方法来实现, 通常推荐重写 findClass() 方法, 而不是重写 loadClass() 方法.
 * loadClass() 方法指定步骤如下:
 * 1. 调用 {@link ClassLoader#findLoadedClass(java.lang.String)} 方法来检查是否已经加载该类, 如果已经加载则返回;
 * 2. 在父类加载器上调用 {@link java.lang.ClassLoader#loadClass(String, boolean)} 方法. 如果父类加载为 null, 则使用根类加载器来加载.
 * 3. 调用 {@link java.lang.ClassLoader#findClass(String)} 方法查找类.
 * 因此, 重写 findClass() 方法可以避免覆盖默认类加载器的父类委托, 缓冲机制两种策略; 如果重写 loadClass(), 则实现逻辑更为复杂.
 * ---
 * 在 ClassLoader 中有一个核心方法, {@link java.lang.ClassLoader#defineClass} 该方法负责指定类的字节码文件(.class 文件)
 * 读入字节数组内, 并把它转换成 Class 对象, 该字节码文件可以来源于文件、网络等.
 * @see java.lang.ClassLoader#defineClass 方法管理 JVM 的许多复杂的实现, 它负责将字节码分析成运行时数据结构, 并校验有效性等.
 * 该方法为 final 方法, 无需重写.
 * @see java.lang.ClassLoader#findSystemClass(String) final 方法, 从本地文件系统转入文件. 它在本地文件系统中寻找类文件,
 * 如果存在, 就使用 defineClass() 方法将原始字节转换成 Class 对象, 以将该文件转换成类.
 * @see ClassLoader#getSystemClassLoader() static-method, 返回系统类加载器
 * @see ClassLoader#getParent() 获取该类加载器的父类加载器
 * @see java.lang.ClassLoader#resolveClass(Class) final-method 链接指定的类. 类加载器可以使用此方法来链接指定类.
 * @see java.lang.ClassLoader#findLoadedClass(String) final-method 如果此 Java 虚拟机已加载了指定名称的类, 则直接返回该类对应的
 *      Class 实例, 否则返回 null. 该方法是 Java 类加载缓存机制的体现.
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException {
        final ClassLoader cl = ClassLoader.getSystemClassLoader();
        final Class<?> clazz = cl.loadClass("asm.classloader.Tester");

        System.out.println("=========加载 但未 初始化==========");

        // 强制初始化.
        Class.forName("asm.classloader.Tester");
    }

}

class Tester {

    static {
        System.out.println("Tester 的静态初始化块...");
    }

}