package file.FileClass.listAndlistFiles;

import org.junit.Test;

import java.io.File;

/**
 * shadow
 * 2019/3/14 14:34
 * 全类名: 包名+类名. src 目录下的 .java 文件 的父目录 都属于全类名的一部分
 * File[] listFiles() 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
 * 对于 File 类 的 listFiles() 方法:
 * 如果 调用 该方法的 File 对象,表示的是 文件,那么返回的 File[] 数组 将指向 null
 * 如果 调用 该方法的 File 对象,表示的 文件/文件夹 实际不存在,那么 返回的 File[] 数组 也将指向 null
 * 如果 调用 该方法的 File 对象,表示的是 文件夹 且 该文件夹实际存在, 那么返回 File[] 数组,
 * 数组中的元素 是该 File 对象所指向的 文件夹 根目录 下 的所有文件/文件夹 的 File 对象
 * 如果该实际存在的文件夹为空,则数组也为空(不是 null,只是什么也没有的空数组)
 */
public class Demo03 {
    public static void main(String[] args) {
     /*   File f = new File("Fei");
        System.out.println(f);
        System.out.println(f.getPath());
        System.out.println(f.getAbsolutePath()); //相对路径默认是 当前项目文件夹的目录,即使文件实际不存在,也会返回绝对路径
        File[] files = f.listFiles(); //如果 f 表示的是文件, 当其调用 listFiles() 方法时,返回 null
        System.out.println(files); //null*/
        //在全盘递归过程中: 建议先直接 转数组,再判断数组是否指向 null
        getFile(new File("D:\\")); //OK
        getFile2(new File("D:\\")); //NPE
    }

    //递归方式一: 直接把传入的实参转数组,不判断其是否存在
    //通过后面对 返回数组值的判断,间接判断 File 对象表示 文件/文件夹 是否存在
    private static void getFile(File file) {
        File[] files = file.listFiles(f -> f.isDirectory() || f.getName().endsWith(".java"));
        if (files == null) { //files == null, 说明 file 是文件/或 file 所指向的文件
            System.out.println(file);
        } else { //files != null, 说明 file 是文件夹
            //遍历文件夹, 直接递归
            for (File f : files) {
                getFile(f);
            }
        }
    }

    //方法二: 先判断传入的 File 对象(文件/文件夹)是否存在, 在进行其他操作
    //本方法更复杂: 本方法在全盘遍历的时候 会报 NPE ...
    private static void getFile2(File file) {
        if (!file.exists()) { //如果传入的文件/文件夹不存在
            System.out.println("文件/文件夹错误!");
        } else { //如果文件/文件夹存在,判断是文件还是文件夹
            if (file.isFile()) { //如果是文件,直接输出文件名
                System.out.println(file);
            } else { //如果是文件夹,调用 listFiles() 方法 获取 file 文件夹下的 File 对象 组成的数组
                //判断文件夹是否为空(文件夹下什么也没有)
                if (!"{}".equals(file.toString())) {
                    File[] files = file.listFiles();
                    //遍历数组
                    for (File f : files) { //NPE
                        getFile2(f);
                    }
                } else {
                    System.out.println(file);
                }
            }
        }
    }

    @Test
    public void test() {
        //当前目录: 是指 全类名的当前目录,而不是 .java 文件的目录,即 从 src 目录开始计算
        //        getFile(new File("./"));
        //        getFile2(new File("D:\\"));
    }
}
