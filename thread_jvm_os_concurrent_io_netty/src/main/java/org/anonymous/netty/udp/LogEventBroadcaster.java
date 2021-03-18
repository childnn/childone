package org.anonymous.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 14:55
 * UDP 广播.
 * 文件必须真实存在
 * @see org.anonymous.netty.udp.LogEventMonitor
 */
public class LogEventBroadcaster {

    private final Bootstrap bootstrap;
    private final File file;
    private final EventLoopGroup group;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        // 1. 引导 NioDatagramChannel, 使用 SO_BROADCAST 选项
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));

        this.file = file;
        System.out.println(file);
    }

    public void run() throws IOException {
        // 2. 绑定管道. 注意当使用 Datagram Channel 时, 是无连接的.
        final Channel ch = bootstrap.bind(0).syncUninterruptibly().channel();
        System.out.println("LogEventBroadcaster running");

        long pointer = 0;
        for (; ; ) {
            long len = file.length();
            if (len < pointer) {
                // file was reset
                // 3. 如果需要, 可以设置文件的指针指向文件的最后字节
                pointer = len;
            } else if (len > pointer) {
                // Content was added
                final RandomAccessFile raf = new RandomAccessFile(this.file, "r");
                // 4. 设置当前文件的指针, 这样不会把旧的发出去
                raf.seek(pointer);
                String line;
                while (null != (line = raf.readLine())) {
                    // 5. 写一个 LogEvent 到管道用于保存文件名和文件实体
                    // 我们期望每个日志实体是一行长度
                    ch.writeAndFlush(new LogEvent(null, -1, file.getAbsolutePath(), line));
                }
                // 6. 存储当前文件的位置, 这样, 可以稍后继续
                pointer = raf.getFilePointer();
                raf.close();
            }

            try {
                // 7. sleep 1s, 如果其他中断退出循环就重启它.
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // e.printStackTrace();
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.asList(args));
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        // 8. 构造新的实例 LogEventBroadcaster 并启动它.
        final LogEventBroadcaster broadcaster = new LogEventBroadcaster(
                new InetSocketAddress("255.255.255.255", Integer.parseInt(args[0]))
                , new File(args[1]));
        try {
            broadcaster.run();
        } finally {
            broadcaster.stop();
        }

    }

}
