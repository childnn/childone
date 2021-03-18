package org.anonymous.netty.echo;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/5 10:40
 * 此目录下的示例, echo server/client(回声), 便于理解 Netty 如何实现 request-response 交互.
 * 最基本的 server/client 服务器系统模型.
 * Server
 * 1. 服务器 handler: 这个组件实现了服务器的业务逻辑, 决定了连接创建后和接收到信息后该如何处理
 *   示例: {@link org.anonymous.netty.echo.EchoServerHandler}
 * 2. Bootstrapping: 这个是配置服务器的启动代码. 最少需要设置服务器绑定的端口, 用来监听连接请求.
 * 引导服务器
 * 1. 监听和接收进来的连接请求
 * 2. 配置 Channel 来通知一个关于入站消息的 EchoServerHandler 实例
 * Transport
 *  在网络的多层视图协议里, 传输层提供了用于 端到端 或 主机到主机 的通信服务.
 *  互联网通信的基础是 TCP 传输. 当我们使用 NIO-transport 术语时, 指的是一个传输的实现,
 *  它大多等同于 TCP, 除了一些由 Java-NIO 的实现提供了服务端的性能增强.
 *   {@link org.anonymous.netty.echo.EchoServer}
 * ---
 * Client
 * 客户端要做的是:
 *  1. 连接服务器
 *  2. 发送消息
 *  3. 发送的每个信息, 等待和接收从服务器返回的同样的信息
 *  4. 关闭连接
 *  客户端实现 {@link io.netty.channel.SimpleChannelInboundHandler}
 *   示例 {@link org.anonymous.netty.echo.EchoClientHandler}
 * 引导客户端
 */
public class Echo {
}
