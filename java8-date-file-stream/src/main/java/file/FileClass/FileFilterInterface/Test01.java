package file.FileClass.FileFilterInterface;

import java.io.File;

/**
 * 2019年2月22日18:15:43
 * 遍历文件夹中的指定格式文件
 */
public class Test01 {
    public static void main(String[] args) {
        File file = new File("D:\\新建文件夹\\AA");
        fileRecursion(file);
    }

    private static void fileRecursion(File file) {
        if (!file.exists()) { //如果文件不存在
            System.out.println("文件/文件夹不存在");
        } else { //如果文件存在
            File[] files = file.listFiles(pathname -> {
                if (pathname.isDirectory()) {
                    return true;
                }
                return pathname.toString().toLowerCase().endsWith(".avi");
            });
            if (files == null) { //如果数组为 null 说明 pathname 以文件结尾，直接输出
                System.out.println(file);
            } else {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) { //如果是文件，输出
                        System.out.println(files[i]);
                    } else {
                        fileRecursion(files[i]);
                    }
                }
            }
            /*if (file.isFile()) { //如果是文件，直接输出
                System.out.println(file);
            } else { //如果是文件夹
                File[] files = file.listFiles( pathname -> {
                    if (pathname.isDirectory()) { //如果是文件夹，true，把 文件夹 传入 等号左边的集合
                        return true;
                    } //return语句：else 可以省略
                    return pathname.getName().toLowerCase().endsWith(".exe");
                } );
                //遍历集合
                for (File f : files) {
                    fileRecursion(f);
                }
            }*/
        }
    }
}
