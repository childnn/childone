package org.anonymous.classloader;

import sun.misc.Launcher;
import sun.misc.URLClassPath;

import java.net.URL;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/1 19:08
 * Bootstrap Classloader, 不是 {@link java.lang.ClassLoader} 的子类, 而是由 JVM 自身实现.
 */
public class BootstrapTest {

    // 获取 根类加载器所加载的核心类库.
    public static void main(String[] args) {
        final URLClassPath urlClassPath = Launcher.getBootstrapClassPath();
        final URL[] urLs = urlClassPath.getURLs();
        for (URL urL : urLs) {
            System.out.println(urL.toExternalForm());
        }
    }

}
