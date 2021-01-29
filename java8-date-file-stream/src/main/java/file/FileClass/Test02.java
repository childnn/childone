package file.FileClass;

import java.io.File;
import java.util.Arrays;

/**
 * 2019年2月22日11:38:10
 * 遍历文件夹：如果用文件来遍历，会在迭代器遍历报NPE
 * 可以自动遍历获取到文件夹中的隐藏文件/文件夹
 * 重点：注意 NPE：如果 对象 以文件结尾，调用 方法返回的数组值为 【null】！！！不可以再遍历
 * String[] list()：返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录。
 * File[] listFiles()：返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
 */
public class Test02 {
    public static void main(String[] args) {
        File file = new File("D:\\Develope");
        String[] list = file.list();
        /*
        [BasicCode71, desktop.ini, eclipse, Everything, feiQ, FeiQ.exe, Hydra, IntelliJ IDEA 2018.3.4, J2EE, java, Java1.9, Java11.0.2, myEclipse, Notepad++, VC6_DownZa.Cn, 控屏软件]
        不使用 list.toString() 的原因：
                1.为了 防止 NPE
                2.数组的 toString() 输出的还是地址值（不能重写toString()方法）
         */
        String s = Arrays.toString(list);
        System.out.println(Arrays.toString(list)); //[Ljava.lang.String;@2ac1fdc4
        //普通for
        /*for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]);
        }*/
        //超级for
        /*for (String s : list) {
            System.out.println(s);
        }*/
        //file目录以文件结尾
        file = new File("D:\\Develope\\FeiQ.exe");
        String[] list1 = file.list(); //返回值为null，如果继续遍历，会报 NPE //这时不应再继续遍历，应该直接输出 对象名
        System.out.println(Arrays.toString(list1)); //null
        File[] files = file.listFiles(); //null
        System.out.println(files);

    }
}
