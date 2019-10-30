package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/27 22:21
 */
public class ChannelCopy {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println(
                    "arguments: sourcefile destfile");
            System.exit(1);
        }
        try (
                FileChannel in = new FileInputStream(
                        args[0]).getChannel();
                FileChannel out = new FileOutputStream(
                        args[1]).getChannel()
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
            while (in.read(buffer) != -1) {
                buffer.flip(); // 准备写入
                out.write(buffer);
                buffer.clear();  // 准备读取
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
