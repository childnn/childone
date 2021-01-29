package file.FileClass.FilenameFilterInterface;

import java.io.File;

public class Test {
    public static void main(String[] args) {
        File file = new File("D:\\");
        fileRecursion(file);
    }

    private static void fileRecursion(File file) {
        /*File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory() || name.toLowerCase().endsWith(".avi");
            }
        });*/
        File[] files = file.listFiles((dir, name) -> new File(dir, name).isDirectory() || name.toLowerCase().endsWith(".java"));

        if (files == null) { //如果数组指向 null，表明 file 以文件结尾，直接输出 file 对象
            System.out.println(file);
        } else { //如果数组引用不为null表明 file 是以文件夹结尾
            for (File f : files) {
                if (f.isFile()) { //如果数组中元素是文件，直接输出
                    System.out.println(f);
                } else { //如果是文件夹，继续遍历
                    fileRecursion(f);
                }
            }
        }
    }
}
