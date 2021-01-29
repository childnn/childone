package org.anonymous.netty.nio;

import java.nio.CharBuffer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.nio.channels.Channel
 * Channel 类似于传统的流对象, 但与传统的流对象有两个主要区别
 * 1. Channel 可以直接将指定文件的部分或全部直接映射成 Buffer
 * 2. 程序不能直接访问 Channel 中的数据, 包括读取, 写入都不行, Channel 只能与 Buffer 进行交互.
 * 也就是说, 如果要从 Channel 中取得数据, 必须先用 Buffer 从 Channel 中取出一些数据, 然后
 * 让程序从 Buffer 中取出这些数据; 如果要将程序中的数据写入 Channel, 一样先让程序将数据放入 Buffer 中,
 * 程序再将 Buffer 里的数据写入 Channel 中.
 * TCP:
 * @see java.nio.channels.SocketChannel
 * @see java.nio.channels.ServerSocketChannel
 * UDP:
 * @see java.nio.channels.DatagramChannel
 * Thread: 线程通信
 * @see java.nio.channels.Pipe.SinkChannel
 * @see java.nio.channels.Pipe.SourceChannel
 * File:
 * @see java.nio.channels.FileChannel
 * -----
 * 所有 Channel 都不应该通过构造器来直接创建, 而是通过传统的节点获取
 * {@link java.io.FileInputStream#getChannel()} {@link java.io.FileOutputStream#getChannel()}
 * {@link java.net.DatagramSocket#getChannel()}
 * {@link java.nio.channels.SocketChannel#open()} {@link java.nio.channels.SocketChannel#open(java.net.SocketAddress)}
 * {@link java.nio.channels.ServerSocketChannel#open()}
 * ---
 * Channel 常用方法:
 * @see java.nio.channels.FileChannel#map(java.nio.channels.FileChannel.MapMode, long, long)  将 Channel 对应的部分或全部数据映射为 ByteBuffer
 * 参数一: {@link java.nio.channels.FileChannel.MapMode} 映射模式
 * 参数二, 三: 用于控制将 Channel 的哪些数据映射成 ByteBuffer
 * @see java.nio.channels.FileChannel#read
 * @see java.nio.channels.FileChannel#write
 * @since 2021/1/20 18:36
 * 关于直接 buffer(DirectBuffer): 由于直接 Buffer 的创建成本很高, 所以直接 Buffer 只适用于长生存期的 buffer,
 * 而不适用于短生存期, 一次用完就丢弃的 buffer. 而且只有 ByteBuffer 才提供了 {@link java.nio.ByteBuffer#allocateDirect(int)} 方法
 * 所以只能在 ByteBuffer 级别上创建直接 Buffer. 如果希望使用其他类型, 则应该将该 Buffer 转换成其他类型的 Buffer.
 * -------------
 * 关于 Channel
 */
public class NIOChannel {

    public static void main(String[] args) {
        // 创建 Buffer
        CharBuffer buff = CharBuffer.allocate(8);

        System.out.println("capacity: " + buff.capacity());
        System.out.println("limit: " + buff.limit());
        System.out.println("position: " + buff.position());

        // --------------------------write--------------------------
        // 放入元素
        buff.put('a');
        buff.put('b');
        buff.put('c');

        System.out.println("加入三个元素后, position 值: " + buff.position());

        // flip: 翻转. 读写转换. 详情可查看文档.
        // 将 limit 移动到原 position 的位置, 限制可读空间, 避免读到 null/空(数组默认值) 值.
        buff.flip();

        // --------------------------read--------------------------
        System.out.println("flip 之后: ");
        System.out.println("position: " + buff.position());
        System.out.println("limit: " + buff.limit());

        // 取第一个元素
        System.out.println("第一个元素: " + buff.get());
        System.out.println("取出一个元素后, position: " + buff.position());

        // clear
        buff.clear();

        System.out.println("clear 之后: ");
        System.out.println("position: " + buff.position());
        System.out.println("limit: " + buff.limit());

        System.out.println("clear 后, buffer 并没有被清空. 第三个元素为(索引从 0 开始): " + buff.get(2));
        System.out.println("执行绝对读取(根据索引取值, 不会改变 position 值)后, position: " + buff.position());

        System.out.println("buff.get(7) = " + buff.get(7)); // 空字符串. ByteBuffer 默认为 0.

    }

}
