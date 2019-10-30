package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/27 22:24
 * 方法 `transferTo()` 和 `transferFrom()` 允许你直接连接此通道到彼通道：
 */
public class TransferTo {
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
            in.transferTo(0, in.size(), out);
            // Or:
            // out.transferFrom(in, 0, in.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
