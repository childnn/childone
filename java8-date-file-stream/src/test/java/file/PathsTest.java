package file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/4 13:28
 */
public class PathsTest {
    @Test
    public void test() {
        // 注: / 开头表示绝对路径, 否则为相对路径. 注意二者区别.
        Path path = Paths.get("/user/child/hello.txt");
        System.out.println("path = " + path);
        Path path1 = Paths.get("user", "child", "world.txt");
        System.out.println("path1 = " + path1);

        System.out.println("=====================================");

        System.out.println(path.getClass());

        // 文件名.
        Path fileName = path.getFileName();
        System.out.println("fileName = " + fileName);
        // 父路径.
        Path parent = path.getParent();
        System.out.println("parent = " + parent);
        // 文件层级数量.
        int nameCount = path.getNameCount();
        System.out.println("nameCount = " + nameCount);
        // 获取指定层级文件(夹)名.
        Path name = path.getName(0);
        System.out.println("name = " + name);
        Path name1 = path.getName(nameCount - 1);
        System.out.println("name1 = " + name1);
        // 获取同级目录其他文件(夹).
        Path aaa = path.resolveSibling("aaa");
        System.out.println("aaa = " + aaa);
        Path sibling = path.resolveSibling("zzz/bbb.txt");
        System.out.println("sibling = " + sibling);
        // 获取子级目录其他文件(夹).
        Path xxx = path.resolve("xxx");
        System.out.println("xxx = " + xxx);
        // 获取绝对路径.
        Path absolutePath = path.toAbsolutePath();
        System.out.println("absolutePath = " + absolutePath);
        // 转文件.
        File file = path.toFile();
        System.out.println("file = " + file);
        URI uri = path.toUri();
        System.out.println("uri = " + uri);
    }

    // 批量重命名文件
    @Test
    public void renameFiles() throws IOException {
        Files.list(Paths.get("E:\\三国演义(中国评书网www.zgpingshu.com)"))
                .forEach(p -> {
                    //System.out.println(p.getParent());
                    /*这里加上可以绝对路径(父目录), 否则重命名后的文件在当前项目目录下*/
                    p.toFile().renameTo(new File(p.getParent() + "\\" + p.getFileName().toString().replace("xxx", "")));
                });
    }

    @Test
    public void f() {
    }

}
