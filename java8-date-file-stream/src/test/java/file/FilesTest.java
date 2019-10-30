package file;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/4 13:57
 */
public class FilesTest {
    @Test
    public void test() throws IOException {
        Path path = Paths.get("user/test/aaa");

        // 校验文件/夹是否存在.
        boolean exists = Files.exists(path);
        System.out.println("exists = " + exists);
        boolean b = Files.notExists(path);
        System.out.println("b = " + b);
        // 是否是文件夹.
        boolean directory = Files.isDirectory(path);
        System.out.println("directory = " + directory);
        // 是否是文件.
        boolean regularFile = Files.isRegularFile(path.resolve("zzz.txt"));
        System.out.println("regularFile = " + regularFile);
        // 创建文件.
//        Files.createFile(path.resolve(...))
        // 创建文件夹. -- 特定情况异常.
//        Files.createDirectory(path.resolve(aa/bb)) // 可以创建多级.
        // 多级文件夹. -- 特定情况无异常.
//        Files.createDirectories()
        // 删除.
//        Files.delete(path);
//        Files.deleteIfExists(path);
        // 复制.
//        Files.copy(path.resolve(""), path.resolve(path.getFileName()));
        // 移动.
//        Files.move(path, path, StandardCopyOption.REPLACE_EXISTING);

        // 读.
        Path path1 = Paths.get("src/test/java/file/FilesTest.java");
        List<String> strings = Files.readAllLines(path1/*, Charset.defaultCharset()*/);
//        strings.forEach(System.err::println);
        byte[] bytes = Files.readAllBytes(path1);
        // 写.
//        Path write = Files.write(path1, Arrays.asList("123", "456")); // 覆盖.
//        System.out.println("write = " + write);
//        Files.write(path1, Arrays.asList("aaa", "cccc"), WRITE, APPEND);
//        Files.write(path1, new byte[]{});
        // 写.
//        Files.newBufferedWriter(path1).write("");

        // stream
        InputStream is = Files.newInputStream(path1);
//        OutputStream os = Files.newOutputStream(path1); // 覆盖.

        // 文件列表.
        Stream<String> lines = Files.lines(path1);
        lines.forEach(System.err::println);

        BasicFileAttributes attributes = Files.readAttributes(path1, BasicFileAttributes.class);
        // 查找.
//        Files.find(path1, 3, (path2, basicFileAttributes) -> false, );
    }

    /**
     * 文件(夹)列表
     *
     * @param path 路径
     */
    public List<File> listDirs(String path) throws IOException {
        Path dirPath = Paths.get(path);
        if (Files.isDirectory(dirPath)) {
            Comparator<File/*BaseFileBean*/> comparing = (o1, o2) -> Collator.getInstance(Locale.CHINESE).compare(o1.getName(), o2.getName());
            return Files.list(Paths.get(path)).map(Path::toFile).sorted(comparing).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 查找文件(夹)
     */
    public List<File> findFiles(String folder, String keyword) throws IOException {
        final int MAX_DEPTH = 5;
        File dir = new File(folder);
        if (dir.exists() && dir.isDirectory()) {
            final String lowerKeyword = keyword.toLowerCase();
            Comparator<File> comparing = (o1, o2) -> Collator.getInstance(Locale.CHINESE).compare(o1.getName(), o2.getName());
            Stream<Path> stream = Files.find(Paths.get(folder), MAX_DEPTH, (path, basicFileAttributes) -> path.getFileName().toString().toLowerCase().contains(lowerKeyword));
            return stream.map(Path::toFile).sorted(comparing).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
