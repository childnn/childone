package org.anonymous.netty.filelock;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.nio.channels.FileChannel#lock 阻塞. 如果无法获得锁则阻塞
 * @see java.nio.channels.FileChannel#tryLock 尝试锁定文件, 直接返回而不是阻塞, 如果获得了文件锁, 则返回锁, 否则返回 null.
 * @since 2021/1/21 17:12
 * 排他锁
 */
public class FileLockTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        FileLock fl = null;
        try (
                final FileOutputStream fos = new FileOutputStream("FileChannelTest.txt");
                final FileChannel fc = fos.getChannel()
        ) {
            // 使用非阻塞方式对指定文件加锁
            fl = fc.tryLock();
            // 程序暂停 10s
            Thread.sleep(10000L);
        } finally {
            if (fl != null) {
                fl.release();
            }
        }
    }

}
