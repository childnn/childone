package org.anonymous.netty.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/22 16:26
 */
public class FilesTest {

    public static void main(String[] args) throws IOException {
        // 返回值实际就是文件大小: 字节数.
        final Path path = Paths.get("netty/src/main/java/org/anonymous/java7nio/file/FilesTest.java");

        final FileOutputStream fos = new FileOutputStream("./netty/src/main/resources/FilesTest.txt");
        final long copy = Files.copy(
                path,
                fos); // 这里也可以使用 path
        System.out.println("copy = " + copy);
        System.out.println("Files.size(path) = " + Files.size(path));

        System.out.println("===============================================");
        System.out.println("Files.isHidden(path) = " + Files.isHidden(path));
        System.out.println("Files.isDirectory(path) = " + Files.isDirectory(path));
        System.out.println("Files.isRegularFile(path) = " + Files.isRegularFile(path));
        System.out.println("Files.isReadable(path) = " + Files.isReadable(path));
        System.out.println("Files.isSameFile(path, path) = " + Files.isSameFile(path, path));

        System.out.println("=================================================");

        final List<String> lines = Files.readAllLines(path);
        lines.forEach(System.out::println);

        // write
        Path wp = new File("./netty/src/main/resources/FilesTest1.txt").toPath();
        // 第一次执行时不存在该文件, 必须先创建,
        if (!Files.exists(wp)) {
            wp = Files.createFile(wp); // 这里可以不用接收返回值: 返回值就是该 path 对象
        }
        Files.write(wp,
                lines,
                APPEND); // 追加

    }

}
