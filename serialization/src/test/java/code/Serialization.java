package code;

import org.junit.Before;
import org.junit.Test;
import pojo.Student;

import java.io.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/22 15:35
 */
public class Serialization {

    @Before
    @Test
    public void serial() throws IOException {
        Student stu = new Student().setAge(100).setName("jack").setSex("male");
        System.out.println("serial = " + stu);
        FileOutputStream fs = new FileOutputStream("stu.ser"); // 输出流: 没有指定文件就会创建.
        ObjectOutputStream os = new ObjectOutputStream(fs);
        os.writeObject(stu);

        os.close();
    }

    @Test
    public void deSerial() throws IOException, ClassNotFoundException {
        FileInputStream fi = new FileInputStream("stu.ser"); // 输入流: 没有指定文件就会报错.
        ObjectInputStream os = new ObjectInputStream(fi);
        Student stu = (Student) os.readObject();
        os.close();
        System.out.println("deSerial = " + stu);
    }
}
