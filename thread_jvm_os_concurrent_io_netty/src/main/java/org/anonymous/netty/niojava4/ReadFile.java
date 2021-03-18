package org.anonymous.netty.niojava4;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see FileChannelTest 一次性写
 * @see RandomAccessChannelTest 追加
 * @since 2021/1/21 15:16
 */
public class ReadFile {

    // 非一次性写出.
    public static void main(String[] args) throws IOException {
        try (
                final FileInputStream fis = new FileInputStream("netty/src/main/java/org/anonymous/nio/ReadFile.java");
                final FileChannel fc = fis.getChannel()
        ) {
            // 创建 ByteBuffer 对象, 用于重复读取
            final ByteBuffer buf = ByteBuffer.allocate(1024);

            // 将 Channel 中的数据放入 ByteBuffer 中
            while (fc.read(buf) != -1) { // read
                // 锁定 buffer 的空白区: 避免读取到空值
                buf.flip();

                // 解码为 CharBuffer, 输出.
                final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
                final CharBuffer cb = decoder.decode(buf);
                System.out.println(cb);

                // 初始化 buffer, 为下一次读取数据做准备
                buf.clear();
            }
        }
    }

}
