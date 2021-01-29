package org.anonymous.netty.aiojava7;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.nio.channels.AsynchronousChannel AIO 顶层接口 since 1.7
 * NIO 的顶层接口 {@link java.nio.channels.InterruptibleChannel}, 但直接相关的接口为 {@link java.nio.channels.SelectableChannel}
 * TCP:
 * @see java.nio.channels.AsynchronousSocketChannel
 * @see java.nio.channels.AsynchronousServerSocketChannel
 * @see java.nio.channels.AsynchronousChannelGroup A grouping of asynchronous channels for the purpose of resource sharing.
 * 注: 查看官方文档, 很详细.
 * 异步 Channel 分组管理器, 可以实现资源共享.
 * 创建时传入一个 {@link java.util.concurrent.ExecutorService}, 绑定线程池
 * 该线程池负责两个任务: 处理 IO 事件和触发 {@link java.nio.channels.CompletionHandler}
 * termination of the group results in the shutdown of the associated thread pool.
 * Asynchronous channels that do not specify a group at construction time are bound to the default group.
 * The default group has an associated thread pool that creates new threads as needed.
 * @see java.nio.channels.AsynchronousServerSocketChannel#accept() 接受客户端请求. 返回 Future<AsynchronousSocketChannel>,
 * Future 的 get() 方法即为连接成功后的 AsynchronousSocketChannel, 但 get() 方法在返回之前会阻塞.
 * 因此, 此 accept() 方法会阻塞当前线程
 * 正真的异步:
 * 如上述, 程序发起 IO 请求, 实际的 IO 操作有操作系统完成, 当前程序不知道该 异步 IO 什么时候完成, 因此, 以下的 accept() 方法不会阻塞.
 * 这是回调存在的意义, 用来监听/等待 IO 操作的完成.
 * @see java.nio.channels.AsynchronousServerSocketChannel#accept(Object, java.nio.channels.CompletionHandler)
 * 参数一: The object to attach to the I/O operation; can be {@code null}
 * 参数二: The handler for consuming the result. 回调
 * 成功或失败都会触发 CompleitonHandler, 其第一个泛型参数就是指连接成功之后的 客户端 {@link java.nio.channels.AsynchronousSocketChannel}
 * @see java.nio.channels.CompletionHandler#completed(Object, Object) 成功时执行
 * @see java.nio.channels.CompletionHandler#failed(Throwable, Object) 失败时执行
 * 同理:
 * @see java.nio.channels.AsynchronousSocketChannel#read
 * @see java.nio.channels.AsynchronousSocketChannel#write
 * 以上的 read/write 都有两种版本, 即是否使用 {@link java.nio.channels.CompletionHandler} 作为回调.
 * ---
 * 使用 {@link java.nio.channels.AsynchronousServerSocketChannel} 的基本三步:
 * 1. open
 * 2. bind
 * 3. accpet
 * @since 2021/1/29 10:35
 * Java7 NIO.2 引入 AIO, 即 Asynchronous IO, 异步
 * 注: 如果按 POSIX 的标准来划分 IO, 可以把 IO 分为两类: 同步 IO 和异步 IO.
 * 对于 IO 操作可以分成两步:
 * 1. 程序发出 IO 请求,
 * 2. 完成实际的 IO 操作
 * 区分阻塞/非阻塞都是针对第一步: 如果发出的 IO 请求会阻塞线程, 就是阻塞 IO; 如果发出 IO 请求没有阻塞线程, 就是非阻塞 IO.
 * 但同步/异步 IO 的区别在第二步: 如果实际的 IO 操作有操作系统完成, 再将结果返回给应用程序, 就是异步 IO;
 * 如果实际的 IO 需要应用程序本身去执行, 会阻塞线程, 就是同步 IO.
 * BIO, NIO 的非阻塞形式 都属于 同步 IO!!!
 */
public class AIOMain {


}
