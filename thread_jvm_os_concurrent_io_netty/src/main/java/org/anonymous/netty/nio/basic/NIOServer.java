package org.anonymous.netty.nio.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static org.anonymous.netty.bio.BIOServer.PORT;

public class NIOServer {

    /**
     * Java7 以前, 要让 ServerSocketChannel 监听指定端口, 必须先调用 {@link java.nio.channels.ServerSocketChannel#socket()}
     * 获取它关联的 {@link java.net.ServerSocket}, 在调用 {@link java.net.ServerSocket#bind} 方法监听指定端口
     * Java7 新增 {@link java.nio.channels.ServerSocketChannel#bind} 直接监听.
     */
    static ServerSocketChannel serverSocketChannelB4Java7(InetSocketAddress addr) throws IOException {
        final ServerSocketChannel server = ServerSocketChannel.open();
        final ServerSocket socket = server.socket();
        socket.bind(addr);

        return server;
    }

    static ServerSocketChannel serverSocketChannelAfterJava7(InetSocketAddress addr) throws IOException {
        final ServerSocketChannel server = ServerSocketChannel.open();

        // 以下两种方式返回完全等价.
        return server.bind(addr); // 此方法返回的就是 this server.
        // return server;
    }

    public static void main(String[] args) throws IOException {
        /*// 创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 绑定一个端口6666, 在服务器端监听
        ServerSocket ss = serverChannel.socket();
        ss.bind(new InetSocketAddress(PORT));*/

        final InetSocketAddress addr = new InetSocketAddress(PORT);
        final ServerSocketChannel serverChannel = serverSocketChannelAfterJava7(addr);

        // 设置为非阻塞
        serverChannel.configureBlocking(false);

        // 得到一个Selector对象
        Selector selector = Selector.open();

        // 把 serverChannel 注册到  selector 关心 事件为 OP_ACCEPT
        // java.nio.channels.ServerSocketChannel 只支持 accept 事件
        // 返回的 selection-key 就代表 此 sever-channel 在该 selector 上的注册关系.
        final SelectionKey serverKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 这里得到的一个 selection-key 就是指 此 server-channel 与 此 selector 的注册关系.
        System.out.println("注册后的 selectionKey 数量 = " + selector.keys().size()); // 1 -- 服务端
        System.out.println("selector.keys().contains(serverKey) = " + selector.keys().contains(serverKey)); // true


        // 循环等待客户端连接
        while (true) {
            // 阻塞方法
            // 这里我们等待3秒，如果没有事件发生, 返回
            // 可以使用有参或无参的方法
            /* if (selector.select(*//*3000*//*) == 0) { // 没有事件发生
                System.out.println("服务器等待了3秒，无连接");
                continue;
            }*/
            try {
                // 类似 socket.accept(); 监控所有注册的 Channel, 当有需要处理的 IO 操作时, 返回,
                // 并将对应的 SelectionKey 加入被选择的 SelectionKey 集合中, 该方法返回这些 Channel 的数量.
                selector.select();
            } catch (IOException e) {
                System.out.println("监听出错: " + e);
                break;
            }

            // 如果返回的>0, 就获取到相关的 selectionKey集合
            // 1.如果返回的>0， 表示已经获取到关注的事件
            // 2. selector.selectedKeys() 返回关注事件的集合 // selector.keys() 返回所有注册的 key
            // 通过 readyKeys 反向获取通道
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            System.out.println("readyKeys 数量 = " + readyKeys.size());

            for (SelectionKey key : readyKeys) {
                // 移除当前 key, 防止重复操作(多线程)
                readyKeys.remove(key);
                try {
                    // 根据key 对应的通道发生的事件做相应处理
                    if (key.isAcceptable()) { // 如果是 OP_ACCEPT, 有新的客户端连接
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        // 该客户端生成一个 SocketChannel
                        SocketChannel client = server.accept();
                        System.out.println("客户端连接成功 生成了一个 client " + client);
                        // 将  SocketChannel 设置为非阻塞
                        client.configureBlocking(false);
                        // 将socketChannel 注册到selector, 关注事件为 OP_READ， 同时给 client 关联一个Buffer
                        client.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        System.out.println("客户端连接后 ，注册的 selectionKey 数量 = " + selector.keys().size()); // 2,3,4..
                    }

                    if (key.isReadable()) {  //发生 OP_READ
                        // 通过key 反向获取到对应channel
                        SocketChannel client = (SocketChannel) key.channel();
                        // 获取到该channel关联的buffer
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        client.read(buffer);
                        System.err.println("form 客户端 " + new String(buffer.array(), StandardCharsets.UTF_8));
                    }

                    //if (key.isWritable()) {                //8
                    //    SocketChannel client = (SocketChannel)key.channel();
                    //    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    //    while (buffer.hasRemaining()) {
                    //        if (client.write(buffer) == 0) {        //9
                    //            break;
                    //        }
                    //    }
                    //    client.close();                    //10
                    //}

                } catch (IOException e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ex) {
                        // ignore
                    }
                }
            }

          /*  //遍历 Set<SelectionKey>, 使用迭代器遍历
            Iterator<SelectionKey> keyIterator = readyKeys.iterator();
            while (keyIterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey key = keyIterator.next();
                //根据key 对应的通道发生的事件做相应处理
                if (key.isAcceptable()) { //如果是 OP_ACCEPT, 有新的客户端连接
                    //该该客户端生成一个 SocketChannel
                    SocketChannel socketChannel = serverChannel.accept();
                    System.out.println("客户端连接成功 生成了一个 socketChannel " + socketChannel.hashCode());
                    //将  SocketChannel 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel 注册到selector, 关注事件为 OP_READ， 同时给socketChannel
                    //关联一个Buffer
                    socketChannel.serverKey(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接后 ，注册的selectionkey 数量=" + selector.keys().size()); //2,3,4..
                }
                if (key.isReadable()) {  //发生 OP_READ
                    //通过key 反向获取到对应channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("form 客户端 " + new String(buffer.array()));

                }
                //手动从集合中移动当前的selectionKey, 防止重复操作
                keyIterator.remove();
            }
*/
        }

    }
}
