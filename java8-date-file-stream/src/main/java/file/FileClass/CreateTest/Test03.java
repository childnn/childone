package file.FileClass.CreateTest;

import java.io.File;

/**
 * 2019年2月23日21:18:23
 * boolean mkdir() 创建由此抽象路径名命名的目录。
 * 与 mkdirs()  的区别：能且只能创建一级 文件夹
 * 返回true，创建成功的两种情况：
 * 1.在File对象表示单级文件夹时，该文件夹必须不存在
 * 2.在File对象表示多级文件夹时，该多级文件夹末尾文件夹的所有父目录文件夹必须全部已经存在
 * 返回 false 的两种情况：
 * 1.在File对象包含多级文件夹时，父目录不存在
 * 2.在File对象只表示单级文件夹时，如果该文件夹已经存在，则返回 false
 */
public class Test03 {
    public static void main(String[] args) {
        File file = new File("java.java");
        System.out.println(file.mkdir()); //
        //        System.out.println(file.mkdirs());
        System.out.println(file.delete());
        file = new File("java.java\\java");
        System.out.println(file.mkdir());


    }
}
