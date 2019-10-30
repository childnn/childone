package org.anonymous.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 一个客户端对应一个线程
 * 1. cmd: telnet localhost 6666
 * 2. ctrl + ] 进入交互界面
 * 3. send + 发送信息
 */
public class BIOServer {

    public static final int PORT = 6666;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws Exception {

        //线程池机制

        //思路
        //1. 创建一个线程池
        //2. 如果有客户端连接，就创建一个线程，与之通讯(单独写一个方法)

        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建 ServerSocket
        ServerSocket serverSocket = new ServerSocket(PORT);         // 1   服务端

        System.out.println("服务器启动了");

        // 创建线程的循环: 接受每一个客户端的请求, 一个客户端循环一次
        while (true) {
            System.out.println("线程信息 id =" + Thread.currentThread().getId() + " 名字=" + Thread.currentThread().getName());
            System.out.println("等待连接....");
            //监听，等待客户端连接
            final Socket clientSocket = serverSocket.accept();        // 2   监听客户端
            System.out.println("连接到一个客户端");

            //就创建一个线程，与之通讯(单独写一个方法)
            newCachedThreadPool.execute(() -> {             // 3   开启线程
                //可以和客户端通讯
                handler(clientSocket);
            });
        }
    }

    //编写一个handler方法，和客户端通讯
    public static void handler(Socket clientSocket) {
        try {
            System.out.println("线程信息 id = " + Thread.currentThread().getId() + " 名字 = " + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //通过socket 获取输入流
            InputStream inputStream = clientSocket.getInputStream();  // 4   读取流

            // 一条消息循环一次
            // 循环的读取客户端发送的数据
            while (true) {
                System.out.println("线程信息 id =" + Thread.currentThread().getId() + " 名字=" + Thread.currentThread().getName());
                System.out.println("read....");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read)); //输出客户端发送的数据
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                clientSocket.close();             // 5
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
