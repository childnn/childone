package file.FileClass.listAndlistFiles;

import java.io.File;
import java.io.FileFilter;

/**
 * shadow
 * 2019/3/14 18:36
 * File[] listFiles(FileFilter filter) 返回抽象路径名数组，这些路径名表示此抽象路径名表示的目录中满足指定过滤器的文件和目录。
 */
public class Demo04 {
    public static void main(String[] args) {
        getFile(new File("D:/"));
    }

    //本方法 假定 传入的 file 真实存在
    private static void getFile(File file) {
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) { //过滤条件: .exe 结尾, 或者是 文件夹
                return pathname.getName().toLowerCase().endsWith(".java") || pathname.isDirectory();
            }
        });
        if (files == null) { //如果 files 指向 null 说明 file 是文件,直接输出
            System.out.println(file);
        } else { //如果 files 不指向 null, 说明 file 是文件夹, 遍历 数组
            for (File f : files) {
                getFile(f);
            }
        }
    }
}
