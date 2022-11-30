package org.anonymous.chapter03.connector.http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 连接器
 *      等待 HTTP 请求
 *      创建服务器套接字{@link ServerSocket}, 等待传入的 HTTP 请求
 *      为每个请求创建一个 {@link HttpProcessor} 实例
 *      调用 {@link HttpProcessor#process(Socket)} 方法
 * 此 connector 的功能与 {@link org.anonymous.chapter02.HttpServer1} 相似
 * 区别在于 从 {@link ServerSocket#accept()} 方法获取一个 套接字{@link Socket}
 * 创建一个 {@link HttpProcessor} 实例并传入该套接字, 调用 {@link HttpProcessor#process(Socket)} 方法
 * <p>
 * 该连接器是 Tomcat 4 中默认的连接器简化版. 但由于此连接器会解析所有的 HTTP 请求头,
 * 即使并不会在 servlet 中使用它们. 因此 默认连接器 运行缓慢, 现已被 Coyote 连接器替代.
 * ---
 * tomcat 中使用的连接器必须满足的要求:
 * 1. 实现 org.apache.catalina.Connector 接口
 * 2. 负责创建实现了 org.apache.catalina.Request 接口的 request 对象
 * 3. 负责创建实现了 org.apache.catalina.Response 接口的 response 对象
 * ---
 *
 * @see HttpProcessor
 * 在 tomcat 4 中, org.apache.catalina.connector.http.HttpConnector 与
 * org.apache.catalina.connector.http.HttpProcessor 是一对多的关系
 * HttpConnector 维护一个 HttpProcessor 池, 每个 HttpProcessor 实例都运行在其自己的线程中.
 * 这样, HttpConnector 实例就可以同时处理多个 HTTP 请求.
 * HttpProcessor 实例存储在 {@link java.util.Stack} 中, 其数量由两个变量决定: minProcessors 和 maxProcessors.
 * 如果 HttpProcessor 实例的数量已经达到了 maxProcessors 限定的数值, 但还是不够用, 此时引入的 HTTP 请求就会被忽略掉.
 * 若希望 HttpConnector 可以持续的创建 HttpProcessor 实例, 就可以将变量 maxProcessors 的值设置为负数.
 * 此外, 变量 curProcessors 保存了当前已有的 HttpProcessor 实例的数量.
 * org.apache.catalina.connector.http.HttpConnector#start() 方法创建初始数量的 HttpProcessor 实例的部分实现
 * 其中 newProcessor() 方法负责创建 HttpProcessor 实例, 并将 curPorcessors 加 1. recyle() 方法将新创建的 HttpProcessor 对象入栈.
 * 每个 HttpProcessor 实例负责解析 HTTP 请求行和请求头,填充 request 对象. 因此, 每个 HttpProcesspr 对象都关联一个 request 对象和
 * 一个 response 对象. HttpProcessor 类的构造函数会调用 HttpConnector 类的 createRequest() 方法和 createResonse() 方法
 * HttpConnector 类的主要业务逻辑在其 run() 方法中. run() 方法包含一个 while 循环, 在该循环体内, 服务器套接字等待 HTTP 请求,
 * 直到 HttpConnector 对象关闭.
 * 对于每个引入的 HTTP 请求, 它通过调用其私有方法 createProcessor() 获得一个 HttpProcessor 对象.
 * 但是, 大多数时间里, createProcessor() 方法并不会创建一个新的 HttpPocessor 实例. 相反, 它会从池中获取一个对象.
 * 如果栈中还有一个 HttpPorcessor 实例可用, createProcessor() 方法就从栈中弹出一个 HttpProcessor 实例并将其返回.
 * 如果栈已经空了, 而已经创建的 HttpPorcessor 实例的数量还没有超过限定的最大值, createProcessor() 方法就会创建一个新的 HttpProcessor 实例.
 * 但是, 若 HttpProcessor 实例的数量已经达到限定的最大值, createProcessor() 方法返回 null. 此时, 服务器会简单的关闭套接字,
 * 不会对引入的 HTTP 请求进行处理.
 * 如果 createProcessor() 方法的返回值不为 null, 则会将客户端套接字传入到 HttpProcessor 类的 assign() 方法中.
 * --
 * HttpConnector 实现 org.apache.catalina.Connector 接口(作为 catalina 连接器必须实现的接口),
 * {@link Runnable} 接口(确保它的实例在自己的线程中运行)和 org.apache.catalina.Lifecycle 接口.
 * Lifecycle 接口用于维护每个实现类该接口的每个 catalina 组件的生命周期.
 * --
 * org.apache.catalina.connector.http.HttpConnector#initialized 方法调用一个私有方法 open() 方法
 * open() 方法返回一个 {@link ServerSocket} 实例, 赋值给成员变量 serverSocket.
 * <p>
 * tomcat4.org.apache.catalina.connector.http.HttpConnector#start (run() -> processor.assign(socket);)
 * -> tomcat4.org.apache.catalina.connector.http.HttpConnector#newProcessor
 * -> tomcat4.org.apache.catalina.connector.http.HttpProcessor#start (run() -> Socket socket = await();)
 * -> tomcat4.org.apache.catalina.connector.http.HttpProcessor#process
 */
public class HttpConnector implements Runnable {

    // 请求协议
    private final String scheme = "http";
    boolean stopped;

    public String getScheme() {
        return scheme;
    }

    public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stopped) {
            // Accept the next incoming connection from the server socket
            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (Exception e) {
                continue;
            }
            // Hand this socket off to an HttpProcessor
            HttpProcessor processor = new HttpProcessor(this);
            processor.process(socket);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}