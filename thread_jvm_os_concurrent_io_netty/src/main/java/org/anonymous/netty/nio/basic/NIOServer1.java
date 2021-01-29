package org.anonymous.netty.nio.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.anonymous.netty.bio.BIOServer.PORT;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/28 14:53
 * <p>
 * selector--(selection-key)--Channel
 * 一个 Selector 注册多个 Channel, 每个 Channel 分别通过一个 SelectionKey 与 Selector 关联.
 */
public class NIOServer1 {

    static Charset UTF8 = StandardCharsets.UTF_8;
    private Selector selector;

    public static void main(String[] args) throws IOException {
        new NIOServer1().init();
    }

    void init() throws IOException {
        selector = Selector.open(); // not null?

        ServerSocketChannel server = getServer();

        // 这个唯一的 SelectionKey 就是 server-channel 与 selector 的注册关系
        System.out.println("server 注册完毕之后, selector 上注册的 selection-keys: " + selector.keys());

        // 监听: 等待客户端的 IO 操作, 这里返回的值肯定不含 server-channel 本身.
        while (selector.select() > 0) {
            System.out.println("有 channel 接入了!");
            // 得到所有被选中的孩子们
            final Set<SelectionKey> selectedKeys = selector.selectedKeys();

            // 此测试中接入到 此 selector 的 Channel 都是 java.nio.channels.SocketChannel
            // 参见: java.nio.channels.SocketChannel.validOps, 查看支持的操作.
            // 在实际应用中, 接入的 Channel 不一定都支持 accept/read/write 操作, 所以下面的每一步都需要判断
            selectedKeys.forEach(sk -> {
                // 从 selector 上的 selected-keys 中删除正在处理的 SelectionKey
                // 这里的 remove 只是删除集合中的一个元素, 并不是把该 SK 从 Selector 上解除注册
                selectedKeys.remove(sk);
                // 如果是当前 Channel 关注的操作类别
                if (sk.readyOps() == server.validOps()) { // sk.isAcceptable()
                    accept(server, sk);
                }

                // 如果 sk 对应的 Channel 有数据可以读取
                if (sk.isReadable()) {
                    readAndWrite(sk);
                }
            });
        }
    }

    // 当客户端关闭时, 服务器对应的 Channel 会跑异常, 如果不捕获, 则导致当前线程退出
    // 除非针对每一个 client-channel(selection-key) 都新开一个独立的线程(实际情况肯定不现实)
    private void readAndWrite(SelectionKey sk) {
        // 获取该 SelectionKey 对应的 Channel, 该 Channel 中有可读的数据
        final SocketChannel sc = (SocketChannel) sk.channel();
        // 定义准备执行读取数据的 ByteBuffer
        final ByteBuffer buf = ByteBuffer.allocate(1024);

        // 读取数据
        StringBuilder content = new StringBuilder();
        try {
            while (sc.read(buf) > 0) {
                buf.flip();
                content.append(UTF8.decode(buf));
            }
            // 打印从该 sk 对应的 Channel 里读取到的数据
            System.out.println("读取的数据: " + content);
            // 将 sk 对应的 Channel 设置成准备下一次读取
            // todo: 同上, 为何要重新设置?
            sk.interestOps(SelectionKey.OP_READ);
        } catch (IOException e) {
            sk.cancel();
            try {
                sc.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            System.out.printf("读取数据异常, 关闭该 SelectionKey [%s] 对应的 Channel [%s]%n", sk, sc);
            e.printStackTrace();
        }

        // 如果 content 的长度大于 0, 即聊天信息不为空
        if (content.length() > 0) {
            // 获取该 selector 上注册的所有 SelectionKey
            final Set<SelectionKey> keys = selector.keys();
            //
            keys.forEach(k -> {
                // 获取该 key 对应的 Channel
                final SelectableChannel targetChannel = k.channel();
                // 如果该 Channel 是 SocketChannel 对象, 不是当前的 SK(发消息者自己收不到自己的消息)
                if (targetChannel instanceof SocketChannel/* && k != sk*/) {
                    // 将读到的内容写入该 Channel 中
                    final SocketChannel dest = (SocketChannel) targetChannel;
                    try {
                        dest.write(ByteBuffer.wrap(content.toString().getBytes()));
                        dest.write(UTF8.encode(content.toString()));
                    } catch (IOException e) {
                        System.out.printf("写出到 key [%s]--channel [%s] 异常%n", k, dest);
                        e.printStackTrace();
                    }
                }
            });

            // 一次输出结束.
            System.out.println("========================================");
        }
    }

    private void accept(ServerSocketChannel server, SelectionKey sk) {
        // 调用 accept 方法接受连接请求, 得到服务端的 SocketChannel (类似 java.net.ServerSocket.accept())
        try {
            // todo: 如果是 non-blocking server-channel, 则返回 null?
            // 参见源码解释:
            final SocketChannel sc = server.accept();
            System.out.println("客户端连接成功 生成了一个 client " + sc);
            sc.configureBlocking(false); // non-blocking 模式
            sc.register(selector, SelectionKey.OP_READ); // 此 channel 关注 read 操作

            // 校验此 SK 的 operation-set 值, 下面的设置好像没有必要,
            // 这个方法本身就是处理 OP_ACCEPT
            final int interestOps = sk.interestOps();
            System.out.println("interestOps = " + interestOps); // 输出就是 OP_ACCEPT.
            // 将当前 SelectionKey 对应的 Channel 设置成准备接受其他请求
            // todo: 需要重新设置吗?
            sk.interestOps(server.validOps()); // accept

        } catch (IOException e) {
            System.out.println("服务端接受");
            e.printStackTrace();
        }
    }

    private ServerSocketChannel getServer() throws IOException {
        final ServerSocketChannel server = ServerSocketChannel.open();
        final InetSocketAddress addr = new InetSocketAddress(PORT); // 可以指定 IP
        server.bind(addr); // 绑定到指定 IP:port
        server.configureBlocking(false); // non-blocking
        final SelectionKey register = server.register(selector, server.validOps());// 将 server-channel 注册到 selector 上, 指定 server 支持的操作集

        System.out.printf("server-channel 注册到 selector 上, server-SelectionKey 为: [%s]%n", register);

        return server;
    }


}
