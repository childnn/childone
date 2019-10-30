package io;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/27 20:22
 */
public class UsingRandomAccessFile {
    static String file = "rtest.dat";

    /**
     * `display()` 方法打开了一个文件，并以 `double` 值的形式显示了其中的七个元素。在 `main()` 中，
     * 首先创建了文件，然后打开并修改了它。因为 `double` 总是 8 字节长，所以如果要用 `seek()` 定位到第 5 个
     * （从 0 开始计数） `double` 值，则要传入的地址值应该为 `5*8`。
     */
    public static void display() {
        try (
                RandomAccessFile rf =
                        new RandomAccessFile(file, "r")
        ) {
            for (int i = 0; i < 7; i++)
                System.out.println(
                        "Value " + i + ": " + rf.readDouble());
            System.out.println(rf.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try (
                RandomAccessFile rf =
                        new RandomAccessFile(file, "rw")
        ) {
            for (int i = 0; i < 7; i++)
                rf.writeDouble(i * 1.414);
            rf.writeUTF("The end of the file");
            rf.close();
            display();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (
                RandomAccessFile rf =
                        new RandomAccessFile(file, "rw")
        ) {
            rf.seek(5 * 8);
            rf.writeDouble(47.0001);
            rf.close();
            display();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
