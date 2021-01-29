package org.anonymous.netty.aiojava7;

import org.anonymous.netty.bio.BIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.anonymous.netty.bio.BIOServer.PORT;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.nio.channels.AsynchronousSocketChannel
 * 1. open
 * 2. connect
 * 3. read/write: 两个版本
 * @since 2021/1/29 14:55
 */
public class AIOClient1 {

    static final InetSocketAddress SERVER_HOST = new InetSocketAddress(BIOServer.HOST, PORT);

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

        // blockRead(client);
        nonBlock(client);

    }

    private static void nonBlock(AsynchronousSocketChannel client) {
        client.connect(SERVER_HOST, "21312321", new CompletionHandler<Void, String>() {
            @Override
            public void completed(Void result, String attachment) {
                System.out.println("result = " + result);
                System.out.println("attachment = " + attachment);
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                throw new RuntimeException(exc);
            }
        });

        final ByteBuffer buf = ByteBuffer.allocate(1024);

        client.read(buf, "fdsfdsfsdfsdf", new CompletionHandler<Integer, String>() {
            @Override
            public void completed(Integer result, String attachment) {
                buf.flip(); // todo: ?
                System.out.println("read: " + StandardCharsets.UTF_8.decode(buf).toString());
                System.out.println("attachment = " + attachment);
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                throw new RuntimeException(exc);
            }
        });
    }


    private static void blockRead(AsynchronousSocketChannel client) throws InterruptedException, ExecutionException {
        final Future<Void> future = client.connect(SERVER_HOST);

        // read-buf
        final ByteBuffer buf = ByteBuffer.allocate(1024);

        future.get(); // 阻塞 等待结果
        // if (future.isDone()) { // 不能使用 if, 否则 未结束则直接跳过, 不会有输出
        final Future<Integer> read = client.read(buf);

        read.get(); // 阻塞
        // if (read.isDone()) {
        buf.flip();
        System.out.println("接受服务器信息: " + StandardCharsets.UTF_8.decode(buf).toString());
        // }
        // }
    }

}
