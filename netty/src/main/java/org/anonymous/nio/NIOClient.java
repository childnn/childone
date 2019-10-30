package org.anonymous.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static org.anonymous.bio.BIOServer.HOST;
import static org.anonymous.bio.BIOServer.PORT;

public class NIOClient {

    public static void main(String[] args) throws Exception {
        // 得到一个网络通道
        SocketChannel client = SocketChannel.open();
        // 设置非阻塞
        client.configureBlocking(false);
        // 提供服务器端的ip 和 端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
        // 理解一下两个方法
        // 连接服务器
        if (!client.connect(inetSocketAddress)) {
            while (!client.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作..");
            }
        }

        // ...如果连接成功，就发送数据
        String str = "hello, NIO~~";
        // Wraps a byte array into a buffer
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // 发送数据，将 buffer 数据写入 channel
        client.write(buffer);
        System.in.read();
    }
}
