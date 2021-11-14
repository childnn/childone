package asm.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/1 19:21
 * @see sun.misc.Launcher
 */
public class ClassLoaderPropTest {

    public static void main(String[] args) throws IOException {
        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("systemClassLoader = " + systemClassLoader); // app-class loader

        // 获取系统类加载器的加载路径--通常由 CLASSPATH 环境变量指定
        // 如果操作系统没有指定 CLASSPATH 环境变量, 则默认以当前路径作为
        // 系统类加载器的加载路径
        final Enumeration<URL> em = systemClassLoader.getResources("");
        while (em.hasMoreElements()) {
            System.out.println("em.nextElement() = " + em.nextElement());
        }

        // 获取系统类加载器的父类加载器, 得到扩展类加载器
        final ClassLoader extensionLoader = systemClassLoader.getParent();
        System.out.println("extensionLoader = " + extensionLoader); // ext-class loader
        System.out.println("System.getProperty(\"java.ext.dirs\") = " + System.getProperty("java.ext.dirs"));
        // 扩展类加载器的父加载器为 null, 并不是跟类加载器
        // 这是因为根类加载器并没有继承 ClassLoader 抽象类
        // 实际上扩展类加载器的父类加载器是 根类加载器, 只是根类加载器不是由 Java 实现.
        // 程序通常无需访问根类加载器
        System.out.println("extensionLoader.getParent() = " + extensionLoader.getParent());
    }

}
