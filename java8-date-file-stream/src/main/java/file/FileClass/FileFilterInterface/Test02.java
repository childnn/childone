package file.FileClass.FileFilterInterface;

import java.io.File;

/**
 * 2019年2月22日20:19:50
 * 遍历目录中指定格式的文件
 */
public class Test02 {
    public static void main(String[] args) {
        File file = new File("D:\\");
        fileRecursion(file);
    }

    private static void fileRecursion(File file) {
        //如果file是文件，直接输出
        if (file.isFile()) {
            System.out.println(file);
        } else { //如果file是文件夹，进入遍历流程
            File[] files = file.listFiles(pathname -> {
                // lisFiles 方法会遍历 file 目录，如果 file 目录下的是文件夹，返回true，存入数组，等待接下来的遍历
                // 如果 listFiles 方法遍历得到的是 file 目录下的文件，且以 ".exe" 结尾，返回 true，存入数组
                return pathname.isDirectory() || pathname.getName().toLowerCase().endsWith(".exe");
            });
            //            System.out.println(Arrays.toString(files));
            if (null == files /*"[]".equals(Arrays.toString(files))*/) { //为什么会有数组的引用指向 null ？？
                System.out.println(files);
            } else {
                //遍历数组
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) { //如果数组中元素是文件,直接输出
                        System.out.println(files[i]);
                    } else { //如果是数组中元素是文件夹，继续递归
                        fileRecursion(files[i]);
                    }
                }
            }
        }
    }
}
