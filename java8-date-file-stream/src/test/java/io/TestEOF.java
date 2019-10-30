package io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/27 18:31
 * 注意，`available()` 的工作方式会随着所读取媒介类型的不同而有所差异，它的字面意思就是“在没有阻塞的情况下所能读取的字节数”。
 * 对于文件，能够读取的是整个文件；但是对于其它类型的“流”，可能就不是这样，所以要谨慎使用。
 * 我们也可以通过捕获异常来检测输入的末尾。但是，用异常作为控制流是对异常的一种错误使用方式。
 */
public class TestEOF {
    public static void main(String[] args) {
        try (
                DataInputStream in = new DataInputStream(
                        new BufferedInputStream(
                                new FileInputStream("java8-date-file-stream/src/test/java/io/TestEOF.java")))
        ) {
            while (in.available() != 0)
                System.out.write(in.readByte());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
