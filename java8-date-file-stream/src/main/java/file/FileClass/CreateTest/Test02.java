package file.FileClass.CreateTest;

import java.io.File;

/**
 * 2019年2月22日16:15:21
 * 创建新【文件夹】：
 * boolean mkdirs():创建此抽象路径名指定的目录，包括所有【必需但不存在】的父目录。
 * //只能创建文件夹，即使写的是绝对路径包含文件,也会将文件 以文件夹名的方式创建
 * boolean mkdir() 创建由此抽象路径名命名的目录。
 * 只能创建单级文件夹
 */
public class Test02 {
    public static void main(String[] args) {
        //若不写盘符（绝对路径），默认在当前路径（即当前项目文件夹下）新建 名为“a.txt” 的文件夹
        //与各模块平级
        File file = new File("a.txt");
        //        boolean b1 = file.mkdir();
        //        System.out.println(b1);
        boolean b2 = file.mkdirs();
        System.out.println(b2);
        System.out.println(file.exists()); //true
        //        System.out.println(file.delete());
        System.out.println("==============");

        file = new File("bb\\a.txt");
        boolean mkdirs = file.mkdirs();
        System.out.println(mkdirs);
        System.out.println(file.delete());
        file = new File("bb");
        System.out.println(file.mkdirs()); //已经存在该文件夹，false
        System.out.println(file.delete()); //删除 该项目根目录下 名为 “bb” 的文件夹
        System.out.println("===========");

        File f = new File("D:\\aaa\\bb.java");
        System.out.println(f.mkdirs()); //在 D 盘下新建了两级文件夹，一级名为“aaa”，一级名为“bb.java”
        //把下面两行注释掉，会在D 盘下新建了两级文件夹，一级名为“aaa”，一级名为“bb.java”
        //若不注释，下面第一行会 删除 名为 “bb.java” 的子文件夹，
        //第二行会删除失败，输出 false
        System.out.println(f.delete()); //只能删除 【最内层的子文件夹】,存在则删除并返回true
        System.out.println(f.delete()); //不存在则返回 false

        f = new File("D:\\aaa");
        System.out.println(f.delete()); //删除父目录 “aaa”

       /* boolean delete = file.delete();
        System.out.println(delete);
        File f = new File("D:\\Firefox-latest.exe");
        System.out.println(f.exists()); //判断 file/directory 是否存在
        System.out.println(f.delete()); //当且仅当 exists() 返回true时，delete 放回true*/
    }
}
