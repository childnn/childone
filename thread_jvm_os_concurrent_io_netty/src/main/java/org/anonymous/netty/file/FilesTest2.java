package org.anonymous.netty.file;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/22 18:17
 */
public class FilesTest2 {

    public static void main(String[] args) throws IOException {
        // Java8-stream-files: 列出当前目录下所有文件和子目录: 非递归
        final Stream<Path> paths = Files.list(Paths.get("."));
        paths.forEach(System.out::println);

        final Stream<String> lines = Files.lines(Paths.get("netty/src/main/java/org/anonymous/java7nio/file/FilesTest2.java"));
        lines.forEach(System.out::println);

        final FileStore fs = Files.getFileStore(Paths.get("C:"));
        System.out.println("fs.getTotalSpace() = " + fs.getTotalSpace()); // bytes
        System.out.println("fs.getUsableSpace() = " + fs.getUsableSpace());
    }

}
