package org.anonymous.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see RandomAccessChannelTest 追加
 * @see ReadFile 多次写 buffer
 * @since 2021/1/21 14:49
 * 虽然 FileChannel 既可以 read 也可以 write, 但 FileInputStream 获取的 FileChannel 只能读, FileOutOutStream 获取的
 * FileChannel 只能写.
 * 写.
 */
public class FileChannelTest {

    public static void main(String[] args) throws IOException {
        try (
                FileChannel ic = new FileInputStream("netty/src/main/java/org/anonymous/nio/FileChannelTest.java").getChannel();
                FileChannel oc = new FileOutputStream("FileChannelTest.txt").getChannel()
        ) {
            // 将 FileChannel 里的数据全部映射成 ByteBuffer
            final MappedByteBuffer buffer = ic.map(FileChannel.MapMode.READ_ONLY, 0, ic.size());

            // 将 Buffer 里的数据输出
            oc.write(buffer);

            // clear
            buffer.clear();


            // 解码器
            final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
            // final CharsetDecoder decoder = Charset.forName("GBK").newDecoder();
            // 使用解码器将 ByteBuffer 转换成 CharBuffer
            final CharBuffer cb = decoder.decode(buffer);
            System.out.println(cb);
        }

    }

}
