package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/29 23:25
 * 文件映射通常用于非常大的文件。你可能需要锁定此类文件的某些部分，以便其他进程可以修改未锁定的部分。
 * 例如，数据库必须同时对许多用户可用。这里你可以看到两个线程，每个线程都锁定文件的不同部分:
 */
public class LockingMappedFiles {
    static final int LENGTH = 0x8FFFFFF; // 128 MB
    static FileChannel fc;

    public static void main(String[] args) throws Exception {
        fc = new RandomAccessFile("test.dat", "rw").getChannel();
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);
        for (int i = 0; i < LENGTH; i++)
            out.put((byte) 'x');
        new LockAndModify(out, 0, LENGTH / 3);
        new LockAndModify(out, LENGTH / 2, LENGTH / 2 + LENGTH / 4);
    }

    // **LockAndModify** 线程类设置缓冲区并创建要修改的 `slice()`，在 `run()` 中，
    // 锁在文件通道上获取（不能在缓冲区上获取锁—只能在通道上获取锁）。
    // `lock()` 的调用非常类似于获取对象上的线程锁 —— 现在有了一个“临界区”，可以对文件的这部分进行独占访问。
    private static class LockAndModify extends Thread {
        private ByteBuffer buff;
        private int start, end;

        LockAndModify(ByteBuffer mbb, int start, int end) {
            this.start = start;
            this.end = end;
            mbb.limit(end);
            mbb.position(start);
            buff = mbb.slice();
            start();
        }

        // 当 JVM 退出或关闭获取锁的通道时，锁会自动释放，但是你也可以显式地调用 **FileLock** 对象上的 `release()`
        @Override
        public void run() {
            try {
                // Exclusive lock with no overlap:
                FileLock fl = fc.lock(start, end, false);
                System.out.println("Locked: " + start + " to " + end);
                // Perform modification:
                while (buff.position() < buff.limit() - 1)
                    buff.put((byte) (buff.get() + 1));
                fl.release();
                System.out.println("Released: " + start + " to " + end);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
