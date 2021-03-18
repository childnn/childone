package org.anonymous.netty.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/5 11:06
 * 引导服务器. 使用 {@link org.anonymous.netty.echo.EchoServerHandler} 实现的业务逻辑
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " <port>");
            return;
        }

        final int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    private void start() throws InterruptedException {
        // 处理事件, 接受新的连接, read/write 数据.
        final NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            // 引导服务器, 并随后绑定至指定的 Address
            final ServerBootstrap b = new ServerBootstrap();
            b.group(group) // 使用 NIO 传输, 指定 NioEventLoopGroup 接受和处理新连接.
                    .channel(NioServerSocketChannel.class) // 指定使用 IO 的 Channel: 使用 NioServerSocketChannel 作为信道类型.
                    .localAddress(new InetSocketAddress(port)) //
                    /*
                     * io.netty.channel.ChannelInitializer 继承自 io.netty.channel.ChannelInboundHandlerAdapter
                     * 当一个新的连接被接受, 一个新的子 Channel 将被创建, ChannelInitializer 会添加
                     * 自定义的 org.anonymous.netty.echo.EchoServerHandler 实例到 Channel 的 ChannelPipeline.
                     * 如果由入站信息, 这个处理器将被通知.
                     * */
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 添加 EchoServerHandler 到 Channel 的 ChannelPipeline
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            final ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new EchoServerHandler());
                        }
                    });
            // 绑定到服务器, sync 等带服务器关闭
            final ChannelFuture channelFuture = b.bind(); // 绑定到上面指定的 InetSocketAddress
            // 调用 sync() 的原因是当前线程阻塞
            final ChannelFuture f = channelFuture.sync();

            final Channel channel = f.channel();
            System.out.println(EchoServer.class.getName() + " started and listen on " + channel.localAddress());

            // 关闭 channel 和块, 直到它被关闭.
            final ChannelFuture cf = channel.closeFuture();
            cf.sync(); // 等待.
        } finally {
            final Future<?> future = group.shutdownGracefully();

            future.sync();
        }
    }

}
