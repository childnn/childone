package org.anonymous.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.ImmediateEventExecutor;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 10:53
 * todo: 此示例启动成功后, 访问 http://localhost:port 无法正常访问,
 * io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
 * 暂不清楚原因为何?
 */
public class ChatServer {

    // 1. 创建 DefaultChannelGroup 用来保存所有连接的 WebSocket channel
    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group = new NioEventLoopGroup();
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address) {
        // 2. 引导服务器
        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(channelGroup));

        final ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        channel = future.channel();

        return future;
    }

    // 3. 创建 ChannelInitializer
    protected ChannelHandler createInitializer(ChannelGroup channelGroup) {
        return new NettyPreview.ChatServerInitializer(channelGroup);
    }

    // 4. close, 释放资源.
    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }

    // 如果 此 main 方法不抛出异常, 子类 SecureChatServer 无法正常编译
    // static 方法并非重写, 为何必须在父类抛出异常?
    public static void main(String[] args) throws SSLException, CertificateException {
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }
        final int port = Integer.parseInt(args[0]);

        System.out.println("Server start on port: " + port);

        final ChatServer endPoint = new ChatServer();
        final ChannelFuture future = endPoint.start(new InetSocketAddress(port));

        // super.run();
        Runtime.getRuntime().addShutdownHook(new Thread(endPoint::destroy));

        future.channel().closeFuture().syncUninterruptibly();

    }

}

class SecureChatServer extends ChatServer {

    private final SslContext context;


    SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    protected ChannelHandler createInitializer(ChannelGroup channelGroup) {
        // return super.createInitializer(channelGroup);
        // 启用加密.
        return new NettyPreview.SecureChatServerInitializer(channelGroup, context);
    }

    public static void main(String[] args) throws CertificateException, SSLException {
        if (args.length != 1) {
            System.err.println("Please give port as argument");
            System.exit(1);
        }

        final int port = Integer.parseInt(args[0]);
        final SelfSignedCertificate cert = new SelfSignedCertificate();
        final SslContext context = SslContextBuilder.forServer(cert.certificate(), cert.privateKey()).build();
        final SecureChatServer endpoint = new SecureChatServer(context);
        final ChannelFuture future = endpoint.start(new InetSocketAddress(port));

        Runtime.getRuntime().addShutdownHook(new Thread(endpoint::destroy));

        future.channel().closeFuture().syncUninterruptibly();
    }

}