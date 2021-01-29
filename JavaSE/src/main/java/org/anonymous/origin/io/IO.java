package interview.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.channels.Channel;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author child
 * 2019/7/3 18:37
 * <p>
 * 同步, 异步, 阻塞, 非阻塞
 * 同步请求: A 调用 B, B 处理请求是同步的, 在 处理完成之前 不会通知 A,
 * 只有处理完之后才会明确的通知 A.
 * 异步请求: A 调用 B, B 处理请求是异步的, 在 接到请求后 先告诉 A,已经接到请求,
 * 然后 异步去处理, 处理完之后通过回调等方式在通知 A.
 * 阻塞请求: A 调用 B, A 一直等着 B 的返回, 别的事情什么也不干
 * 非阻塞请求: A 调用 B, A 不用一直等着 B 返回, 先去忙别的事情
 * <p>
 * 同步与异步最大的区别在于 被调用方 的执行方式和返回时机,
 * 同步指的是 被调用方 做完事情之后再返回,
 * 异步指的是 被调用方 先返回,然后再做事情,做完之后再想办法通知调用方.
 * 阻塞与非阻塞最大的区别在于,在被调用方法返回结果之前的这段时间内, 调用方 是否一直等待.
 * 阻塞指的是 调用方 一直等待,别的事情什么都不做
 * 非阻塞指的是 调用方 法先去忙别的事情(仍然需要不断的主动询问, 是否有结果了).
 * <p>
 * 同步/异步 与 阻塞/非阻塞的区别:
 * 针对的对象不同: 同步/异步 针对【被调用者】, 阻塞/非阻塞 针对【调用者】
 * 同步阻塞: 在水烧开之前, 坐在水壶前等着水烧开
 * 同步非阻塞: 在水烧开之前, 先做别的事情, 但是水壶不会主动通知, 需要我们时不时去看水有没有烧开.
 * 异步阻塞: 带有提醒功能的水壶, 在水壶发出水烧开的提醒之前,一直在水壶前等着水开
 * 异步非阻塞: 带有提醒功能的水壶, 在水壶发出水烧开的提醒之前, 做别的事情, 直到水壶发出水烧开的声音
 * 阻塞/非阻塞: 人 (调用方) 是否坐在水壶前面一直等
 * 同步/异步: 水壶 (被调用方) 是否在水烧开之后主动通知人
 * <p>
 * BIO: Blocking I/O, 同步阻塞 I/O. 数据的读写必须阻塞在一个线程内等待其完成
 * 有一排水壶在烧开水, 直到这个水壶烧开, 才去处理下一个水壶, 但实际上线程在等待水壶烧开的时间段什么都没做
 * NIO: New I/O, 同时支持 阻塞/非阻塞模式, 但主要是 同步非阻塞I/O
 * 一个线程不断的 轮询 每个水壶的状态, 看是否有水壶的状态发生改变, 从而进行下一步操作
 * AIO: Asynchronous I/O: 异步非阻塞 I/O 模型.
 * 为每个水壶上面装了一个开关, 水烧开之后, 水壶会自动通知我水烧开了.
 * <p>
 * 同步: 一个任务的完成依赖另一个任务, 只有等待被依赖的任务完成后, 依赖的任务才能算完成, 这是一种可靠的任务队列.
 * 异步:
 * @see java.io  BIO 传统的 io 包, 基于 流模型实现, 交互的方式是 同步、阻塞方式, 也就是说在
 * 读入输入流或者输出流时, 在读写动作完成之前, 线程会一直阻塞在那里(read/write).
 * 优点就是代码简单、逻辑直观; 缺点就是 io 的效率和扩展性低, 容易称为应用性瓶颈.
 * @see java.nio NIO jdk 1.4+
 * 提供了 {@link Channel}, {@link Selector}, {@link Buffer} 等新的抽象
 * 可以构建多路复用,同步非阻塞 io 程序, 同时提供了更接近操作系统底层高性能的数据操作方式.
 * @see java.nio.channels.AsynchronousSocketChannel  jdk 1.7+
 * @see java.nio.channels.AsynchronousServerSocketChannel
 * @see java.nio.channels.AsynchronousFileChannel
 * @see java.nio.channels.AsynchronousByteChannel
 * nio 升级版, 提供了 异步非阻塞 的 io 操作方式, Asynchronous IO, 异步 io 是基于事件和回调机制实现的,
 * 也就是应用操作之后会直接返回, 不会阻塞在那里, 当后台处理完成, 操作系统会通知相应的线程进行后续的操作.
 * <p>
 * 传统 io
 * 字节流: {@link java.io.InputStream} {@link java.io.OutputStream}
 * 字符流: {@link java.io.Reader} {@link java.io.Writer}
 * 磁盘 io: {@link java.io.File}
 * 网络 io: {@link java.net.Socket}
 */
public class IO {

    public static void nio() throws IOException {
        //        byte[] bytes = new byte[1024];
        //        Path write = Files.write(Paths.get("Summary/src/interview/io/IO.java"), bytes, StandardOpenOption.APPEND);
        //        byte[] bys = Files.readAllBytes(Paths.get("Summary\\src\\interview\\io\\IO.java"));
        List<String> strings = Files.readAllLines(Paths.get("Summary\\src\\interview\\io\\IO.java"), StandardCharsets.UTF_8);
        strings.forEach(System.err::println);
        //        System.out.println(Arrays.toString(bys));
        //        System.out.println(Arrays.toString(bytes));
        //        System.out.println("write = " + write);

        //        Files.createDirectories()
    }

    public static void main(String[] args) throws IOException {
        nio();
    }

    public void bio() throws IOException {
        FileReader reader = null;
        FileWriter writer = null;
        BufferedReader bf = null;
        BufferedWriter bw = null;
        reader = new FileReader("");
        writer = new FileWriter("", true);

        bf = new BufferedReader(reader);
        bw = new BufferedWriter(writer);
        String str;
        while (null != (str = bf.readLine())) {
            bw.write(str);
        }
            /*char[] chs = new char[1024];
            int len;
            while (-1 != (len = reader.read(chs))) {
                writer.write(chs, 0, len);
            }*/
        writer.close();
        reader.close();
        bw.flush();
        bw.close();
        bf.close();
        //        writer.flush();
    }

}