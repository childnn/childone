package org.anonymous.nio;

import io.netty.channel.nio.NioEventLoop;

import java.net.SocketAddress;
import java.nio.IntBuffer;
import java.nio.InvalidMarkException;
import java.nio.channels.*;
import java.util.Arrays;

/**
 * BIO 以流的方式处理数据, NIO 以块(缓冲区, Buffer)的方式处理数据, 块 I/O 的效率比流 I/O 高很多
 * BIO 是阻塞的, NIO 是非阻塞的
 * BIO 基于字节流和字符流进行操作, 而 NIO 基于 Channel(通道) 和 Buffer(缓冲区) 进行操作, 数据总是从
 * 通道读取到缓冲区中, 或者从缓冲区写入到通道中. Selector(选择器)用于监听多个通道的事件(eg: 连接请求, 数据到达等),
 * 因此用单个线程就可以监听多个客户端通道.
 * <p>
 * Selector, Channel, Buffer
 *
 * @see java.nio.channels.Selector
 * @see java.nio.channels.Channel
 * @see java.nio.Buffer
 * 1. 每个线程 Thread 对应一个 Selector, 每个 Selector/Thread 对应多个 Channel(连接)
 * 2. 程序会切换到哪个 Channel 是由 Event 事件决定的, Event 是一个重要的概念
 * 3. Selector 会根据不同的事件, 在各个通道上切换
 * 4. Buffer 就是一个内存块, 底层可以认为是一个数组
 * 5. 数据的 读写 都是通过 Buffer, 由 flip(切换)方法执行;
 * 而 BIO 中 输入/输出流是分开的.
 * 6. Channel 是双向的, 可以返回底层操作系统的情况, 比如 Linux, 底层的操作系统通道就是双向的.
 * <p>
 * 缓冲区 Buffer: 缓冲区本质上是一个可以 读写数据 的内存块, 可以理解成是一个 容器对象(包括数组),
 * 该对象提供了 一组方法, 可以更轻松地使用内存块, 缓冲区对象 内置一些机制, 能够跟踪和记录缓冲区的状态
 * 变化情况. Channel 提供从文件、网络读取数据的渠道, 但是读写数据都必须经由 Buffer.
 * <p>
 * {@code java.nio.Buffer#mark} 标记.
 * A buffer's <i>mark</i> is the index to which its position will be reset
 * when the {@link java.nio.Buffer#reset reset} method is invoked.  The mark is not always
 * defined, but when it is defined it is never negative and is never greater
 * than the position.  If the mark is defined then it is discarded when the
 * position or the limit is adjusted to a value smaller than the mark.  If the
 * mark is not defined then invoking the {@link java.nio.Buffer#reset reset} method causes an
 * {@link InvalidMarkException} to be thrown.
 * {@code java.nio.Buffer#position}  位置(指针的位置). 下一个要被读/写的元素的索引, 每次读写数据都会改变该值, 为下次读写做准备.
 * ## A buffer's <i>position</i> is the index of the next element to be read or written.
 * A buffer's position is never negative and is never greater than its limit.
 * {@code java.nio.Buffer#limit}  表示缓冲区的当前终点, 该值不可大于 capacity, 不能对缓冲区超过极限的位置进行读写操作
 * 且极限位置可以修改.
 * ## A buffer's <i>limit</i> is the index of the first element that should not be read or written.
 * A buffer's limit is never negative and is never greater than its capacity.
 * {@code java.nio.Buffer#capacity}  容量. 即 当前 Buffer 可以容纳的最大数据量; 在缓冲区创建时被设定并且不能改变
 *
 * <li><p> {@link java.nio.Buffer#clear} makes a buffer ready for a new sequence of
 * channel-read or relative <i>put</i> operations: It sets the limit to the
 * capacity and the position to zero.  </p></li>
 * *
 * <li><p> {@link java.nio.Buffer#flip} makes a buffer ready for a new sequence of
 * channel-write or relative <i>get</i> operations: It sets the limit to the
 * current position and then sets the position to zero.  </p></li>
 * *
 * <li><p> {@link java.nio.Buffer#rewind} makes a buffer ready for re-reading the data that
 * it already contains: It leaves the limit unchanged and sets the position
 * to zero.  </p></li>
 * ##################################
 * Channel 通道
 * NIO 的通道类似于流, 但有些区别
 * 1. 通道可以同时进行读写, 而流只能读或者只能写
 * 2. 通道可以实现异步读写数据
 * 3. 通道可以从缓冲区读数据, 也可以写数据到缓冲区
 * @see java.nio.channels.FileChannel
 * @see java.nio.channels.DatagramChannel
 * @see java.nio.channels.ServerSocketChannel
 * @see java.nio.channels.SocketChannel
 * ##################################
 * Selector 能够检测多个注册的通道上是否有事件发生(多个 Channel 以事件的方式注册到同一个 Selector),
 * 如果有事件发生, 便获取事件然后针对每个事件进行相应的处理. 这样就可以只用一个单线程去管理多个通道,
 * 也就是管理多个连接和请求.
 * 只有在 连接/通道 真正有读写事件发生时, 才会进行读写, 就大大减少了系统开销, 并且不必为每个连接都创建一个线程,
 * 不用去维护多个线程, 避免了多线程之间上下文切换导致的开销
 * 1. Netty 的 IO 线程 {@link NioEventLoop} 聚合了 Selector(选择器, 也叫多路复用), 可以同时并发处理成百上千个客户端链接.
 * 2. 当线程从某个客户端 Socket 通道进行读写数据时, 若没有数据可用时, 该线程可以进行其他任务.
 * 3. 线程通常将非阻塞 IO 的空闲时间用于在其他通道上执行 IO 操作, 所以单独的线程可以管理多个输入和输出通道.
 * 4. 由于 读写 操作都是 非阻塞的, 这就可以充分提升 IO 线程的运行效率, 避免由于频繁 IO 阻塞导致的线程挂起.
 * 5. 一个 IO 线程可以并发处理 N 个客户端连接和读写操作, 这从根本上解决了传统同步阻塞 IO 一连接一线程模型,
 * 架构的性能、弹性伸缩能力和可靠性都得到了极大的提升.
 * <p>
 * ##################################
 * 1. 当客户端连接时, 会通过 {@link java.nio.channels.ServerSocketChannel} 得到客户端对应的 {@link java.nio.channels.SocketChannel}
 * 2. 将 {@link java.nio.channels.SocketChannel} {@link SelectableChannel#register} 注册到 {@link java.nio.channels.Selector} 上,
 * 一个 {@link java.nio.channels.Selector} 可以注册多个 {@link java.nio.channels.SocketChannel}
 * 3. 每个 {@link java.nio.channels.SocketChannel} 注册成功后返回一个 {@link java.nio.channels.SelectionKey}
 * 得到与 {@link java.nio.channels.Selector} 关联的 {@link java.nio.channels.SelectionKey} 集合
 * 4. {@link java.nio.channels.Selector} 监听 {@link Selector#select}, 返回 有事件发生的通道个数, 进一步得到有事件发生
 * 的 {@link java.nio.channels.SelectionKey}
 * 5. 由 {@link SelectionKey#channel} 方法得到 {@link java.nio.channels.SocketChannel}
 * 进而使用得到的 channel 完成一系列操作.
 * ##################################
 * @see SelectionKey A token representing the registration of a {@link SelectableChannel} with a {@link Selector}.
 * @see SelectionKey#OP_READ    int OP_READ = 1 << 0; Operation-set bit for read operations.
 * @see SelectionKey#OP_WRITE   int OP_WRITE = 1 << 2; Operation-set bit for write operations.
 * @see SelectionKey#OP_CONNECT int OP_CONNECT = 1 << 3; Operation-set bit for socket-connect operations.
 * @see SelectionKey#OP_ACCEPT  int OP_ACCEPT = 1 << 4; Operation-set bit for socket-accept operations.
 * @see SelectionKey#selector()
 * @see SelectionKey#channel()
 * ##################################
 * @see java.nio.channels.ServerSocketChannel 在服务器监听新的客户端 Socket 连接
 * @see ServerSocketChannel#open() 得到一个 ServerSocketChannel 通道
 * @see ServerSocketChannel#bind 设置服务器端端口号
 * @see ServerSocketChannel#configureBlocking 设置阻塞或非阻塞模式, false 表示 非阻塞
 * @see ServerSocketChannel#accept() 接受一个连接, 返回该连接的 SocketChannel
 * @see ServerSocketChannel#register 注册一个选择器并设置监听事件的类别
 * -----------------------
 * @see java.nio.channels.SocketChannel 网络 IO 通道, 具体负责进行读写操作. NIO 把缓冲区的数据写入通道,
 * 或者把通道里的数据读到缓冲区
 * @see SocketChannel#open() 得到一个 SocketChannel 通道
 * @see SocketChannel#configureBlocking false 非阻塞
 * @see SocketChannel#connect(SocketAddress) 连接服务器
 * @see SocketChannel#finishConnect() 如果 {@link SocketChannel#connect(SocketAddress)} 连接失败,
 * 需要通过该方法完成连接操作
 * @see SocketChannel#register 注册选择器, 指定监听事件类别; 参数三设置共享数据
 * @see SocketChannel#read
 * @see SocketChannel#write
 * @see SocketChannel#close()
 */
public class BasicBuffer {

    public static void main(String[] args) {

        //举例说明Buffer 的使用 (简单说明)
        //创建一个Buffer, 大小为 5, 即可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer 存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }

        //如何从buffer读取数据
        //将buffer转换，读写切换(!!!)
        /*
        // 源码
        public final Buffer flip() {
            limit = position; // 读数据不能超过 position 的 切换之前的值
            position = 0; // 切换之后, position 指针位置 设置到 0
            mark = -1;
            return this;
        }
         */
        Object array = intBuffer.flip().array();
        System.out.println(Arrays.toString((int[]) array));
        //intBuffer.flip();
        intBuffer.position(1); // 移动指针到 position 1(从 0 开始)
        System.out.println(intBuffer.get()); // 获取一次, 移动一次 position
        intBuffer.limit(3); // 限制数据的前几个(从 position 算起)
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
        //System.out.println("intBuffer.get() = " + intBuffer.get()); // java.nio.BufferUnderflowException
    }

}