package org.anonymous.netty.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/5 13:55
 */
public class EchoClient {

    private final String host;

    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    void start() throws InterruptedException {
        // 管理客户端事件.
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            final Bootstrap b = new Bootstrap();
            b.group(group) //
                    .channel(NioSocketChannel.class) // nio-channel
                    .remoteAddress(new InetSocketAddress(host, port)) // 服务器的 InetSocketAddress
                    // 建立一个连接和一个新的通道时, 创建添加到 EchoClientHandler 实例到 ChannelPipeline
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            // 连接到远程, 等待连接完成
            final ChannelFuture f = b.connect().sync();

            final Channel channel = f.channel();
            System.out.println("连接到服务器: " + channel.localAddress());
            // 阻塞直到 Channel 关闭.
            channel.closeFuture().sync();

        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.err.println("Usage: " + EchoClient.class.getSimpleName() + "<host><port>");
            return;
        }

        final String host = args[0];
        final int port = Integer.parseInt(args[1]);

        new EchoClient(host, port).start();
    }

}
