package org.anonymous.netty.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.nio.file.FileVisitor
 * java7 以前, 如果需要遍历指定目录下的所有文件和子目录, 只能递归访问.
 * FileVisitor 提供了更优雅的方式遍历文件和子目录
 * @see java.nio.file.Files#walkFileTree(java.nio.file.Path, java.nio.file.FileVisitor) 遍历 start 参数表示的路径下的所有文件和子目录
 * @see java.nio.file.Files#walkFileTree(java.nio.file.Path, java.util.Set, int, java.nio.file.FileVisitor) 相比上个方法, 多了遍历深度
 * walkFileTree 方法会自动遍历 start 路径下的所有文件和子目录, 遍历文件和子目录都会触发 FileVisitor 中相应的方法.
 * @see java.nio.file.FileVisitor#preVisitDirectory 访问子目录之前触发
 * @see java.nio.file.FileVisitor#postVisitDirectory 访问子目录之后触发
 * @see java.nio.file.FileVisitor#visitFile 访问 file 文件时触发
 * @see java.nio.file.FileVisitor#visitFileFailed 访问 file 文件失败时触发该方法
 * --
 * @see java.nio.file.FileVisitResult#CONTINUE 继续访问
 * @see java.nio.file.FileVisitResult#SKIP_SIBLINGS 继续访问, 忽略该文件/目录的兄弟文件/目录
 * @see java.nio.file.FileVisitResult#SKIP_SUBTREE 继续访问, 忽略该文件/目录的子目录树
 * @see java.nio.file.FileVisitResult#TERMINATE 中止访问
 * 在实际开发中, 可实现 {@link java.nio.file.SimpleFileVisitor}, 相当于一个 adaptor, 根据需要实现相应的方法
 * @since 2021/1/22 18:27
 */
public class FileVisitorTest {

    public static void main(String[] args) throws IOException {
        Files.walkFileTree(Paths.get("netty/src/main/java/org/anonymous"), new SimpleFileVisitor<Path>() {

            // 访问文件时触发该方法.
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("正在访问 [文件]: " + file);
                if (file.endsWith("FileVisitorTest.java")) {
                    System.out.println("目标文件: " + file.getFileName());
                    // 终止
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }

            // 开始访问目录时触发该方法
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("正在访问 [目录]: " + dir);

                return FileVisitResult.CONTINUE;
            }
        });
    }

}
