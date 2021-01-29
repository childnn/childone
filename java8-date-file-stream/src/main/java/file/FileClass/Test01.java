package file.FileClass;

import java.io.File;

/**
 * 2019年2月22日11:07:59
 * boolean exists()：测试此抽象路径名表示的文件或目录是否存在。
 * 互斥的方法：一个路径要么以文件结尾，要么以文件夹结尾
 * boolean isDirectory()：测试此抽象路径名表示的文件是否是一个目录。 //是否是文件夹结尾
 * boolean isFile()：测试此抽象路径名表示的文件是否是一个标准文件。 //是否是文件结尾
 * <p>
 * boolean isHidden():测试此抽象路径名指定的文件是否是一个隐藏文件。
 */
public class Test01 {
    public static void main(String[] args) {
        File f1 = new File("D:/abcd");
        System.out.println(f1.exists()); //不存在，false
        f1 = new File("D:/abc.txt");
        System.out.println(f1.exists()); //存在，true

        System.out.println(f1.isDirectory()); //false
        System.out.println(f1.isFile()); //true
    }
}
