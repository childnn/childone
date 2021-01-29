package org.anonymous.netty.aiojava7.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.anonymous.netty.aiojava7.chat.AIOChatServer.UTF8;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/29 15:57
 */
public class AIOChatServer {

    static final int PORT = 30000;
    static final Charset UTF8 = StandardCharsets.UTF_8;
    List<AsynchronousSocketChannel> channels = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new AIOChatServer().startListen();

        System.in.read();
    }

    void startListen() throws IOException {
        // 1. open
        final ExecutorService pool = Executors.newFixedThreadPool(10);
        // channel group: org.anonymous.netty.aiojava7.AIOMain 笔记说明
        final AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup.withThreadPool(pool);
        final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(channelGroup);
        // 2. bind
        server.bind(new InetSocketAddress(PORT));
        // 3. accept
        server.accept("我是 Server", new AcceptHandler(server, channels));
    }
}

class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {

    private final AsynchronousServerSocketChannel server;
    private final List<AsynchronousSocketChannel> channels;

    public AcceptHandler(AsynchronousServerSocketChannel server, List<AsynchronousSocketChannel> channels) {
        this.server = server;
        this.channels = channels;
    }

    // 两个 CompletionHandler: 一个 this, 一个 read-CompletionHandler
    // IO 操作完成时触发
    @Override
    public void completed(AsynchronousSocketChannel client, Object attachment) {
        channels.add(client);
        // 准备接受客户端的下一次连接
        server.accept("server? ", this);

        final ByteBuffer buf = ByteBuffer.allocate(1024);

        // 将此 client 发送的信息读出, 并群发到其他注册的 client-channel
        client.read(buf, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer i, Object attachment) {

                buf.flip(); // 上面是向 buf 中写入, 下面需要读, 所以需要 flip

                final String content = UTF8.decode(buf).toString();

                // 群发
                channels.forEach(c -> {
                    try {
                        c.write(ByteBuffer.wrap(content.getBytes(UTF8))).get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                });

                buf.clear();

                // 读取下一次数据
                client.read(buf, "client? ", this);
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("读取数据失败: " + exc);
                // 删除该 channel
                channels.remove(client);
            }
        });
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        System.out.println("连接失败: " + exc);
    }

}