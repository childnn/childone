package file.FileClass.CreateTest;

import java.io.File;
import java.io.IOException;

/**
 * 2019年2月22日14:28:25
 * 创建新 【文件】：
 * boolean createNewFile​()
 * 1.在指定盘符下新建文件：
 * 1）在指定盘符根目录下直接新建文件，
 * 若盘符存在：不管有没有写文件后缀，新建的都是 文件！！
 * 若文件已经存在，返回false，覆盖之前的文件
 * 若盘符不存在：IOException
 * 2)不指定盘符：默认当前项目路径
 * 不管写不写后缀，新建的都是文件
 * 父路径必须存在！！！
 */
public class Test {
    public static void main(String[] args) throws IOException {
        //指定盘符
        File file = new File("E:\\123");
        //        boolean nf = file.createNewFile(); //Exception,盘符不存在
        //        System.out.println(nf);
        file = new File("D:\\123\\a.txt");
        //        boolean nf = file.createNewFile(); //Exception, 父路径不存在
        //        System.out.println(nf);
        //不指定盘符
        //        file = new File("abc");
        //        boolean nf = file.createNewFile(); //在当前项目下新建文件
        //        System.out.println(nf);
        //        file = new File("abc.txt");
        //        boolean nf = file.createNewFile();
        //        System.out.println(nf);

    }
}
