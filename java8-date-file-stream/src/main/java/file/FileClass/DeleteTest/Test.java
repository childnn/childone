package file.FileClass.DeleteTest;

import java.io.File;

/**
 * 2019年2月23日19:02:49
 * 重点：1.删除文件只能从内往外删除，不能直接删除一个包含文件/子文件夹的文件夹（前提是定义的 File 对象真实存在）
 * 2.只能删除真实存在的文件/文件夹
 * 3.本删除方法不经过 回收站，直接删除，注意防止递归删除磁盘
 * 4.如果路径真实存在，则删除的是 File 所指向对象的末尾所示 文件/文件夹（根路径）
 * 5.如果想要删除一个大的文件夹，下面包含子文件夹/文件，只能使用递归，但是，一般禁止这么做！！！
 * 比如：定义一个 名为 “D:\\aaa\\bbb\\cc.txt” 的 File 对象，
 * 调用 delete() 方法，只会删除 cc.txt
 * 另外：无法删除不为空的文件夹
 * 比如，定义一个 File 对象 “D:\\aaa\\bbb”
 * 调用 delete() 方法，若 bbb 文件夹下 还有文件/文件夹，则删除失败，返回false
 * <p>
 * 在删除文件/空文件夹时，有两种情况会返回 false
 * 1.文件/文件夹不存在
 * 2.文件夹不为空（文件夹下还有子文件/文件夹）
 */
public class Test {
    public static void main(String[] args) {
        File file = new File("D:\\com\\com\\itheima");
        System.out.println(file.delete()); //false,因为 File 对象的绝对路径下 不为空，
        //若想看到下面的删除效果，可以先在该根目录下手动新建一个名为“123.java”的文件
        //在同一目录下，不容许存在这种情况：一个文件（包括扩展名）和文件夹的名字相同
        //比如 一个【文件】 名为“123”，扩展名为“.java”,
        // 此时，在该路径下不可能存在一个【文件夹】名为“123.java” (一般来说文件夹也不会这么命名)
        file = new File("D:\\com\\com\\itheima\\123.java");
        //若file所示路径的 末尾 表示的文件/文件夹真实存在，则删除成功并返回true
        //若file所示路径 末尾 表示的文件/文件夹不存在，则删除失败并返回 false
        System.out.println(file.delete());

    }
}
