package io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see TestEOF
 * @since 2020/3/27 18:28
 * 如果我们用 `readByte()` 从 `DataInputStream` 一次一个字节地读取字符，那么任何字节的值都是合法结果，
 * 因此返回值不能用来检测输入是否结束。取而代之的是，我们可以使用 `available()` 方法得到剩余可用字符的数量。
 */
public class FormattedMemoryInput {
    public static void main(String[] args) {
        try (
                DataInputStream in = new DataInputStream(
                        new ByteArrayInputStream(
                                BufferedInputFile.read(
                                        "java8-date-file-stream/src/test/java/io/FormattedMemoryInput.java")
                                        .getBytes()))
        ) {
            while (true)
                System.out.write((char) in.readByte());
        } catch (EOFException e) {
            System.out.println("\nEnd of stream");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
