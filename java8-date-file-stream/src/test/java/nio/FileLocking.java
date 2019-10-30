package nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.concurrent.TimeUnit;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/29 23:21
 * 文件锁定可同步访问，因此文件可以共享资源。但是，争用同一文件的两个线程可能位于不同的 JVM 中，
 * 或者一个可能是 Java 线程，另一个可能是操作系统中的本机线程。文件锁对其他操作系统进程可见，因为 Java 文件锁定直接映射到本机操作系统锁定工具。
 * ---
 * 底层操作系统必须提供对独占锁或共享锁的支持。如果操作系统不支持共享锁并且对一个操作系统发出请求，则使用独占锁。
 * 可以使用 **FileLock.**`isShared()` 查询锁的类型（共享或独占）。
 */
public class FileLocking {
    public static void main(String[] args) {
        // 通过调用 **FileChannel** 上的 `tryLock()` 或 `lock()`，可以获得整个文件的 **FileLock**。
        // （**SocketChannel**、**DatagramChannel** 和 **ServerSocketChannel** 不需要锁定，
        // 因为它们本质上是单进程实体；通常不会在两个进程之间共享一个网络套接字）。
        try (
                FileOutputStream fos = new FileOutputStream("file.txt");
                // `tryLock()` 是非阻塞的。它试图获取锁，若不能获取（当其他进程已经持有相同的锁，并且它不是共享的），它只是从方法调用返回。
                // `lock()` 会阻塞，直到获得锁，或者调用 `lock()` 的线程中断，或者调用 `lock()` 方法的通道关闭。使用 **FileLock.**`release()` 释放锁。
                FileLock fl = fos.getChannel().tryLock()
        ) {
            if (fl != null) {
                System.out.println("Locked File");
                TimeUnit.MILLISECONDS.sleep(100);
                fl.release();
                System.out.println("Released Lock");
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
