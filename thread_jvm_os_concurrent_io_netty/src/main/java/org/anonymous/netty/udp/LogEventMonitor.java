package org.anonymous.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 16:08
 * 监视器: 用来接收事件的程序
 * 1. 接收 LogEventBroadcaster 广播的 UDP DatagramPacket
 * 2. 解码 LogEvent 消息
 * 3. 输出 LogEvent 消息
 * @see org.anonymous.netty.udp.LogEventBroadcaster
 * 先启动监听器, 再启动广播.
 * todo: 输出有乱码, 暂未解决.
 */
public class LogEventMonitor {

    private final Bootstrap bootstrap;
    private final EventLoopGroup group;

    public LogEventMonitor(InetSocketAddress address) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        // 1. 引导 NioDatagramChannel, 设置 SO_BROADCAST-socket 选项
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline()
                                // 2. 添加 ChannelHandler 到 ChannelPipeline
                                .addLast(new LogEventDecoder(),
                                        new LogEventHandler());
                    }
                }).localAddress(address);
    }

    public Channel bind() {
        // 3. 绑定的通道. 注意使用 DatagramChannel 是无连接的.
        return bootstrap.bind().syncUninterruptibly().channel();
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: LogEventMonitor <port>");
        }
        // 4. 构建一个新的 LogEventMonitor
        final LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(Integer.parseInt(args[0])));

        try {
            final Channel channel = monitor.bind();
            System.out.println("LogEventMonitor running");

            channel.closeFuture().await();
        } finally {
            monitor.stop();
        }

    }

}
