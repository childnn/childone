package org.anonymous.netty.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import static org.anonymous.netty.udp.UDPServer.DATA_LEN;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/29 19:37
 */
public class UDPClient {


    byte[] inBuf = new byte[DATA_LEN];

    private DatagramPacket outPacket;
    //
    private DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);

    void init() throws IOException {
        // 创建客户端 udp-socket, 使用随机端口
        final DatagramSocket socket = new DatagramSocket();
        // 初始化发送用的 packet, 包含一个空数组
        outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getLocalHost(), UDPServer.PORT);

        final Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            final byte[] buf = sc.nextLine().getBytes();

            outPacket.setData(buf);

            socket.send(outPacket);

            socket.receive(inPacket);

            System.out.println("receive msg from server: " + new String(inBuf));
        }
    }

    public static void main(String[] args) throws IOException {
        new UDPClient().init();
    }

}
