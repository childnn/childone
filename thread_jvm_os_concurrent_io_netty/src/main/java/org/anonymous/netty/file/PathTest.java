package org.anonymous.netty.file;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.nio.file.Path 代表平带无关的文件路径. 实际上就是文件. 作为 {@link java.io.File} 的替代.
 * @see java.nio.file.Paths 工具类, 获取 Path
 * 一个 Path 对象表示的路径/文件不一定真实存在, 可以通过 Files 对 Path 执行一系列操作
 * @see java.nio.file.Files 高度封装的文件操作工具类
 * @since 2021/1/22 9:37
 */
public class PathTest {

    public static void main(String[] args) {
        final Path path = Paths.get("netty/src/main/java/org/anonymous/java7nio/file/PathTest.java");
        final int nameCount = path.getNameCount();
        System.out.println("Path 里包含的路径数量: " + nameCount);
        path.forEach(System.out::println);

        System.out.println("Path 根路径: " + path.getRoot()); // 没有根路径就是 null
        final Path absolutePath = path.toAbsolutePath();
        System.out.println("absolutePath = " + absolutePath);
        System.out.println("absolutePath.getRoot() = " + absolutePath.getRoot());

        System.out.println("user.dir = " + System.getProperty("user.dir"));

        System.out.println("===============================");

        final Path p = Paths.get("g:", "publish", "codes");
        System.out.println("p = " + p);
    }

}
