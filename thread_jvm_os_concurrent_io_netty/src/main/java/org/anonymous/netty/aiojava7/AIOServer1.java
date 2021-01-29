package org.anonymous.netty.aiojava7;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.anonymous.netty.bio.BIOServer.PORT;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/29 12:06
 */
public class AIOServer1 {

    public static void main(String[] args) throws IOException {
        final AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(Executors.newFixedThreadPool(8));
        final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(group);

        // 返回值接不接受无所谓
        server.bind(new InetSocketAddress(PORT)); // return this

        // 循环接受客户端连接
        while (true) {
            final Future<AsynchronousSocketChannel> future = server.accept();

            // 获取连接完成后返回的 AsynchronousSocketChannel
            try {
                final AsynchronousSocketChannel client = future.get();
                // returns the number of bytes written.
                final Integer integer = client.write(ByteBuffer.wrap("欢迎来到 AIO's world!".getBytes(StandardCharsets.UTF_8))).get();
            } catch (InterruptedException e) {
                System.out.println("Future#get() 异常:" + e);
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.print("获取 Client Socket Channel/Future#get() 异常: " + e);
                e.printStackTrace();
            }

        }

    }

}
