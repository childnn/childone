package org.anonymous.netty.nio.basic;

import org.anonymous.netty.bio.BIOServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;

import static org.anonymous.netty.bio.BIOServer.PORT;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/28 19:09
 */
public class NIOClient1 {

    Selector selector = null;
    SocketChannel client;

    public static void main(String[] args) throws IOException {
        new NIOClient1().init();
    }

    // 客户端可以使用多线程:
    // 一个线程负责读取用户的输入, 并将输入的内容写入所有其他的 SocketChannel 中,
    // 另一个线程则不断查询 Selector#select() 方法的返回值, 如果该方法的返回值大于 0
    // 就说明程序需要对相应的 Channel 执行的 IO 处理.
    void init() throws IOException {
        selector = Selector.open();

        client = getClient();

        new ClientThread(selector).start();

        final Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            final String line = scan.nextLine();
            client.write(NIOServer1.UTF8.encode(line));
        }

    }

    private SocketChannel getClient() throws IOException {
        // 连接到指定 host:port
        client = SocketChannel.open(new InetSocketAddress(BIOServer.HOST, PORT));
        client.configureBlocking(false); // non-blocking
        client.register(selector, SelectionKey.OP_READ); // read-operation

        return client;
    }

}

class ClientThread extends Thread {

    private final Selector selector;

    public ClientThread(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            while (selector.select() > 0) {
                // 遍历所有 selected-keys
                final Set<SelectionKey> selectedKeys = selector.selectedKeys();
                selectedKeys.forEach(k -> {
                    // 删除正在处理的 SK
                    selectedKeys.remove(k);

                    final SelectableChannel channel = k.channel();

                    if (k.isReadable() && channel instanceof SocketChannel) {
                        final SocketChannel sc = (SocketChannel) channel;
                        final ByteBuffer buf = ByteBuffer.allocate(1024);
                        StringBuilder content = new StringBuilder();
                        try {
                            while (sc.read(buf) > 0) {
                                sc.read(buf);
                                buf.flip();
                                content.append(NIOServer1.UTF8.decode(buf));
                            }
                            // 打印读取的信息
                            System.out.println("聊天内容: " + content);

                            // 为下一次读取做准备
                            k.interestOps(SelectionKey.OP_READ);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}