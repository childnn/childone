package nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/29 21:56
 * 内存映射文件能让你创建和修改那些因为太大而无法放入内存的文件。有了内存映射文件，
 * 你就可以认为文件已经全部读进了内存，然后把它当成一个非常大的数组来访问。
 * 这种解决办法能大大简化修改文件的代码：
 * ---
 * 为了读写，我们从 **RandomAccessFile** 开始，获取该文件的通道，然后调用 `map()` 来生成 **MappedByteBuffer** ，
 * 这是一种特殊的直接缓冲区。你必须指定要在文件中映射的区域的起始点和长度—这意味着你可以选择映射大文件的较小区域。
 * <p>
 * **MappedByteBuffer**  继承了 **ByteBuffer**，所以拥有**ByteBuffer** 全部的方法。
 * 这里只展示了 `put()` 和 `get()` 的最简单用法，但是你也可以使用 `asCharBuffer()` 等方法。
 * ---
 * 使用前面的程序创建的文件长度为 128MB，可能比你的操作系统单次所允许的操作的内存要大。
 * 该文件似乎可以同时访问，因为它只有一部分被带进内存，而其他部分被交换出去。
 * 这样，一个非常大的文件（最多 2GB）可以很容易地修改。**注意**，操作系统底层的文件映射工具用于性能的最大化。
 */
public class LargeMappedFiles {
    static int length = 0x8000000; // 128 MB

    public static void main(String[] args) throws Exception {
        try (
                RandomAccessFile tdat = new RandomAccessFile("test.dat", "rw")
        ) {
            MappedByteBuffer out = tdat.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, length);
            for (int i = 0; i < length; i++)
                out.put((byte) 'x');
            System.out.println("Finished writing");
            for (int i = length / 2; i < length / 2 + 6; i++)
                System.out.print((char) out.get(i));
        }
    }
}
