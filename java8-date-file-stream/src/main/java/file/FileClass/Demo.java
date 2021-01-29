package file.FileClass;

import java.io.File;

/**
 * shadow
 * 2019/3/14 13:06
 */
public class Demo {
    public static void main(String[] args) {
        File f = new File("Day08FileAndRecursion\\src\\com\\itheima\\FileClass\\Demo.java");
        System.out.println("file name:" + f.getName()); //file name:Demo.java
        System.out.println("path:" + f.getPath()); //path:Day08FileAndRecursion\src\com\itheima\FileClass\Demo.java
        System.out.println("abs path:" + f.getAbsolutePath()); //abs path:D:\Develope\J2EE\Day08FileAndRecursion\src\com\itheima\FileClass\Demo.java
        System.out.println("parent:" + f.getParent()); //parent:Day08FileAndRecursion\src\com\itheima\FileClass
        System.out.println(f.exists() ? "exists" : "does not exists");
        System.out.println(f.canRead() ? "is readable" : "is not readable");
        System.out.println(f.canWrite() ? "is writeable" : "is not writeable");
        System.out.println("is " + (f.isDirectory() ? "" : "not") + "directory");
        System.out.println("is " + (f.isAbsolute() ? "" : "not") + "absolute");
        System.out.println("file size:" + f.length() + " bytes");
    }
}
