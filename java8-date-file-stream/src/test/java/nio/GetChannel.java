package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/27 21:46
 * 我们这里所讲的任何流类，都可以通过调用 `getChannel( )` 方法生成一个 **FileChannel**（文件通道）。
 * **FileChannel** 的操作相当基础：操作 **ByteBuffer**  来用于读写，并独占式访问和锁定文件区域(稍后将对此进行描述)。
 * <p>
 * 将字节放入 **ByteBuffer** 的一种方法是直接调用 `put()` 方法将一个或多个字节放入 **ByteBuffer**；
 * 当然也可以是其它基本类型的数据。此外，参考上例，我们还可以调用 `wrap()` 方法包装现有字节数组到 **ByteBuffer**。
 * 执行此操作时，不会复制底层数组，而是将其用作生成的 **ByteBuffer** 存储。这样产生的 **ByteBuffer** 是数组“支持”的。
 * <p>
 * data.txt 文件被 **RandomAccessFile** 重新打开。**注意**，你可以在文件中移动 **FileChanne**。
 * 在这里，它被移动到末尾，以便添加额外的写操作。
 * <p>
 * 对于只读访问，必须使用静态 `allocate()` 方法显式地分配 **ByteBuffer**。**NIO** 的目标是快速移动大量数据，
 * 因此 **ByteBuffer** 的大小应该很重要 —— 实际上，这里设置的 1K 都可能偏小了(我们在工作中应该反复测试以找到最佳大小)。
 * <p>
 * 通过使用 `allocateDirect()` 而不是 `allocate()` 来生成与操作系统具备更高耦合度的“直接”缓冲区，
 * 也有可能获得更高的速度。然而，这种分配的开销更大，而且实际效果因操作系统的不同而有所不同，
 * 因此，在工作中你必须再次测试程序，以检验直接缓冲区是否能为你带来速度上的优势。
 * <p>
 * 一旦调用 **FileChannel** 类的 `read()` 方法将字节数据存储到 **ByteBuffer** 中，你还必须调用缓冲区上的 `flip()`
 * 方法来准备好提取字节（这听起来有点粗糙，实际上这已是非常低层的操作，且为了达到最高速度）。如果要进一步调用 `read()`
 * 来使用 **ByteBuffer** ，还需要每次 `clear()` 来准备缓冲区。
 * ---
 * **FileChannel** 用于读取；**FileChannel** 用于写入。当 **ByteBuffer** 分配好存储，调用 **FileChannel** 的 `read()`
 * 方法返回 **-1**（毫无疑问，这是来源于 Unix 和 C 语言）时，说明输入流读取完了。在每次 `read()` 将数据放入缓冲区之后，
 * `flip()` 都会准备好缓冲区，以便 `write()` 提取它的信息。在 `write()` 之后，数据仍然在缓冲区中，我们需要 `clear()`
 * 来重置所有内部指针，以便在下一次 `read()` 中接受数据。
 */
public class GetChannel {
    private static final int BSIZE = 1024;
    private static String name = "data.txt";

    public static void main(String[] args) {
        // 写入一个文件:
        try (
                FileChannel fc = new FileOutputStream(name).getChannel()
        ) {
            fc.write(ByteBuffer.wrap("Some text ".getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 在文件尾添加：
        try (
                FileChannel fc = new RandomAccessFile(name, "rw").getChannel()
        ) {
            fc.position(fc.size()); // 移动到结尾
            fc.write(ByteBuffer.wrap("Some more".getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 读取文件e:
        try (
                FileChannel fc = new FileInputStream(name).getChannel()
        ) {
            ByteBuffer buff = ByteBuffer.allocate(BSIZE);
            fc.read(buff);
            buff.flip();
            while (buff.hasRemaining())
                System.out.write(buff.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.flush();
    }

}
