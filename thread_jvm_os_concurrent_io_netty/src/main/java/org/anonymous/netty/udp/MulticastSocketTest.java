package org.anonymous.netty.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.net.MulticastSocket 广播
 * 发送: 无需指定 host:port
 * 接收: 指定端口, 否则无法确定发送数据报的目标端口
 * 多点广播 IP: 224.0.0.0 - 239.255.255.255
 * @since 2021/1/29 19:48
 */
public class MulticastSocketTest implements Runnable {

    private static final String BROADCAST_IP = "230.0.0.1";

    static final int BROADCAST_PORT = 30000;

    static final int DATA_LEN = 4096;

    MulticastSocket socket = null;

    InetAddress broadcastAddr = null;

    Scanner sc;

    byte[] inBuf = new byte[DATA_LEN];

    DatagramPacket inPacket = new DatagramPacket(inBuf, inBuf.length);

    DatagramPacket outPacket = null;

    void init() throws IOException {
        final Scanner scan = new Scanner(System.in);

        socket = new MulticastSocket(BROADCAST_PORT);
        broadcastAddr = InetAddress.getByName(BROADCAST_IP);

        // 将该 socket 加入指定的多点广播地址
        socket.joinGroup(broadcastAddr);
        // 设置本 socket 发送的数据报会被会送到自身
        socket.setLoopbackMode(false);

        outPacket = new DatagramPacket(new byte[0], 0, broadcastAddr, BROADCAST_PORT);

        new Thread(this).start();

        while (scan.hasNextLine()) {
            final byte[] buf = scan.nextLine().getBytes();
            outPacket.setData(buf);
            socket.send(outPacket);
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                socket.receive(inPacket);
                System.out.println("信息: " + new String(inBuf));
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    socket.leaveGroup(broadcastAddr);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}
