package org.anonymous.netty.aiojava7;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/10 16:52
 */
public class Client {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 20; i++) {
                Socket s = new Socket();
                s.connect(new InetSocketAddress("localhost", 8080));
                processWithNewThread(s, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void processWithNewThread(Socket s, int i) {
        Runnable run = () -> {
            try {
                //睡眠随机的5-10秒，模拟数据尚未就绪
                Thread.sleep((new Random().nextInt(6) + 5) * 1000);
                //写1M数据，为了拉长服务器端读数据的过程
                s.getOutputStream().write(prepareBytes());
                //睡眠1秒，让服务器端把数据读完
                Thread.sleep(1000);
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        new Thread(run).start();
    }

    static byte[] prepareBytes() {
        byte[] bytes = new byte[1024 * 1024];
        /*for (int i = 0; i < bytes.length; i++) {
            bytes[i] = 1;
        }*/
        Arrays.fill(bytes, (byte) 1);
        return bytes;
    }
}
