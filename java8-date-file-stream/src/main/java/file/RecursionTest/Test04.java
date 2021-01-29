package file.RecursionTest;

import java.io.File;

public class Test04 {
    public static void main(String[] args) {
        File file = new File("D:\\software");
        fileRecursion(file);
        //        f(file);
    }

    private static void fileRecursion(File file) {
        if (!file.exists()) { //如果文件/文件夹不存在
            System.out.println("文件/文件夹不存在！");
        } else { //如果文件/文件夹存在
            if (file.isFile()) { //如果是文件直接输出
                System.out.println(file);
            } else { //如果是文件夹，遍历
                File[] files = file.listFiles();
                for (File f : files) { //为什么会有空指针的出现
                    fileRecursion(f);
                }
            }
        }
    }

    private static void f(File file) {
        File[] files = file.listFiles();
        if (files == null) { //传入的实参file 是文件，则 files == null
            System.out.println(file);
        } else { //如果传入的实参是文件夹,遍历集合
            for (File fi : files) {
                f(fi);
            }
        }
    }
}
