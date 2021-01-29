package org.anonymous.se;

/**
 * @author child
 * 2019/7/5 12:08
 * @see jdk.internal.loader.ClassLoaders
 * @see jdk.internal.loader.ClassLoaders.AppClassLoader
 * @see jdk.internal.loader.ClassLoaders.PlatformClassLoader
 * -- 1.8
 * @see sun.misc.Launcher.AppClassLoader
 * @see sun.misc.Launcher.ExtClassLoader
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        // jdk.internal.loader.ClassLoaders$AppClassLoader@3fee733d
        // jdk.internal.loader.ClassLoaders$PlatformClassLoader@5f184fc6
        while (null != classLoader) {
            System.out.println("classLoader = " + classLoader);
            classLoader = classLoader.getParent();
        }
    }
}
