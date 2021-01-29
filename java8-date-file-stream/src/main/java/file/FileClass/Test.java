package file.FileClass;

import java.io.File;
import java.io.IOException;

/**
 * 2019年2月22日09:13:59
 * java.io.File类
 * 文件（file）和目录（directory）路径名的抽象表示形式
 * 绝对路径与相对路径：（二者的区分在于是否以盘符开头，而不管以什么结尾）
 * 绝对路径：以盘符开头的一定是绝对路径
 * 相对路径：不以盘符开头
 * 绝对路径是以根目录为基准，而相对路径是以自身位置到指定文件/文件夹的最短线路
 * 注意：通过相对路径无法找到同级文件夹下的文件
 * eg：文件夹一：D：\developer
 * 文件二：D：\aa.exe
 * 通过 文件 aa.exe 无法访问 developer 文件夹中的文件
 * java把电脑中的文件和文件夹（目录）封装为一个File类，我们可以用File类对文件和文件夹进行操作
 * <p>
 * 此类呈现分层路径名的一个抽象的、与系统无关的视图。
 * File 类的实例是不可变的；也就是说，一旦创建，File 对象表示的抽象路径名将永不改变。
 * 绝对路径名是完整的路径名，不需要任何其他信息就可以定位它所表示的文件。
 * 相反，相对路径名必须使用取自其他路径名的信息进行解释。
 * file：文件
 * directory：目录（文件夹）
 * path：路径
 * <p>
 * File 的成员变量（常量）
 * static String pathSeparator：与系统有关的路径分隔符，为了方便，它被表示为一个字符串。
 * static char pathSeparatorChar：与系统有关的路径分隔符。
 * （源码） public static final String pathSeparator = "" + pathSeparatorChar;
 * static String separator：与系统有关的默认名称分隔符，为了方便，它被表示为一个字符串。
 * static char separatorChar：与系统有关的默认名称分隔符。
 * （源码） public static final String separator = "" + separatorChar;
 * <p>
 * 构造方法：(构造方法创建的是一个 File 对象，是虚构（抽象）的文件/文件夹路径，而不是真的在系统相应的目录新建了文件，
 * 想要新建文件/文件夹，需要使用相应的 File 成员方法)
 * File(File parent, String child)
 * 根据 parent 抽象路径名和 child 路径名字符串创建一个新 File 实例。
 * File(String pathname)
 * 通过将给定路径名字符串转换为抽象路径名来创建一个新 File 实例。
 * File(String parent, String child)
 * 根据 parent 路径名字符串和 child 路径名字符串创建一个新 File 实例。
 * <p>
 * 在调用构造方法创建 File 对象时：
 * 1.把 File 对象指向路径所示的【末尾】 文件/文件夹 而不是指向整个 字符串路径
 * 2.若要通过 createNewFile() 新建文件，要求 父路径 必须存在，否则会报异常
 * 3.若父路径不存在，先 通过 父路径对象 调用mkdirs() 方法创建父路径，
 * 然后再在 新建的父路径下 通过子路径调用 createNewFile() 新建子文件/文件夹
 * 曾：
 * （文件）boolean createNewFile():【当且仅当不存在】具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件。//若要创建的文件已经存在，则返回 false
 * （文件夹）boolean mkdirs():创建此抽象路径名指定的目录，包括所有【必需但不存在】的父目录。 //
 * boolean mkdir() 创建由此抽象路径名命名的目录。
 * 删：
 * （文件/文件夹）boolean delete():删除此抽象路径名表示的文件或目录。【如果此路径名表示一个目录，则该目录必须为空才能删除。】
 * 只能从内往外依次分层删除（由小到大）
 * （新建时，实际上是由外往内新建）（由大到小）
 * <p>
 * File getAbsoluteFile()：返回此抽象路径名的绝对路径名形式。 （返回绝对路径的对象表示形式）
 * String getAbsolutePath():返回此抽象路径名的绝对路径名字符串。（返回绝对路径的字符串表示）
 * String getPath():将此抽象路径名转换为一个路径名字符串。（toString() 方法就是调用了 getPath() 方法）
 * String toString​() 返回此抽象路径名的路径名字符串。
 * String getName():返回由此抽象路径名表示的文件或目录的名称。 (返回的是构造方法传递文件/文件夹的结尾部分)
 * 判断file对象是否一指定格式结束：file.getName().endsWith(".txt")
 * <p>
 * long length()：返回由此抽象路径名表示的文件的长度。（文件的字节数，不可以操作文件件，文件夹没有大小概念）
 * 如果文件不存在，返回 0
 * 如果操作文件夹，返回 0
 * <p>
 * String getParent():返回此抽象路径名父目录的路径名字符串；如果此路径名没有指定父目录，则返回 null。
 * File getParentFile():返回此抽象路径名父目录的抽象路径名；如果此路径名没有指定父目录，则返回 null。
 */
public class Test {
    public static void main(String[] args) throws IOException {
        //字符串形式的分隔符
        //        System.out.println(File.pathSeparator); //; 路径分隔符：分号
        //        System.out.println(File.separator); // \  文件名称分隔符：反斜杠

        File file = new File("C:\\devalope\\software");
        File file1 = new File(file, "a.txt");
        //        System.out.println(file); // C:\devalope\software
        //        System.out.println(file1); // C:\devalope\software\a.txt

        File file2 = new File("D:\\software\\baidu", "b.java");
        //        System.out.println(file2); // D:\software\baidu\b.java

        //创建新【文件】
        /*File file3 = new File("D:\\abc.txt");
        boolean b = file3.createNewFile(); //此时 D 盘根目录下新建了一个 abc.txt 文件
        System.out.println(file3);
        System.out.println(b); //若新建的文件已经存在，返回false，新建失败*/

        //在当前项目下新建文件
        /*file3 = new File("abc.txt");
        //当 新建的文件已经存在时，返回false，创建失败
        //当 新建文件不存在时，返回true，创建成功
        boolean newFile = file3.createNewFile(); //此时， 当前java 项目 根目录 下新建了一个 abc.txt
        System.out.println(file3);*/

       /* file2 = new File("D:\\AAA");
        boolean nf = file2.createNewFile(); //创建的是文件而不是文件夹，（就算没有写文件类型）
        System.out.println(nf); //true*/

        file = new File("D:\\software\\天若OCR文字识别\\Data\\update.exe");
        //        System.out.println(file.isFile());
        File f = new File("D:\\Develope\\J2EE\\Day08FileAndRecursion\\src\\com\\itheima\\FileClass\\Test.java");
        System.out.println(f.getName()); //文件/文件夹名: File 路径结尾部分
        System.out.println(f.getPath()); //File 文件/文件夹的字符串形式
        System.out.println(f.toString()); //等价 getPath()
    }
}
