package file.FileClass.FileFilterInterface;

import java.io.File;
import java.io.FileFilter;

/**
 * 2019年2月22日16:20:49
 * File[] listFiles(FileFilter filter)：返回抽象路径名数组，这些路径名表示此抽象路径名表示的目录中满足指定过滤器的文件和目录。
 * java.io.FileFilter 接口：
 * 用于抽象路径名的过滤器。
 * boolean accept(File pathname) ：测试指定抽象路径名是否应该包含在某个路径名列表中。
 * 参数：遍历listFiles得到的File对象
 * <p>
 * File[] listFiles(FilenameFilter filter)：返回抽象路径名数组，这些路径名表示此抽象路径名表示的目录中满足指定过滤器的文件和目录。
 * java.io.FilenameFilter 接口：
 * 实现此接口的类实例可用于过滤器文件名
 * boolean accept(File dir, String name) ：测试指定文件是否应该包含在某一文件列表中。
 * 参数：
 * File dir: File类构造方法的文件目录
 * String name:
 */
public class Test {
    public static void main(String[] args) {
        //File[] listFiles(FileFilter filter)：返回抽象路径名数组，这些路径名表示此抽象路径名表示的目录中满足指定过滤器的文件和目录。
        File file = new File("D:\\");
        fileRecursion(file);
    }

    private static void fileRecursion(File file) {
        /*if (file.isFile()) { //如果是文件，直接输出
            System.out.println(file);
        } else {*/
        //listFiles(FileFilter pathname)方法做了三件事：
        // 1. 对构造方法中传递的目录进行遍历，获取目录中的每一个文件/文件夹--> 封装为File对象
        // 2. 调用参数传递的过滤器中的方法 accept(File pathname)
        // 3. 将遍历得到的每一个 File 对象，传递给 accept 方法的参数 pathname，进行指定的比较
        // accept() 返回值：
        // true：把 File 对象保存到 等号左边的 数组中
        // false： 不会存入数组
        File[] arr = file.listFiles(new FileFilter() { //按指定规则过滤 //当 file 以文件结尾时，arr==null
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                }
                //                return pathname.toString().toLowerCase().endsWith(".avi");
                return pathname.getName().toLowerCase().endsWith(".java");
            }
        });
        //数组转 字符串
        //        String s = Arrays.toString(arr);
        /*//遍历字符串
            for (int i = 0; i < s.length(); i++) {
                //字符串新建 File 对象
                File file1 = new File("" + s.charAt(i));

        }*/
        if (arr == null) { //如果file 以文件的格式结尾，数组收到的是 null
            System.out.println(file);
        } else { //如果数组不是 null
            //遍历File对象数组
            for (File files : arr) {
                if (files.isFile()) { //如果是文件，输出
                    System.out.println(files);
                } else { //如果是文件夹，继续传递 //这里else去掉就 NPE 为什么？ 不写 else 上面 if() 中的代码还会继续往下
                    //这跟return 不同，如果是 if 中只有 return 语句，那么 else 可以省略
                    fileRecursion(files);
                }
            }
        }
    }
}
