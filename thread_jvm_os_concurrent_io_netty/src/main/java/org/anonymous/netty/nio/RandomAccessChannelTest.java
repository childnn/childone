package org.anonymous.netty.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.io.RandomAccessFile#getChannel() 该方法返回的 FileChannel 读写权限取决于
 * RandomAccessFile 打开文件的模式.
 * 追加
 * @see FileChannelTest
 * @see ReadFile
 * @since 2021/1/21 15:06
 */
public class RandomAccessChannelTest {

    /**
     * 每运行一次, 就将 源文件 FileChannelTest.txt 复制并追加在文件后面.
     */
    public static void main(String[] args) throws IOException {
        try (
                final RandomAccessFile raf = new RandomAccessFile("FileChannelTest.txt", "rw");
                final FileChannel fc = raf.getChannel()
        ) {
            // 将 Channel 中的数据全部映射成 Buffer
            final MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

            // 把 Channel 的记录指针移动到最后: 追加
            fc.position(fc.size());
            // 将 buffer 所有的数据输出
            fc.write(buffer);
        }
    }

}
