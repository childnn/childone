package org.anonymous.netty.nio;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.nio.channels.Selector 它是 {@link java.nio.channels.SelectableChannel}
 * 对象的多路复用器(A multiplexor of {@link java.nio.channels.SelectableChannel} objects.),
 * 所有希望采用非阻塞方式进行通信的 Channel 都应该注册到 Selector 对象. 可以通过调用 {@link java.nio.channels.Selector#open()}
 * 方法来创建 Selector 实例, 该方法将使用系统默认的 Selector 来返回新的 Selector.
 * Selectror 可以同时监控多个 SelectabelChannel 的 IO 状况, 是非阻塞 IO 的核心. 一个 Selector 实例有三个 {@link java.nio.channels.SelectionKey} 集合
 * 1. <li><p> The <i>key set</i> contains the keys representing the current
 * channel registrations of this selector.  This set is returned by the
 * {@link java.nio.channels.Selector#keys() keys} method. </p></li>
 * 2. <li><p> The <i>selected-key set</i> is the set of keys such that each
 * key's channel was detected to be ready for at least one of the operations
 * identified in the key's interest set during a prior selection operation.
 * This set is returned by the {@link java.nio.channels.Selector#selectedKeys() selectedKeys} method.
 * The selected-key set is always a subset of the key set. </p></li>
 * 3. <li><p> The <i>cancelled-key</i> set is the set of keys that have been
 * cancelled but whose channels have not yet been deregistered.  This set is
 * not directly accessible.  The cancelled-key set is always a subset of the
 * key set. </p></li>
 * @see java.nio.channels.Selector#select() (相当于 socket.accept() 监听) This method performs a blocking selection
 * operation.  It returns only after at least one channel is selected,
 * this selector's {@link java.nio.channels.Selector#wakeup wakeup} method is invoked, or the current
 * thread is interrupted, whichever comes first.
 * 当 Selector 上注册的所有 Channel 都没有需要处理的 IO 操作时, select() 方法将被阻塞, 调用该方法的线程被阻塞.
 * @see java.nio.channels.Selector#select(long) 参见源码文档
 * @see java.nio.channels.Selector#selectNow()  <p> This method performs a non-blocking <a href="#selop">selection
 * operation</a>.  If no channels have become selectable since the previous
 * selection operation then this method immediately returns zero.
 * <p> Invoking this method clears the effect of any previous invocations
 * of the {@link java.nio.channels.Selector#wakeup wakeup} method.  </p>
 * @see java.nio.channels.Selector#wakeup() 使一个还未返回的 select() 方法立即返回.
 * ---
 * @see java.nio.channels.SelectableChannel 参见源码文档: 很详细, 也很简洁. 一定要看
 * 1. 默认阻塞, 可设置非阻塞 {@link java.nio.channels.SelectableChannel#configureBlocking(boolean)} false 表示非阻塞
 * 2. 可注册到 Selector 上, 这种注册关系由一个特定的 SelectionKey 表示 {@link java.nio.channels.SelectableChannel#register}
 * 3. Channel 注册之前必须设置为 非阻塞.
 * 不同的 Channel 支持不同的操作类别, 可查看对应实现类的 {@link java.nio.channels.ServerSocketChannel#validOps()} 方法
 * Operation-set 可选值:
 * @see java.nio.channels.SelectionKey#OP_READ
 * @see java.nio.channels.SelectionKey#OP_WRITE
 * @see java.nio.channels.SelectionKey#OP_CONNECT
 * @see java.nio.channels.SelectionKey#OP_ACCEPT
 * ---
 * 一个 Selector 上可以注册多个 Channel, 一个 Channel 可以注册到多个 Selector 上.
 * @see java.nio.channels.SelectableChannel#isRegistered() this 是否已注册到一个或多个 Selector 上.
 * @see java.nio.channels.SelectableChannel#keyFor(java.nio.channels.Selector) this 是否注册到参数指定的 Selector 上.
 * @see java.nio.channels.SelectableChannel#isBlocking()
 * ---
 * @see java.nio.channels.SelectionKey Channel 与 Selector 之间的注册关系
 * ---
 * @see java.nio.channels.ServerSocketChannel#accept() 对应 {@link java.net.ServerSocket}
 * 只支持 {@link java.nio.channels.SelectionKey#OP_ACCEPT} 参见 {@link java.nio.channels.ServerSocketChannel#validOps()}
 * @see java.nio.channels.SocketChannel 对应 {@link java.net.Socket}
 * 支持操作参见 {@link java.nio.channels.SocketChannel#validOps()}
 * 实现 ByteChannel, ScatteringByteChannel, GatheringByteChannel 可以直接通过 SocketChannel 来读写 ByteBuffer 对象.
 * @since 2021/1/27 10:23
 */
public class NIOMain {


}
