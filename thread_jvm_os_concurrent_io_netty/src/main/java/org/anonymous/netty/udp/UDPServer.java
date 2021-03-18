package org.anonymous.netty.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/29 19:27
 * UDP: 客户端, 服务端并没有严格区分, 不需建立连接.
 * @see java.net.DatagramSocket 连接对象: 只负责传递数据
 * @see java.net.DatagramPacket 数据对象: 包含数据和数据来源 host:port
 * todo: 有点问题, 查看收发数据清空.
 */
public class UDPServer {

    static final int PORT = 9999;

    static final int DATA_LEN = 4096;

    byte[] inBuf = new byte[DATA_LEN];

    DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);

    DatagramPacket outPacket;

    List<String> books = Arrays.asList("java", "python", "c++");

    void init() throws IOException {
        final DatagramSocket socket = new DatagramSocket(PORT);
        for (int i = 0; i < 1000; i++) {
            // 将 socket 中的数据读到 packet 中
            socket.receive(inPacket);
            System.out.println("接收的数据与指定数组是否为同一个: " + (inBuf == inPacket.getData()));
            System.out.println("packet: " + new String(inBuf));

            final byte[] sendData = books.get(i % 3).getBytes();

            outPacket = new DatagramPacket(sendData, sendData.length, inPacket.getSocketAddress());

            // 发送数据
            socket.send(outPacket);
        }
    }

    public static void main(String[] args) throws IOException {
        new UDPServer().init();
    }

}
