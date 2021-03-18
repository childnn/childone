package org.anonymous.netty;

import com.google.protobuf.MessageLite;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.ssl.ApplicationProtocolNames;
import io.netty.handler.ssl.ApplicationProtocolNegotiationHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import org.eclipse.jetty.npn.NextProtoNego;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/4 19:46
 * Channel:
 * @see io.netty.channel.Channel Netty-NIO 的基本结构. 它代表了一个用于连接到实体如硬件设备, 文件, 网络套接字或程序组件,
 * 能够执行一个或多个不同的 I/O 操作(read/write etc.) 的开放链接.
 * 把 Channel 想象成一个可以 "打开" 或 "关闭", "连接" 或 "断开" 和作为传入和传出数据的运输工具.
 *
 * Callback:
 * @see io.netty.channel.ChannelHandler callback
 *  callback 是一个简单的方法, 提供给另一种方法作为引用, 这样后者就可以在某个适合的时间调用前者.
 *  这种技术被广泛使用在各种编程的情况下, 最常见的方法之一是通知给其他人操作已完成.
 *   示例 {@link org.anonymous.netty.NettyPreview.ConnectHandler}
 *
 * Future:
 * @see io.netty.util.concurrent.Future 实现 java 1.5+ 的 Future, 暂时不确定是不是 JDK 借鉴了 Netty.
 * JDK 的 Future, 必须手动检查操作是否完成或阻塞了. Netty 实现了{@link io.netty.channel.ChannelFuture}
 * 用于在执行异步操作时使用. ChannelFuture 允许添加 {@link io.netty.util.concurrent.GenericFutureListener}
 * 在 Future 操作完成时调用 {@link io.netty.util.concurrent.GenericFutureListener#operationComplete(io.netty.util.concurrent.Future)}
 * 事件监听者能够确认这个操作是否成功或错误. 如果是后者, 可检索产生的 Exception. (或者参见其子接口: {@link io.netty.channel.ChannelFutureListener})
 * 此即 真正的 Non-blocking
 * 每个 Netty 的 outbound-I/O 操作都会返回一个 {@link io.netty.channel.ChannelFuture}, 这样就不会阻塞.
 * 这就是 Netty 所谓的 "自底向上的异步和事件驱动".
 * 参见示例 {@link org.anonymous.netty.NettyPreview.ChannelFutureTest}
 * 调用方法 {@link io.netty.channel.ChannelOutboundInvoker#connect(java.net.SocketAddress)}
 * 直接返回 ChannelFuture, Non-blocking, 调用在背后完成. 无需等待操作完成.
 * 异步连接远程地址处理成功/失败示例: {@link org.anonymous.netty.NettyPreview.ChannelFutureListenerTest}
 * ---
 * Event 和 Handler
 * Netty 使用不同的事件来通知我们更改的状态或操作的状态. 这使我们能够根据发生的事件触发适当的行为.
 * 这些行为包括但不限于: 日志, 数据转换, 流控制, 应用程序逻辑
 * 由于 Netty 是一个网络框架, 事件很清晰的跟入站(inbound)或出站(outbound)数据流相关. 因为一些事件可能触发传入
 * 的数据或状态的变化包括: 活动或非活动连接, 数据的读取, 用户事件, 错误.
 * 出站事件是由于在未来操作将触发一个动作. 包括: 打开或关闭一个连接到远程, 写或冲刷数据到 socket.
 * 每个事件都可以分配给用户实现处理程序类的方法. 这说明事件驱动的范例可直接转换为应用程序构建块.
 * Netty 的 {@link io.netty.channel.ChannelHandler} 是各种处理程序的基本抽象.
 * 想象一下, 每个处理器示例就是一个 回调, 用于执行对各种事件的响应.
 * 在此基础上, Netty 也提供了一组丰富的预定义的处理程序, 开箱即用. 比如, 各种协议的 编解码器 包括
 * HTTP 和 SSL/TLS. 在内部, ChannelHandler 使用事件和 future 本身, 创建具有 Netty 特性抽象的消费者.
 * ---
 * 整合 Future, Callback, Handler
 * Netty 的异步编程模型是建议在 future 和 calback 概念上的. 所有这些元素的协同为自己的设计提供了强大的力量.
 * 拦截操作和转换 inbound/outbound 数据只需提供 callback 或利用 future 操作返回的. 这使得链操作简单, 高效,
 * 促进编写可重用的, 通用的代码. 一个 Netty 的设计的主要目标是促进 "关注点分离": 业务逻辑从网络基础设施应用程序中分离.
 * Selector, Event, Event Loop
 * Netty 通过触发事件从应用程序中抽象出 Selector, 从而避免手写调度代码. EventLoop 分配给每个 Channel 来处理所有的事件, 包括:
 *  注册感兴趣的事件, 调度事件到 ChannelHandler, 安排进一步行动.
 * 该 EventLoop 本身只有一个线程驱动, 它给一个 Channel 处理所有的 I/O 事件, 并且在 EventLoop 的生命周期内不会改变.
 * 这个简单而强大的线程模型消除你可能对你的 ChannelHandler 同步的任何关注, 这样你就可以专注于提供正确的回调逻辑来执行.
 * 基本示例: {@link org.anonymous.netty.echo.Echo} 及其目录下的 client/server 模型
 * -----------------------------------------------------------------------------
 * Netty 核心组件:
 * 引导类:
 * @see io.netty.bootstrap.Bootstrap 连接到远程主机和端口, EventLoopGroup 数量为 1;
 * @see io.netty.bootstrap.ServerBootstrap 绑定本地端口, EventLoopGroup 数量为 2.
 *      2 个 Channel 集合, 一个集合包含一个单例 ServerChannel, 代表持有一个绑定了本地端口的 socket;
 *      一个集合包含所有创建的 Channel, 处理服务器所接收到的客户端进来的连接.
 * -
 * @see io.netty.channel.Channel 底层网络传输 API 必须提供给应用 I/O 操作的接口. 如 read/write/bind 等.
 *   目前来说, 这个结构总会是一个 socket. Channel(或其父接口) 定义了与 socket 丰富交互的操作集:
 *   bind, close, config, connect, isAcitve, isOpen, isWritable, read, write, etc.
 * Netty 提供大量的 Channel 实现供特定场景使用.
 * {@link io.netty.channel.AbstractChannel} {@link io.netty.channel.AbstractServerChannel}
 * {@link io.netty.channel.nio.AbstractNioChannel} {@link io.netty.channel.embedded.EmbeddedChannel}
 * {@link io.netty.channel.local.LocalServerChannel} {@link io.netty.channel.socket.nio.NioSocketChannel} etc.
 * -
 * @see io.netty.channel.ChannelHandler
 *     ChannelHandler 支持很多协议, 并且提供用于数据处理的容器. ChanelHandler 由特定事件触发, 可用于几乎所有的动作,
 *     包括将一个对象转为字节(或相反), 执行过程中抛出的异常.
 *     常用的一个接口是 {@link io.netty.channel.ChannelInboundHandler}, 这个类型接收到入站事件(包括接收到的数据)
 *     可以处理应用程序逻辑. 当需要提供响应时, 也可以从 {@link io.netty.channel.ChannelOutboundHandler} 冲刷数据.
 *     outbound: 数据从用户应用程序到远程主机
 *     inbound: 数据从远程主机到用户应用程序.
 *  数据从一端到达另一端, 一个或多个 ChannelHandler 将以某种方式操作数据. 这些 ChannelHandler 会在程序的 "引导" 阶段被添加
 *  ChannelPipeline 中, 并且被添加的顺序将决定处理数据的顺序. 入站时按 ChannelHandler 添加顺序执行, 出站时 ChannelHandler 执行顺序相反.
 *  在当前的链中, 事件可以通过 {@link io.netty.channel.ChannelHandlerContext} 传递给下一个 handler. Netty 为此提供了抽象基类
 *  {@link io.netty.channel.ChannelInboundHandlerAdapter} 和 {@link io.netty.channel.ChannelOutboundHandlerAdapter}
 *  用来处理你想要的事件. 这些类提供了方法的实现, 可以简单的通过调用 {@link io.netty.channel.ChannelHandlerContext} 上的相应方法
 *  将事件传递给下一个 handler.
 *  ChannelHandlerContext 代表一个 ChannelHandler 和 ChannelPipeline 之间的 绑定, 它通常是安全保存此对象的引用, 除了当协议中
 *  使用是面向无连接(如 UDP). 而该对象可以被用来获得底层 Channel, 它主要用来 write 出站数据.
 *  实际上, Netty 发送消息有两种方式. 可以直接写消息给 Channel 或写入 ChannelHandlerContext 对象.
 *  主要的区别是, 前一种方法会导致消息从 ChannelPipeline 的尾部开始, 而后者导致消息从 ChannelPipeline 下一个处理器开始.
 *  如果 inbound/outbound 操作不同, 当 ChannelPipeline 中有混合处理器时将发生什么?
 *  虽然 inbound/outbound 处理器都扩展了 ChannelHandler, Netty 的 ChannelInboundHandler 的实现和 ChannelOutboundHandler 之间
 *  的是有区别的, 从而保证数据传递从一个处理器到下一个处理器的正确类型.
 *  -
 * 常用的 ChannelHandler
 *  编解码器: 实现自 ChannelOutboundHandler/ChannelInboundHandler
 * @see io.netty.handler.codec.MessageToByteEncoder 编码(出站)
 * @see io.netty.handler.codec.ByteToMessageDecoder 解码(入站)
 * @see io.netty.handler.codec.protobuf.ProtobufEncoder 支持谷歌 protocol-buffer 的编码器
 * @see io.netty.handler.codec.protobuf.ProtobufDecoder 支持谷歌 protocol-buffer 的解码器
 * 对于 inbound 数据, 覆盖{@link io.netty.channel.ChannelInboundHandler#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)}
 * 这个方法在每个消息从入站 Channel 读入时调用. 该方法将调用特定解码器 解码, 并将解码后的消息转发到管道中下个 ChannelInboundHandler.
 * outbound 数据, 编码器将消息转为字节, 转发到下个 ChannelOutboundHandler.
 * @see io.netty.channel.SimpleChannelInboundHandler
 *   解码之后, 需要执行一些业务逻辑. 只需扩展 {@link io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)}
 *   泛型 T 表示想要进行处理的类型.
 * 应禁止任何阻塞操作在 ChannelHandler 中. 可以指定一个 {@link io.netty.util.concurrent.EventExecutorGroup},
 * 当 ChannelHandler 添加到 ChannelPipeline, 此 EventExecutorGroup 将用于获得 EventExecutor, 将执行所有的 ChannelHandler 的方法.
 * EventExecutor 将从 I/O 线程使用不同的线程, 从而释放 EventLoop.
 * -
 * @see io.netty.channel.ChannelPipeline
 *     ChannelPipeline 提供了一个容器给 ChannelHandler 链, 并提供了一个 API 用于管理沿着链 inbound/outbound 事件的流动.
 *     每个 Channel 都有自己的 ChannelPipeline, 当 Channel 创建时自动创建.
 *     ChannelHandler 如何安装在 ChannelPipeline?
 *     主要是实现了 ChannelHandler 的抽象 {@link io.netty.channel.ChannelInitializer}
 *     ChannelInitializer 子类通过 ServerBootstrap 注册. 当 {@link io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)}
 *     被调用时, 这个对象将安装自定义的 ChannelHandler 集到 pipeline. 当这个操作完成时, ChannelInitializer 子类则从 ChannelPipeline 自动删除自身.
 * @see io.netty.channel.EventLoop
 *     EventLoop 用于处理 Channel 的 I/O 操作. 一个单一的 EventLoop 通常会处理多个 Channel 事件. 一个 EventLoopGroup 可以包含多个
 *     EventLoop 并提供一种迭代方式用于检索清单中的下一个.
 * @see io.netty.channel.ChannelFuture
 *     Netty 所有的 I/O 操作都是异步. 因为一个操作可能无法立即返回, 需要有一种方法在以后确定它的结果. 出于这个目的, Netty 提供了
 *     接口 ChannelFuture, {@link io.netty.channel.ChannelFuture#addListener} 方法注册一个 {@link io.netty.util.concurrent.GenericFutureListener}
 *     当操作完成时, 可以被通知(不管成功与否).
 *  ChannelFuture 对象作为一个未来执行操作结果的占位符, 何时执行取决于几个因素, 因此不可能预测与精确.
 *  但可以肯定的是, 它会被执行. 此外, 所有的操作返回 ChannelFuture 对象属于同一个 Channel, 它们将在以正确的顺序被执行.
 * -
 * Channel, Event, I/O
 * Netty 是一个非阻塞, 事件驱动的网络框架. Netty 实际上使用 多线程 处理 I/O 事件. Netty 的设计保证程序处理事件不会有同步.
 * 一个 EventLoopGroup 拥有一个或多个 EventLoop. 当创建一个 Channel, Netty 通过一个单独的 EventLoop 实例来注册该 Channel
 * (一个单独的 Thread)的使用寿命.
 * ---
 * Transport: NIO, OIO, Local, Embeded
 *   Java Blocking-IO 示例: {@link org.anonymous.netty.NettyPreview.PlainOioServer}
 *   Java Non-Blocking-IO 示例: {@link org.anonymous.netty.NettyPreview.PlainNioServer}
 *   -
 *   Netty Blocking-IO 示例: {@link org.anonymous.netty.NettyPreview.NettyOioServer}
 *   Netty Non-Blocking-IO 示例: {@link org.anonymous.netty.NettyPreview.NettyNioServer}
 *  参看以上四个示例, 可以发现 JDK 自带 IO, Blocking 与 Non-Blocking 使用完全不同的 API,
 *  而对 Netty 而言, 二者几乎一致, Netty 使用相同的 API 来实现每个传输, 不关心使用什么实现.
 *  Netty 通过操作接口 Channel, ChannelPipeline, ChannelHandler 实现.
 * Transport API 的核心是 {@link io.netty.channel.Channel} 接口, 用于所有出战操作
 * 每个 Channel 都会分配一个 {@link io.netty.channel.ChannelPipeline} 和 {@link io.netty.channel.ChannelConfig}
 * ChannelConfig 负责设置并存储 Channel 的配置, 并允许在运行期间跟新它们. 传输一般有特定的配置,
 * 它可能实现了 ChannelConfig 的子类型.
 * ChannelPipeline 容纳了使用 ChannelHandler 实例, 这些 ChannelHandler 将处理通道传递的 inbound/outbound 数据及事件.
 * ChannelHandler 的实现允许你改变数据状态和传输数据.
 * ChannelHandler 可以执行的操作:
 * 1. 传输数据时, 将数据从一种格式转换成另一种格式
 * 2. 异常通知
 * 3. Channel 变为 active 或 inactive 时获得通知 某个 Channel 被注册或注销时从 EventLoop 中获得通知
 * 4. 通知用户特定事件
 * -
 * Intercepting Filter: 拦截过滤器
 *  ChannelPipeline 实现了常用的 Intercepting Filter 设计模式.
 *  !!!UNIX 管道是另一个例子: 命令链接在一起, 一个命令的输出链接到下一个命令的输入.
 *  可以根据需要添加 ChannelHandler 实例到 ChannelPipeline 或从 ChannelPipeline 中删除,
 *  这能帮助我们构建高度灵活的 Netty 程序.
 *  Channel 写出数据使用示例: {@link org.anonymous.netty.NettyPreview.WriteToAChannel}
 * Channel 是线程安全的, 它可被多个不同的线程安全的操作, 在多线程环境下, 所有的方法都是安全的.
 *  Channel 多线程使用示例: {@link org.anonymous.netty.NettyPreview.MultiThreadChannel}
 * ---
 * Netty 提供的传输方式:
 *   NIO: package io.netty.channel.nio/io.netty.channel.socket.nio,
 *        Java 1.4. 核心思想, 选择器 {@link org.anonymous.netty.niojava4.NIOMain}
 *        基于 java.nio.channels 工具包, 使用 selector 选择器作为基础方法;
 *        应用场景: 在高连接数时使用.
 *   OIO: package io.netty.channel.oio/io.netty.channel.socket.oio;
 *        Old-Blocking I/O
 *        基于 java.net 工具包, 使用 blocking-IO;
 *        应用场景: 在低连接数, 需要低延迟时, 阻塞时使用.
 *   Local: package io.netty.channel.local, 用来在虚拟机之间本地通信.
 *      为运行在同一个 JVM 上的 server/client 提供异步通信. 此传输支持所有的 Netty 常见的传输实现的 API
 *      在此传输中, 与服务器 Channel 关联的 SocketAddress 不是 bind 到一个物理网络地址中, 而是在服务器是运行时
 *      它被存储在注册表中, 当 Channel 关闭时会注销. 由于该传输不是真正的网络通信, 它不能与其他传输实现互操作.
 *        应用场景: 在同一个 JVM 内通信时使用
 *   Embeded: package io.netty.channel.embedded, 嵌入传输, 它允许在没有真正网络的传输中
 *         使用 ChannelHandler, 可以非常有用的来测试 ChannelHandler 的实现.
 *       Netty 提供可嵌入 ChannelHandler 实例到其他的 ChannelHandler 的传输, 使用它们就像辅助类, 增加了灵活性的方法,
 *       可以与 ChannelHandler 互动. 该嵌入技术通常用于测试 ChannelHandler 的实现, 但它也可以将功能添加到现有的
 *       ChannelHandler 而无需更改代码. {@link io.netty.channel.embedded.EmbeddedChannel}
 *       应用场景: 测试 ChannelHandler 时使用
 * --
 * Buffer: 缓冲
 *   网络数据的基本单位为 byte(万物皆字节). Java-NIO 提供 {@link java.nio.ByteBuffer} 作为字节容器,
 *   但它的作用太有限, 也未进行优化, 使用 ByteBuffer 通常是一件繁琐而又复杂的事.
 *   Netty 提供了一个强大的缓冲实现类用来表示字节序列, 操作字节和自定义的 POJO. 这个新的缓冲类
 *   {@link io.netty.buffer.ByteBuf}, 在 Netty 的 pipeline 中传输数据. 它是为了解决 ByteBuffer 存在的
 *   一些问题以及满足网络程序开发者的需求, 提高效率.
 * @see io.netty.buffer.ByteBuf
 * @see io.netty.buffer.ByteBufHolder
 *  Netty 使用 reference-counting(引用计数)来判断何时可以释放 ByteBuf 或 ByteBufHolder 和其他相关资源,
 *  从而可以利用池和其他技巧来提高性能和降低内存小消耗.
 *  Netty 缓冲 API 提供了几个优势:
 *   1. 可以自定义缓冲类型
 *   2. 通过一个内置的复合缓冲类型实现零拷贝
 *   3. 无需像 JDK-Buffer 调用{@link java.nio.Buffer#flip()} 读写切换
 *   4. read/write 索引分开
 *   5. 链式调用
 *   6. 引用计数
 *   7. 池: pooling
 * ByteBuf 如何工作?
 * 写入数据到 ByteBuf 后, writerIndex 增加写入的字节数. 读取字节后, readerIndex
 * 也增加读取出的字节数. 可以读取字节, 直到写入索引和读取索引处在相同的位置. 此时 ByteBuf 不可读,
 * 下次 read 操作将会抛出 IOOBE, 就像数组索引越界一样.
 * !!! 调用 ByteBuf 以 read/write 开头的任何方法都将自带增加相应的索引. 另一方面, set/get 操作
 * 字节将不会移动索引位置, 而只会在指定的相对位置上操作字节.
 * 可以给 ByteBuf 指定一个最大容量值, 这个值限制着 ByteBuf 的容量. 任何尝试将写入超过这个值的数据
 * 的行为都将导致抛出异常. ByteBuf 的默认最大容量限制是 {@link java.lang.Integer#MAX_VALUE}
 * ByteBuf 类似一个字节数组, 最大的区别是 read/write 的索引可以用来控制对缓冲区的访问.
 * -
 * ByteBuf 使用模式:
 *  Heap-buffer: 堆缓冲区
 *   最常用的模式是 ByteBuf 将数据存储在 JVM 堆空间, 这是通过将数据存储在数组的实现.
 *   堆缓冲区可以快速分配, 当不使用时也可以快速释放. 它还提供了直接访问数组的方法, 通过
 *   {@link io.netty.buffer.ByteBuf#array()} 来获取 byte[] 数据.
 *   示例: {@link org.anonymous.netty.NettyPreview.ByteBufBackingArray}
 *  Direct Buffer: 直接缓冲区
 *   JDK 1.4. 引入的 NIO-ByteBuffer 允许 JVM 通过 native 方法调用分配内存, 目的是:
 *   1. 通过免去中间交换的内存拷贝, 提升 IO 处理速度; 直接缓冲区的内容可以驻留在垃圾回收扫描的堆区以外;
 *   2. DirectBuffer 在 -XX:MaxDirectMemorySize=xxM 大小限制下, 使用 Heap 之外的内存, GC 对此
 *      无能为力, 意味着规避了在高负载下频繁的 GC 过程对应用程序线程的中断影响.
 *   这就解释了为什么 "直接缓冲区" 对于那些通过 socket 实现数据传输的应用来说, 是一种非常理想的方式.
 *   如果你的数据是存放在堆中分配的缓冲区, 那么实际上, 在通过 socket 发送数之前, JVM 需要先将数据
 *   复制到直接缓冲区. 但是直接缓冲区的缺点是在内存空间的分配和释放上比堆缓冲区更复杂, 另外一个缺点是如果
 *   要将数据传递给遗留代码处理, 因为数据不是在堆上, 你可能不得不作出一个副本.
 *   示例: {@link org.anonymous.netty.NettyPreview.ByteBufDirectBuf}
 *  Composite Buffer: 复合缓冲区
 *   可以创建多个不同的 ByteBuf, 然后提供一个这些 ByteBuf 组合的视图. 复合缓冲区就像一个列表, 可以动态的添加和
 *   删除其中的 ByteBuf, JDK 的 ByteBuffer 没有这样的功能. Netty 提供了 ByteBuf 的子类
 *   {@link io.netty.buffer.CompositeByteBuf} 类来处理复合缓冲区, CompositeByteBuf 只是一个视图.
 *   {@link io.netty.buffer.CompositeByteBuf#hasArray()} 在多复合的情况下, 有可能既包含对缓冲区, 也包含直接缓冲区.
 *   示例: {@link org.anonymous.netty.NettyPreview.ByteBufCompositeBuf}
 *   Netty 尝试使用 CompositeByteBuf 优化 socket I/O 操作, 消除原生 JDK 中可能存在的性能低和内存消耗问题.
 *   虽然这是在 Netty 的核心代码中进行的优化, 并且是不对外暴露的, 但作为开发者还是应该意识到其影响.
 * -
 * 字节级别的操作
 *  除了基本的 read/write 操作, ByteBuf 还提供了它所包含的数据的修改方法.
 *  1. 随机访问索引
 *    ByteBuf 使用 zero-based 的 indexing, 第一个字节的索引是 0, 最后一个字节的索引是 ByteBuf 的 capacity - 1.
 *    示例: {@link org.anonymous.netty.NettyPreview.ZeroBasedIndexing}
 *  2. 顺序访问索引
 *    ByteBuf 提供两个指针变量执行 read/write 操作, 分别使用 readIndex()/writeIndex().
 *    这和 JDK 的 ByteBuffer 不同, ByteBuffer 只有一个方法来设置索引, 所以需要使用 flip() 方法来切换 read/write 模式.
 *    0 <= readerIndex <= writerIndex <= capacity
 *      0~readerIndex 之间: 已经被读的字节, 可以被丢弃(discardable bytes);
 *      readerIndex~writerIndex 之间: 还没有被读的字节(readable bytes);
 *      writerIndex~capacity 之间: 可以写入字节(writable bytes);
 *    解释:
 *    discardable bytes 已经被读取, 可以被丢弃, 调用 {@link io.netty.buffer.ByteBuf#discardReadBytes()}
 *    来回收空间. 这个索引区间的初始大小存储在 readerIndex, 即 0, 当 "read" 操作被执行时递增("get" 操作时不会移动 readerIndex).
 *    {@link io.netty.buffer.ByteBuf#discardReadBytes()} 用来清空 ByteBuf 中已经读取的数据, 从而使 ByteBuf 有
 *    多余的空间容纳新的数据, 但是 discardReadBytes() 可能会涉及内存复制, 因为它需要移动 ByteBuf 中可读的字节到开始位置,
 *    这样的操作会影响性能, 一般在需要马上释放内存的时候使用收益会比较大.
 *    -
 *    readable bytes 可读字节, 存储的实际数据. 新分配, 包装, 或复制的缓冲区的 readIndex 的默认值为 0. 任何操作,
 *    其名称以 read/skip 开头的都将检索或跳过该数据的当前 readerIndex, 并且通过读取的字节来递增.
 *    如果所谓的读操作是一个指定 ByteBuf 参数作为写入的对象, 并且没有一个目标索引参数, 目标缓冲区的 writerIndex 也会增加了.
 *    eg: {@link io.netty.buffer.ByteBuf#readBytes(byte[])}
 *    如果试图从缓冲区读取已经用尽的可读的字节, 则抛出 IOOBE.
 *    示例: {@link org.anonymous.netty.NettyPreview.ReadAllData}
 *    一个新分配的缓冲区的 writerIndex 的默认值是 0, 任何操作, 其名称 write 开头的操作在当前的 writerIndex 写入数据时,
 *    递增字节写入的数量. 如果 write 操作的目标也是 ByteBuf, 且未指定源索引, 则源缓冲区的 readerIndex 将增加相同的量.
 *    eg: {@link io.netty.buffer.ByteBuf#writeBytes(io.netty.buffer.ByteBuf)}
 *    如果试图写入超出目标的容量, 则抛出 IOOBE.
 *    示例: {@link org.anonymous.netty.NettyPreview.WriteData}
 * -
 * 索引管理
 *  在 JDK 的 {@link java.io.InputStream#mark(int)} 和 {@link java.io.InputStream#reset()}
 *  方法, 分别用来标记流中的当前位置和复位流到该位置.
 *  与此类似, {@link io.netty.buffer.ByteBuf#markReaderIndex()}, {@link io.netty.buffer.ByteBuf#markWriterIndex()}
 *  {@link io.netty.buffer.ByteBuf#resetReaderIndex()}, {@link io.netty.buffer.ByteBuf#resetWriterIndex()}
 *  可通过 {@link io.netty.buffer.ByteBuf#writerIndex(int)} {@link io.netty.buffer.ByteBuf#readerIndex(int)}
 *  将游标移动到指定位置. 在尝试任何无效位置上设置一个索引将导致 IOOBE.
 *  调用 {@link io.netty.buffer.ByteBuf#clear()} 将同时设置 readerIndex/writerIndex 为 0, 这不会清除内存中的数据.
 *  调用 clear() 之后, 整个 ByteBuf 空间都是可写的. clear() 比 discardReadBytes() 更低成本, 因为它只是重置了索引,
 *  而没有内存拷贝.
 * -
 * 查询操作
 *   确定指定缓冲区中指定值的索引: {@link io.netty.buffer.ByteBuf#indexOf(int, int, byte)} 最简单,
 *   更复杂的方式 {@link io.netty.buffer.ByteBuf#forEachByte(io.netty.util.ByteProcessor)}
 *   类似 {@link java.util.function.Predicate}
 *   示例: {@link org.anonymous.netty.NettyPreview.ByteBufProcessor}
 * -
 * 衍生的缓冲区
 *   衍生的缓冲区代表一个专门的展示 ByteBuf 内容的视图, 这种视图由 {@link io.netty.buffer.ByteBuf#duplicate()}
 *   {@link io.netty.buffer.ByteBuf#slice} {@link io.netty.buffer.ByteBuf#order} 等方法创建.
 *   这些方法返回一个新的 ByteBuf 实例包括它自己的 reader/writer 和标记索引. 然而, 内部数据存储共享就像在一个 NIO 的
 *   ByteBuffer. 使得衍生的缓冲区创建, 修改其内容, 以及修改其源实例更廉价.
 * -
 * 拷贝
 *   如需已有的缓冲区的全新副本: {@link io.netty.buffer.ByteBuf#copy}. 不同于派生缓冲区, 这个调用返回的 ByteBuf
 *   有数据的独立副本. 若需要操作某段数据, 使用 {@link io.netty.buffer.ByteBuf#slice(int, int)}
 *   示例: {@link org.anonymous.netty.NettyPreview.SliceAByteBuf}
 *        {@link org.anonymous.netty.NettyPreview.CopyAByteBuf}
 * -
 * read/write
 *   读写操作主要有两类:
 *    get/set 操作从指定索引开始, readerIndex/writerIndex 不变
 *    read/writer 操作从指定索引开始, 与字节访问的数量相适应, 递增当前的 readerIndex/writerIndex.
 *    示例: {@link org.anonymous.netty.NettyPreview.GetSetByte}
 *        {@link org.anonymous.netty.NettyPreview.ReadWriteByte}
 * -
 * ByteBufHolder
 *   经常遇到需要另外存储 除有效的实际数据各种属性值. HTTP 相应是个很好的例子: 与内容一起的字节的还有状态码, cookies 等.
 *   Netty 提供 {@link io.netty.buffer.ByteBufHolder} 处理这种常见的情况. ByteBufHolder 还提供对于 Netty 的高级
 *   功能, 如缓冲池, 其中保存实际数据的 ByteBuf 可以从池中借用, 如果需要还可以自动释放.
 *   {@link io.netty.buffer.ByteBufHolder#content()} Return the data which is held by this {@code ByteBufHolder}.
 *   {@link io.netty.buffer.ByteBufHolder#copy()} Creates a deep copy of this {@code ByteBufHolder}.
 *   如果实现一个消息对象有效负载存储在 ByteBuf, 使用 ByteBufHolder 是一个不错的选择.
 * -
 * ByteBuf 分配
 *   为了减少分配和释放内存的开销, Netty 通过支持池类 {@link io.netty.buffer.ByteBufAllocator}, 可用于分配任何
 *   ByteBuf 类型.
 *   {@link io.netty.buffer.ByteBufAllocator#buffer}
 *   {@link io.netty.buffer.ByteBufAllocator#heapBuffer}
 *   {@link io.netty.buffer.ByteBufAllocator#directBuffer}
 *   {@link io.netty.buffer.ByteBufAllocator#compositeBuffer}
 *   {@link io.netty.buffer.ByteBufAllocator#compositeHeapBuffer}
 *   {@link io.netty.buffer.ByteBufAllocator#compositeDirectBuffer}
 *   {@link io.netty.buffer.ByteBufAllocator#ioBuffer}
 *   获取 allocator 的方式-示例: {@link org.anonymous.netty.NettyPreview.ByteBufAllocatorTest}
 *   Netty 提供了两种 ByteBufAllocator 的实现, {@link io.netty.buffer.PooledByteBufAllocator}
 *   用 ByteBuf 实例池改进性能以及内存使用降到最低, 此实现使用一个 jemalloc 内存分配.
 *   其他实现不池化 ByteBuf 情况下, 每次返回一个新的实例.
 *   Netty 默认使用 PooledByteBufAllocator, 可以通过 {@link io.netty.channel.ChannelConfig}
 *   或通过引导设置一个不同的实现来改变.
 * -
 * Unpooled 非池化缓存
 *  当未引用 ByteBufAllocator 时, 无法通过 ByteBufAllocator 来访问 ByteBuf. 对于这个用例 Netty 提供一个
 *  实用工具类称为 {@link io.netty.buffer.Unpooled}, 它提供了静态辅助方法来创建非池化的 ByteBuf 实例.
 * ByteBufUtil
 *  {@link io.netty.buffer.ByteBufUtil} 静态方法来操作 ByteBuf, 应为这个 API 是通用的, 与使用池无关,
 *  这些方法已经在外面的分配类实现. 也许最有价值的是 {@link io.netty.buffer.ByteBufUtil#hexDump} 方法,
 *  这个方法返回指定 ByteBuf 中可读字节的十六进制字符串, 可用于调试程序时打印 ByteBuf 的内容. 一个典型的用途是
 *  记录一个 ByteBuf 的内容进行调试. 十六进制字符串比字节而言对用户更友好. 而十六进制版本很容易转回实际字节表示.
 *  另一个 {@link io.netty.buffer.ByteBufUtil#equals(io.netty.buffer.ByteBuf, io.netty.buffer.ByteBuf)}
 *  用来比较 ByteBuf 实例是否相等.
 * -
 * 引用计数器 {@link io.netty.util.ReferenceCounted}
 *   引用计数器在特定的对象上跟踪引用的数目. 活动的引用计数器大于 0 的对象被保证不被释放. 当释放引用减少到 0, 该实例将被释放.
 *   需要注意的是 "释放" 的语义是特定于具体的实现. 最起码, 一个对象已被释放应不再可用.
 *   这种技术就是诸如 PooledByteBufAllocator 这种减少内存分配开销的池化技术的精髓所在.
 * ---
 * ChannelHandler 家族
 *   Channel 生命周期
 *    Channel 有个简单但强大的状态模型, 与 {@link io.netty.channel.ChannelInboundHandler} 密切相关.
 *    {@link io.netty.channel.ChannelInboundHandler#channelRegistered} channel 注册到一个 EventLoop
 *    {@link io.netty.channel.ChannelInboundHandler#channelActive} channel 变为活跃状态(连接到远程主机), 现在可以收发数据了
 *    {@link io.netty.channel.ChannelInboundHandler#channelInactive} channel 处于非活跃状态, 没有连接到远程主机
 *    {@link io.netty.channel.ChannelInboundHandler#channelUnregistered} channel 已创建但为注册到一个 EventLoop
 *  channel 生命周期按上述四个方法的顺序, 进入到指定状态会触发对应的事件, 这样就能与 ChannelPipeline 中的 ChannelHandler 进行及时的交互.
 *   ChannelHandler 定义的生命周期:
 *    {@link io.netty.channel.ChannelHandler#handlerAdded} ChannelHandler 添加到 ChannelPipeline
 *    {@link io.netty.channel.ChannelHandler#handlerRemoved} ChannelHandler 从 ChannelPipeline 移除
 *    {@link io.netty.channel.ChannelHandler#exceptionCaught} 执行异常时调用, 被下一个方法替代.
 *    {@link io.netty.channel.ChannelInboundHandler#exceptionCaught}
 *  ChannelHandler 子接口
 *   Netty 提供两个最重要的 ChannelHandler 子接口
 *   {@link io.netty.channel.ChannelInboundHandler} 处理 inbound 数据和所有状态更改事件
 *   {@link io.netty.channel.ChannelOutboundHandler} 处理 outbound 数据, 允许拦截各种操作
 *  ChannelHandler 适配器
 *   Netty 提供一个简单的 ChannelHandler 的实现 {@link io.netty.channel.ChannelHandlerAdapter}
 *   这个类的方法主要推送事件到 pipeline 下个 ChannelHandler 直到 pipeline 的结束. 这个类作为
 *   {@link io.netty.channel.ChannelInboundHandlerAdapter}, {@link io.netty.channel.ChannelOutboundHandlerAdapter}
 *   的基础.  根据需要扩展指定方法.
 *  ChannelInboundHandler
 *   {@link io.netty.channel.ChannelInboundHandler#channelRegistered}
 *   {@link io.netty.channel.ChannelInboundHandler#channelActive}
 *   {@link io.netty.channel.ChannelInboundHandler#channelInactive}
 *   {@link io.netty.channel.ChannelInboundHandler#channelUnregistered}
 *   {@link io.netty.channel.ChannelInboundHandler#channelRead}
 *   {@link io.netty.channel.ChannelInboundHandler#channelReadComplete}
 *   {@link io.netty.channel.ChannelInboundHandler#channelWritabilityChanged}
 *      invoked when the writablility state of the Channel changes. The user can ensure
 *      writes are not done too fast(with rish of an OOM) or can resume writes when the Channel
 *      becomes writable again. Channel.isWritable() can be used to detect the actual writability
 *      of the channel. The threshold for writability can be set via Channel.config().setWriteHignWaterMark()
 *      and Channel.config().setWriteLowWaterMark().
 *   {@link io.netty.channel.ChannelInboundHandler#userEventTriggered}
 *      invoked when a user calls Channel.fireUserEventTriggerd(...) to pass a pojo through the
 *      ChannelPipeline. This can be used to pass user specific events through the ChannelPipeline
 *      and so allow handling those events.
 *    资源释放示例:
 *    {@link DiscardHandler} {@link org.anonymous.netty.NettyPreview.SimpleDiscardHandler}
 * -
 *  ChannelOutboundHandler
 *    出站. 可在请求时延迟操作或事件. 如, 在写数据到 remote-peer 的过程中被意外暂停, 可以延迟执行刷新操作, 然后在迟些时候继续.
 *    {@link io.netty.channel.ChannelOutboundHandler#bind}
 *    {@link io.netty.channel.ChannelOutboundHandler#connect}
 *    {@link io.netty.channel.ChannelOutboundHandler#disconnect}
 *    {@link io.netty.channel.ChannelOutboundHandler#close}
 *    {@link io.netty.channel.ChannelOutboundHandler#deregister}
 *    {@link io.netty.channel.ChannelOutboundHandler#read}
 *    {@link io.netty.channel.ChannelOutboundHandler#flush}
 *    {@link io.netty.channel.ChannelOutboundHandler#write}
 *    几乎所有方法都将 {@link io.netty.channel.ChannelPromise} 作为参数, 一旦请求结束要通过 {@link io.netty.channel.ChannelPipeline}
 *    转发的时候, 必须通知此参数.
 *   ChannelPromise 是特殊的 ChannelFuture, 允许 ChannelPromise 及其操作成功或失败. 所以任何时候调用
 *   {@link io.netty.channel.Channel#write} 方法, 一个新的 ChannelPromise 将会被创建(可参见源码流程),
 *   并通过 ChannelPipeline 传递. 写操作本身会返回 ChannelFuture, 这样只允许得到一次操作完成的通知.
 *   Netty 本身使用 ChannelPromise 作为返回的 ChannelFuture 的通知.
 *   -
 *   资源管理
 *   当通过 {@link io.netty.channel.ChannelInboundHandler#channelRead} 或者
 *   {@link io.netty.channel.ChannelOutboundHandler#write} 来处理数据, 重要的是在处理资源时要确保资源不要泄露.
 *   Netty 使用引用计数器来处理池化的 ByteBuf. 所以当 ByteBuf 完全处理后, 要确保引用计数器被调整.
 *   Netty 提供了 {@link io.netty.util.ResourceLeakDetector} 让用户更加简单的找到遗漏的释放, 从已分配的缓冲区
 *   取 1% 作为样品来检查是否存在应用程序泄露. 1% 抽样开销很小.
 *   泄露检测等级:
 *   {@link io.netty.util.ResourceLeakDetector.Level#DISABLED}
 *   {@link io.netty.util.ResourceLeakDetector.Level#SIMPLE}
 *   {@link io.netty.util.ResourceLeakDetector.Level#ADVANCED}
 *   {@link io.netty.util.ResourceLeakDetector.Level#PARANOID}
 *   修改检测等级: # java -Dio.netty.leakDetectionLevel=ADVANCED 或者 io.netty.leakDetection.level
 *   {@link io.netty.util.ResourceLeakDetector#PROP_LEVEL} {@link io.netty.util.ResourceLeakDetector#PROP_LEVEL_OLD}
 *   资源释放示例: {@link org.anonymous.netty.NettyPreview.DiscardOutboundHandler}
 * ---
 * ChannelPipeline
 *   {@link io.netty.channel.ChannelPipeline} 是一系列的 ChannelHandler 实例, 用于拦截流经一个 Channel 的 inbound/outbound 事件,
 *   ChannelPipeline 允许用户自己定义对 inbound/outbound 事件的处理逻辑, 以及 pipeline 里的各个 handler 之间的交互.
 *   每次新建 Channel, 都会新建一个 ChannelPipeline 并绑定到 Channel 上. 这个关联是永久性的; Channel 既不能附上另一个 ChannelPipeline
 *   也不能分离当前这个. 这些都由 Netty 负责完成, 而无需开发人员的特别处理.
 *   根据它的起源, 一个事件将由 ChannelInboundHandler/ChannelOutboundHandler 处理. 随后它将调用 ChannelHandlerContext 实现转发到
 *   下一个相同的超类型的处理程序.
 *  ChannelHandlerContext
 *    一个 ChannelHandlerContext 使 ChannelHandler 与 ChannelPipeline 和其他处理程序交互. 一个处理程序可以通知下一个 ChannelPipeline
 *    中的 ChannelHandler 甚至动态修改 ChannelPipeline 的归属.
 *    ChannelPipeline 是一系列的 ChannelHandler(inbound/outbound). 随着管道传播事件,它决定下个 ChannelHandler 是否是相匹配的方向运动的
 *    类型. 如果没有, ChannelPipeline 跳过 ChannelHandler 并继续下一个合适的方向. 一个处理程序可能同时实现 ChannelInboundHandler, ChannelOutboundHandler.
 *  修改 ChannelPipeline
 *    ChannelHandler 可以实时修改 ChannelPipeline 的布局, 通过 add/remove/replace 操作, 也可从 ChannelPipeline 移除 ChannelHandler 自身.
 *    示例: {@link org.anonymous.netty.NettyPreview.ModifyChannelPipeline}
 *  ChannelHandler 执行 ChannelPipeline 和阻塞
 *   通常每个 ChannelHandler 添加到 ChannelPipeline 将处理事件传递到 EventLoop(I/O线程). 至关重要的是不要阻塞这个线程, 它将会对
 *   整体的 I/O 处理产生负面影响. 有时可能需要使用阻塞 api 接口来处理遗留代码. 对此情形, ChannelPipeline 存在可指定 EventExecutorGroup 作为
 *   参数的 addXxx 方法. 如果一个定制的 EventExecutorGroup 传入事件将由含在这个 EventExecutorGroup 中的 EventExecutor 之一来处理,
 *   并且从 Channel 的 EventLoop 本身离开. 默认实现 {@link io.netty.util.concurrent.DefaultEventExecutorGroup}
 *   通过 ChannelPipeline 获取 ChannelHandlers
 *   {@link io.netty.channel.ChannelPipeline#get}
 *   {@link io.netty.channel.ChannelPipeline#context}
 *          A handler can have more than one {@link ChannelHandlerContext}
 *          a {@link ChannelHandler} instance can be added to more than
 *          one {@link ChannelPipeline}.  It means a single {@link ChannelHandler}
 *          instance can have more than one {@link ChannelHandlerContext} and therefore
 *          the single instance can be invoked with different
 *          {@link ChannelHandlerContext}s if it is added to one or more {@link ChannelPipeline}s more than once.
 *          Also note that a {@link ChannelHandler} that is supposed to be added to multiple {@link ChannelPipeline}s should
 *          be marked as {@link io.netty.channel.ChannelHandler.Sharable}.
 *    {@link io.netty.channel.ChannelPipeline#names()} Returns the {@link java.util.List} of the handler names.
 *    {@link io.netty.channel.ChannelPipeline#iterator()} Returns an iterator over elements of type {@code T}.
 * 发送事件
 *   ChannelPipeline API 有额外调用 inbound/outbound 操作的方法.
 *    Inbound operations on ChannelPipeline
 *     {@link io.netty.channel.ChannelPipeline#fireChannelRegistered()}
 *     {@link io.netty.channel.ChannelPipeline#fireChannelActive()}
 *     {@link io.netty.channel.ChannelPipeline#fireChannelInactive()}
 *     {@link io.netty.channel.ChannelPipeline#fireChannelUnregistered()}
 *     {@link io.netty.channel.ChannelPipeline#fireExceptionCaught(Throwable)}
 *     {@link io.netty.channel.ChannelPipeline#fireUserEventTriggered(Object)}
 *     {@link io.netty.channel.ChannelPipeline#fireChannelRead(Object)}
 *     {@link io.netty.channel.ChannelPipeline#fireChannelReadComplete()}
 *   Outbound operations on ChannelPipeline
 *     {@link io.netty.channel.ChannelPipeline#bind}
 *     {@link io.netty.channel.ChannelPipeline#connect}
 *     {@link io.netty.channel.ChannelPipeline#disconnect}
 *     {@link io.netty.channel.ChannelPipeline#close}
 *     {@link io.netty.channel.ChannelPipeline#deregister}
 *     {@link io.netty.channel.ChannelPipeline#flush}
 *     {@link io.netty.channel.ChannelPipeline#write}
 *     {@link io.netty.channel.ChannelPipeline#writeAndFlush}
 *     {@link io.netty.channel.ChannelPipeline#read}
 * 总结:
 *  1. 一个 ChannelPipeline 是用来保存关联到一个 Channel 的 ChannelHandler
 *  2. 可以修改 ChannelPipeline 通过动态添加和删除 ChannelHandler
 *  3. ChannelPipeline 有丰富的 API 调用动作来回应 inbound/outbound 事件.
 * -
 * ChannelHandlerContext
 *   接口 {@link io.netty.channel.ChannelHandlerContext} 代表 ChannelHandler 和 ChannelPipeline 之间的关联,
 *   并在 ChannelHandler 添加到 ChannelPipeline 使创建一个实例. ChannelHandlerContext 的主要功能是管理通过
 *   同一个 ChannelPipeline 关联的 ChannelHandler 之间的交互.
 *   ChannelHandlerContext 有许多方法,其中一些也出现在 Channel 和 ChannelPipeline 本身. 然而, 如果通过 Channel 或
 *   ChannelPipeline 的实例来调用这些方法, 它们就会在整个 pipeline 中传播. 相比之下, 一样的方法在 ChannelHandlerContext 的
 *   实例上调用, 就只会从当前的 ChannelHandler 开始并传播到相关管道中的下一个有处理事件能力的 ChannelHandler.
 *  ChannelHandlerContext
 *   {@link io.netty.channel.ChannelHandlerContext#bind}
 *   {@link io.netty.channel.ChannelHandlerContext#channel}
 *   {@link io.netty.channel.ChannelHandlerContext#close}
 *   {@link io.netty.channel.ChannelHandlerContext#connect}
 *   {@link io.netty.channel.ChannelHandlerContext#deregister}
 *   {@link io.netty.channel.ChannelHandlerContext#disconnect}
 *   {@link io.netty.channel.ChannelHandlerContext#executor}
 *   {@link io.netty.channel.ChannelHandlerContext#fireChannelActive}
 *   {@link io.netty.channel.ChannelHandlerContext#fireChannelInactive()}
 *   {@link io.netty.channel.ChannelHandlerContext#fireChannelRead(Object)}
 *   {@link io.netty.channel.ChannelHandlerContext#fireChannelReadComplete()}
 *   {@link io.netty.channel.ChannelHandlerContext#handler()}
 *   {@link io.netty.channel.ChannelHandlerContext#isRemoved()}
 *   {@link io.netty.channel.ChannelHandlerContext#name()}
 *   {@link io.netty.channel.ChannelHandlerContext#read()}
 *   {@link io.netty.channel.ChannelHandlerContext#pipeline()}
 *   {@link io.netty.channel.ChannelHandlerContext#write}
 *  其他注意事项:
 *   ChannelHandlerContext 与 ChannelHandler 的关联从不改变, 所以缓存它的引用是安全的.
 *   ChannelHandlerContext 所包含的事件流比其他类中同样的方法都要短, 利用这一点可以尽可能高的提高性能.
 * ---
 * 使用 ChannelHandler
 *   1. Channel 绑定到 ChannelPipeline
 *   2. ChannelPipeline 绑定到包含 ChannelHandler 的 Channel
 *   3. 当添加 ChannelHandler 到 ChannelPipeline 时, ChannelHandlerContext 被创建.
 *  示例: {@link org.anonymous.netty.NettyPreview.ChannelHandlerContextTest}
 *      {@link ChannelHandlerContextTest1}
 *    虽然在 Channel 或者 ChannelPipeline 上调用 write() 都会把事件整个管道传播, 但是在 ChannelHandler 级别上,
 *    从一个处理程序转到下一个却要通过在 ChannelHandlerContext 调用方法实现.
 *  -
 *  从 ChannelPipeline 中一个特定的 ChannelHandler 开始传播:
 *   · 通过减少 ChannelHandler 不感兴趣的事件的传递, 从而减少开销.
 *   要想实现从一个特定的 ChannelHandler 开始处理, 你必须引用于此 ChannelHandler 的前一个 ChannelHandler 关联的
 *   ChannelHandlerContext. 这个 ChannelHandlerContext 将会调用与自身关联的 ChannelHandler 的下一个 ChannelHandler.
 *   示例: {@link org.anonymous.netty.NettyPreview.ChannelPipelineTest}
 *  -
 *  ChannelHandler 和 ChannelHandlerContext 的高级用法
 *   通过调用 {@link io.netty.channel.ChannelHandlerContext#pipeline()}, 可以得到一个封闭的 ChannelPipeline 引用.
 *   这使得可以在运行时操作 pipeline 的 ChannelHandler, 这一点可以被利用来实现一些复杂的需求, 例如, 添加一个 ChannelHandler
 *   到 pipeline 来支持动态协议改变. 其他高级用例可以实现通过保持一个 ChannelHandlerContext 引用供以后使用, 这可能发生在任何
 *   ChannelHandler 方法, 甚至来自不同的线程.
 *   示例: {@link org.anonymous.netty.NettyPreview.WriteHandler}
 *   ChannelHandler 可以属于多个 ChannelPipeline, 可以绑定多个 ChannelHandlerContext 实例.
 *   然而, ChannelHandler 用于这种用法必须添加 {@link io.netty.channel.ChannelHandler.Sharable} 注解.
 *   否则, 试图将它添加到多个 ChannelPipeline 将引发一个异常. 此外, 它必须既是线程安全的又能安全地使用多个同时的通道(比如, 连接).
 *   示例: {@link org.anonymous.netty.NettyPreview.SharableHandler}
 *     {@link org.anonymous.netty.NettyPreview.NotSharableHandler}
 *     总之, 使用 @Sharable 必须用在线程安全的 ChannelHandler 上.
 *  为何共享 ChannelHandler
 *    常见原因是要在多个 ChannelPipelines 上安装一个 ChannelHandler 以此来实现多个跨渠道收集统计数据的目的.
 * ----
 * Codec 框架
 *   Decoder: 解码器
 *   Encoder: 编码器
 *   Codec: 编解码器
 *   编写一个网络应用程序需要实现某种 codec, codec 的作用就是将原始字节数据与目标程序数据格式进行互转. 网络中都是以字节码的数据形式来传输数据的,
 *   codec 由两部分组成: decoder/encoder.
 *   入站解码, 出站编码.
 *   encoder: outbound
 *   decoder: inbound
 *   消息被编/解码后, 会自动通过 {@link io.netty.util.ReferenceCountUtil#release} 释放, 如果不想释放消息可以使用
 *   {@link io.netty.util.ReferenceCountUtil#retain}, 这将会使引用数量增加而没有消息发布, 大多数时候不需要这么做.
 *   -
 * decoder:
 *  解码字节到消息: {@link io.netty.handler.codec.ByteToMessageDecoder} {@link io.netty.handler.codec.ReplayingDecoder}
 *  解码消息到消息: {@link io.netty.handler.codec.MessageToMessageDecoder}
 *  decoder 负责将 inbound 数据从一种数据转换到另一种格式, Netty-decoder 是一种 ChannelInboundHandler 的抽象实现.
 *  实践中使用解码器很简单, 就是将入站数据转换格式后传递到 ChannelPipeline 中的下一个 ChannelInboundHandler 进行处理;
 *  这样的处理是很灵活的, 我们可以将解码器放在 ChannelPipeling 中, 重用逻辑.
 * ByteToMessageDecoder
 *   将字节转为消息(或其他字节序列). 你不能确定远端是否会一次发送完一个完整的 "信息", 因此这个类会缓存入站的数据, 直到准备好了用于处理.
 *   {@link io.netty.handler.codec.ByteToMessageDecoder#decode}
 *      This is the only abstract method you need to implement. It's called with a ByteBuf having the incoming bytes
 *      and a List into which decoded messages are added. decode() is called repeatedly until the List is empty
 *      on return. The contents of the List are then passed to the next handler iin the pipeline.
 *   {@link io.netty.handler.codec.ByteToMessageDecoder#decodeLast}
 *      The default implementation provided simply calls decode(). This method is called once, when the Channel goes
 *      inactive. Override to provide special handling.
 *   假设我们接收一个包含简单整数的字节流, 每个都单独处理. 在示例 {@link org.anonymous.netty.NettyPreview.ToIntegerDecoder} 中,
 *   从入站的 ByteBuf 读取每个整数并将其传递给 pipeline 中的下一个 ChannelInboundHandler. 每次入站的 ByteBuf 读取四个字节, 解码成整型,
 *   并添加到一个 List, 当不能再添加数据到 list 时, 它所包含的内容就会被发送到下个 ChannelInboundHandler.
 *   尽管 ByteToMessageDecoder 简化了整个模式, 但在实际的读操作(如示例中的 readInt 方法)之前, 必须要验证输入的 ByteBuf 要有足够的数据.
 *   注: 对于 编解码器, 一旦一个消息被编码或解码自动调用 {@link io.netty.util.ReferenceCountUtil#release}. 如果稍后还需要用到这个引用
 *   而不是马上释放, 你可以调用 {@link io.netty.util.ReferenceCountUtil#retain}, 这将增加引用计数, 防止消息被释放.
 * ReplayingDecoder
 *   {@link io.netty.handler.codec.ReplayingDecoder} 是 byte-to-message 解码的一种特殊的抽象基类, 读取缓冲区的数据之前需要检查
 *   缓冲区是否有足够的字节, 使用 ReplayingDecoder 就无需自己检查: 若 ByteBuf 中有足够的字节, 则会正常读取; 若没有足够的字节则会停止解码.
 *   ReplayingDecoder 继承自 ByteToMessageDecoder, ReplayingDecoder 带有一定局限性:
 *   - 不是所有 ByteBuf 操作都被支持, 如果调用一个不支持的操作会抛出 异常
 *   - ReplayingDecoder 略慢于 ByteToMessageDecoder.
 *   示例: {@link org.anonymous.netty.NettyPreview.ToIntegerDecoder2}
 *  更复杂的 Decoder: {@link io.netty.handler.codec.LineBasedFrameDecoder} 通过结束控制符("\n","\r\n");
 *  {@link io.netty.handler.codec.http.HttpObjectDecoder} 用于 HTTP 数据解码.
 * -
 * MessageToMessageDecoder
 *   用于从一种消息解码为另外一种消息(如 POJO 到 POJO).
 *   {@link io.netty.handler.codec.MessageToMessageDecoder#decode} decode is the only abstract method you
 *   need to implement. It is called for each inbound message to be decoded to another format. The decoded
 *   messages are then passed to the next ChannelInboundHandler in the pipeline.
 *   示例: {@link org.anonymous.netty.NettyPreview.IntegerToStringDecoder}
 *  更多复杂的示例, 参见 {@link io.netty.handler.codec.http.HttpObjectAggregator}
 * -
 * 在解码时处理太大的帧
 *  Netty 是异步框架, 需要缓冲区字节在内存中, 直到你能够解码它们. 因此, 你不能让你的解码器缓存太多的数据以免耗尽可用内存.
 *  为了解决这个共同关心的问题, Netty 提供了 {@link io.netty.handler.codec.TooLongFrameException}, 通常由解码器在
 *  帧太长时抛出.
 *  为了避免这个问题, 你可以在你的解码器里设置一个最大字节数阈值, 如果超出, 将导致 {@link io.netty.handler.codec.TooLongFrameException}
 *  抛出, 并由 {@link io.netty.channel.ChannelInboundHandler#exceptionCaught} 捕获. 然后由译码器的用户决定如何处理它.
 *  虽然一些协议, 比如 HTTP 允许这种情况下有一个特殊的响应, 有些可能没有, 事件唯一的选择可能就是关闭连接.
 *  示例: {@link org.anonymous.netty.NettyPreview.SafeByteToMessageDecoder}
 * ---
 * encoder:
 *  encoder 是用来把出站数据从一种格式转换到另一种格式, 因此它实现了 {@link io.netty.channel.ChannelOutboundHandler}.
 *  编码从消息到字节: {@link io.netty.handler.codec.MessageToByteEncoder}
 *  编码从消息到消息: {@link io.netty.handler.codec.MessageToMessageEncoder}
 * MessageToByteEncoder:
 *  与 ByteToMessageDecoder 相反.
 *  {@link io.netty.handler.codec.MessageToByteEncoder#encode} The encode method is the only abstract method
 *  you need to implement. It is called with the outbound message, which this class will encodes to a ByteBuf.
 *  The ByteBuf is then forwarded to the next ChannelOutboundHanlder in the ChannlePipeline.
 *  此类只有一个主要方法, 而 ByteToMessageDecoder 却有 decode/decodeLast 方法, 原因就是 decoder 经常需要在 Channel 关闭时
 *  产生一个 "最后的消息".
 *  示例: {@link org.anonymous.netty.NettyPreview.ShortToByteEncoder}
 *  Netty 提供了很多 MessageToByteEncoder 类来帮助你的实现自己的 encoder. 可参考实现:
 *  {@link io.netty.handler.codec.http.websocketx.WebSocket08FrameEncoder}
 * MessageToMessageEncoder:
 *  {@link io.netty.handler.codec.MessageToMessageEncoder#encode} The encode method is the only abstract method
 *  you need to implement. It is called for each message written with write(...) to encode the message to one
 *  or multiple new outbound messages. The encoded messages are then forwarded.
 *  示例: {@link org.anonymous.netty.NettyPreview.IntegerToStringEncoder}
 *  更复杂的 MessageToMessageEncoder 应用案例, 可以查看 {@link io.netty.handler.codec.protobuf.ProtobufEncoder}
 * ----
 * 抽象 Codec(编解码器)类
 *  虽然我们一直把编解码器作为不同的实体来讨论, 但你有时可能会发现把入站和出站的数和信息转换都放在同一个类中更实用. Netty 的抽象
 *  编解码器类就是用于这个目的, 它们把一些成对的编解码器组合在一起, 以此来提供对于字节和消息都相同的操作. 这些类实现了
 *  {@link io.netty.channel.ChannelInboundHandler} 和 {@link io.netty.channel.ChannelOutboundHandler}
 *  您可能想知道是否有时候使用单独的解码器和编码器会比使用这些组合类要好, 最简单的答案是, 紧密耦合的两个函数减少了它们的可重用性,
 *  但是把它们分开就会更容易扩展. 当我们研究抽象编解码器类时, 我们也会拿它和对应的独立的解码器和编码器做对比.
 * ByteToMessageCodec
 *  我们需要解码字节到消息, 也许是一个 POJO, 然后转回来. {@link io.netty.handler.codec.ByteToMessageCodec} 将为我们处理
 *  这个问题, 因为它结合了 ByteToMessageDecoder 和 MessageToByteEncoder.
 *  {@link io.netty.handler.codec.ByteToMessageCodec#decode}
 *      This method is called as long as bytes are available to be consumed. It converts the inbound ByteBuf
 *      to the specified message format and forwards them to the next ChannelInboundHanlder in the pipeline.
 *  {@link io.netty.handler.codec.ByteToMessageCodec#decodeLast}
 *      The default implementation of this method delegates to decode(). It is called only be called once,
 *      when the Channel goes inactive. For special handling it can be overrided.
 *  {@link io.netty.handler.codec.ByteToMessageCodec#encode}
 *      This method is called for each message to be written through the ChannelPipeline.
 *      The encoded messages are contained in a ByteBuf.
 *  什么会是一个好的 ByteToMessageCodec 用例? 任何一个 request/response 都可能是, 例如 SMTP.
 *  编解码器将读取入站字节并解码到一个自定义的消息类型 {@link io.netty.handler.codec.smtp.SmtpRequest}.
 *  当接收到一个 {@link io.netty.handler.codec.smtp.SmtpResponse} 会产生, 用于编码为字节进行传输.
 * -
 * MessageToMessageCodec
 *  MessageToMessageEncoder 从一个消息格式转换为另一种消息格式. 那么 MessageToMessageCodec 如何处理单个类的往返.
 *  {@link io.netty.handler.codec.MessageToMessageCodec#decode}
 *      This method is called with the inbound messages of the codec and decodes them to messages.
 *      Those messages are forwarded to the next ChannelInboundHandler in the ChannelPipeline.
 *  {@link io.netty.handler.codec.MessageToMessageCodec#encode}
 *      The encode method is called for each outbound message to be moved through the ChannelPipeline.
 *      The encoded messages are forwarded to the next ChannelOutboundHandler in the pipeline.
 *  在现实中, 往往涉及两个来回转换的数据消息传递 API. 这是常有的, 当我们不得不与遗留或专有的消息格式进行互操作.
 *  示例: {@link org.anonymous.netty.NettyPreview.WebSocketConvertHandler} 类型互转, 该示例比较典型.
 * -
 * CombinedChannelDuplexHandler
 *  如前所述, 结合编解码器可能会牺牲可重用性. 为了避免这种情况, 部署一个编解码器到 ChannelPipeline 作为逻辑单元而不失便利性很有必要.
 *  此即 {@link io.netty.channel.CombinedChannelDuplexHandler} 即解决此问题. 这个类扩展 ChannelInboundHandler 和
 *  ChannelOutboundHandler 参数化的类型. 这提供了一个容器, 单独的解码器和编码器类合作而无需直接扩展抽象的编解码器类.
 *  示例:
 *   不使用 codec 的方式: 需要两个类 {@link org.anonymous.netty.NettyPreview.ByteToCharDecoder},
 *                               {@link org.anonymous.netty.NettyPreview.CharToByteEncoder}
 *   使用 codec: 结合 inbound/outbound {@link org.anonymous.netty.NettyPreview.CombinedByteCharCodec}
 * ----
 * 已经提供的 ChannelHandler 和 Codec
 * Netty 提供了很多共同协议的编解码器和处理程序, 你可以几乎 "开箱即用" 的使用它们, 而无需花在相当乏味的基础设施问题.
 * 以下将探索这些工具和它们的优点. 包括支持 SSL/TLS, WebSocket 和 Google-SPDY, 通过数据压缩使 HTTP 有更好的性能.
 * -
 * 使用 SSL/TLS(secure socket layer/transport layer security) 加密 Netty 程序
 * 为了数据隐私, 至少需要熟悉加密协议 SSL/TLS 等之上的其他协议实现数据安全. 作为一个 HTTPS(HTTP + SSL/TLS) 网站, 是安全的,
 * 但有些不基于此的程序, 如 安全 SMTP(SMTPS) 邮件服务, 甚至关系数据库系统.
 * 为了支持 SSL/TLS, Java 提供了 {@link javax.net.ssl.SSLContext} 和 {@link javax.net.ssl.SSLEngine} 使它相对简单
 * 的实现加密解密. Netty 的利用该 API 命名 {@link io.netty.handler.ssl.SslHandler} 的 ChannelHandler 实现,
 * 有一个内部 SslEngine 做实际的工作.
 * 示例: {@link org.anonymous.netty.NettyPreview.SslChannelInitializer}
 * 在大多数情况下, SslHandler 将成为 ChannelPipeline 中的第一个 ChannelHandler. 这将确保所有其他 ChannelHandler 应用
 * 它们的逻辑到数据后加密后才发生, 从而确保它们的变化是安全的.
 * SslHandler 有很多有用的方法. 例如, 在握手阶段两阶段相互验证, 商定一个加密方法. 可以配置 SslHandler 修改其行为或提供在
 * SSL/TLS 握手完成后发送通知, 这样所有数据都将被加密. SSL/TLS 握手将自动执行.
 * {@link io.netty.handler.ssl.SslHandler#setHandshakeTimeout}
 * {@link io.netty.handler.ssl.SslHandler#setHandshakeTimeoutMillis}
 * {@link io.netty.handler.ssl.SslHandler#getHandshakeTimeoutMillis()}
 *        Set and get the timeout, after which the handshake ChannelFuture is notified of failure.
 * {@link io.netty.handler.ssl.SslHandler#setCloseNotifyFlushTimeout}
 * {@link io.netty.handler.ssl.SslHandler#setCloseNotifyFlushTimeoutMillis}
 * {@link io.netty.handler.ssl.SslHandler#getCloseNotifyFlushTimeoutMillis()}
 * {@link io.netty.handler.ssl.SslHandler#getCloseNotifyReadTimeoutMillis()}
 * {@link io.netty.handler.ssl.SslHandler#getCloseNotifyTimeoutMillis()}
 *        Set and get the timeout after which the close notify will time out and the connection will close.
 *        This also results in having the close notify ChannelFuture fail.
 *  {@link io.netty.handler.ssl.SslHandler#handshakeFuture()}
 *         Returns a ChannelFuture that will be notified once the handshake is complete. If the handshake
 *         was done before it will return a ChannelFuture that contains the result of the previous handshake.
 *  {@link io.netty.handler.ssl.SslHandler#close}
 *  {@link io.netty.handler.ssl.SslHandler#closeOutbound}
 *         Send the close_notify to request close and destory the underlying SslEngine.
 * -
 * 构建 Netty HTTP/HTTPS 应用
 * HTTP Decoder, Encoder, Codec
 *  HTTP 是 request-response 模式, client 发送一个 HTTP 请求, server 响应此请求. Netty 提供了简单的编解码器来
 *  简化基于此协议的开发工作.
 *  在 Netty 中, 一个完整的 request/response 分别用 {@link io.netty.handler.codec.http.FullHttpRequest},
 *  {@link io.netty.handler.codec.http.FullHttpResponse} 表示.
 *  HTTP request/response 由多个部分组成,
 *      第一部分为 头信息: 请求/响应头
 *          {@link io.netty.handler.codec.http.HttpRequest}/{@link io.netty.handler.codec.http.HttpResponse}
 *      第二部分为 数据部分: 可能有多个 HttpContent 部分
 *          {@link io.netty.handler.codec.http.HttpContent}
 *      第三部分为 结束部分: 标记 HTTP request/response 的结束, 可能同时包含 请求/响应头的尾部信息
 *          {@link io.netty.handler.codec.http.LastHttpContent}
 *  所有类型的 HTTP 消息 实现 {@link io.netty.handler.codec.http.HttpObject}
 *  {@link io.netty.handler.codec.http.HttpRequestEncoder} Encodes HttpRequest, HttpContent and LastHttpContent
 *          messages to bytes.
 *  {@link io.netty.handler.codec.http.HttpResponseEncoder} Encodes HttpResponse, HttpContent and LastHttpContent
 *          messages to bytes.
 *  {@link io.netty.handler.codec.http.HttpRequestDecoder} Decodes bytes into HttpRequest, HttpContent and
 *          LastHttpContent messages.
 *  {@link io.netty.handler.codec.http.HttpResponseDecoder}  Decodes bytes into HttpReonse, HttpContent and
 *          LastHttpContent messages.
 *  示例: {@link org.anonymous.netty.NettyPreview.HttpPipelineInitializer}
 * -
 * HTTP 消息聚合
 *  安装 ChannelPipeline 中的初始化之后, 你能够对不同 HttpObject 消息进行操作. 但由于 HTTP 请求/响应可以由许多部分组合而成,
 *  你需要聚合它们形成完整的消息. 为了消除这种繁琐任务, Netty 提供了一个聚合器, 合并消息部件到
 *  {@link io.netty.handler.codec.http.FullHttpRequest}, {@link io.netty.handler.codec.http.FullHttpResponse}
 *  消息. 这样总是能看到完整的消息内容.
 *  这个操作有轻微的成本, 消息段需要缓冲, 直到完全可以将消息转发到下一个 ChannelInboundHanlder 管道. 但好处是, 不必担心消息碎片.
 *  实现自动聚合只需添加另一个 ChannelHandler 到 ChannelPipeline.
 *  示例: {@link org.anonymous.netty.NettyPreview.HttpAggregatorInitializer}
 * -
 * HTTP 压缩
 *   使用 HTTP 时建议压缩数据以减少传输流量, 压缩数据会增加 CPU 负载, 现代硬件设施都很强大, 大多数时候压缩数据是一个好主意.
 *   Netty 支持 gzip 和 deflate, 为此提供了两个 ChannelHandler 实现分别用于压缩和解压.
 *   示例: {@link org.anonymous.netty.NettyPreview.HttpAggregatorInitializer1}
 * -
 * 使用 HTTPS
 * 启用 HTTPS, 只需添加 SslHandler
 * 示例: {@link org.anonymous.netty.NettyPreview.HttpsCodecInitializer}
 * --
 * WebSocket
 * Http 是不错的协议, 但是如果需要实时发布信息怎么做? (server 主动向 client 发送消息)
 * 有个做法就是客户端一直轮询请求服务器, 这种方式虽然可以达到目的, 但是其缺点很多, 也不是优秀的解决方案, 为了解决这个问题,
 * 便出现了 WebSocket.
 * WebSocket 允许数据双向传输, 而不需要 request-response 模式(没有严格意义的 client/server 的区分).
 * 早期的 WebSocket 只能发送文本数据, 然后现在不仅可以发送文本数据, 也可以发送二进制数据, 这使得可以使用 WebSocket 构建
 * 你想要的程序.
 * real-time web 是一组技术和实践, 使用户能够实时地接收到作者发布的信息, 而不需要用户用他们的软件定期检查更新 resources.(即服务器端主动推送)
 *
 * HTTP 的 request/response 设计并不能满足实时需求, 而 WebSocket 协议从设计以来就提供双向数据传输, 允许客户和服务器在任何时间发送消息,
 * 并要求它们能够异步处理消息.
 * WebSocket 使用一种被称为 Upgrade handshake(升级握手)机制将标准 HTTP/HTTPS 协议转为 WebSocket. 因此, 使用 WebSocket 的应用程序
 * 将始终以 HTTP(s) 开始, 然后进行升级. 这种升级发生在什么时候取决于具体的应用, 可以在应用启动的时候, 或者当一个特定的 URL 被请求的时候.
 * 在我们的应用中, 仅当 URL 请求以 "/ws" 结束时, 我们才升级协议为 WebSocket. 否则, 服务器将使用基本的 HTTP(S). 一旦连接升级,
 * 之后的数据传输都将使用 WebSocket. 示例: {@link org.anonymous.netty.NettyPreview.HttpRequestHandler1}
 *
 * WebSocket 规范及其实现是为了一个更有效的解决方案. 简单的说, 一个 WebSocket 提供一个 TCP 连接两个方向的交通.
 * 结合 WebSocket API 它提供了一个替代 HTTP 轮询双向通信从页面到远程服务器.
 * 也就是说 WebSocket 提供真正的双向 client/server 之间的数据交换. WebSocket 目前可以用于任意数据, 就像一个正常的套接字.
 * WebSocket 协议流程:
 *  1. Client(HTTP) 与 Server 通讯
 *  2. Server(HTTP) 与 Client 通讯
 *  3. Client 通过 HTTP(s) 来进行 WebSocket 握手, 并等待确认
 *  4. 连接协议升级至 WebSocket
 * WebSocket protocol
 *  添加应用程序支持 WebSocket 只需要添加适当的 client/server WebSocket ChannelHandler 到管道. 这个类将处理特殊
 *  WebSocket 定义的消息类型, 称为 "帧".
 *  WebSockets 利用帧发送消息, 其中每一个都代表一个消息的一部分. 一个完整的消息可以利用多个帧.
 *  WebSocket-RFC 定义了六种不同的 frame; Netty 给它们每个都提供了一个 POJO 实现.
 * @see io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame Data frame: binary data
 * @see io.netty.handler.codec.http.websocketx.TextWebSocketFrame Data frame: text data
 * @see io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame Data frame:
 *      text or binary data that belongs to a previous BinaryWebSocketFrame or TextWebSocketFrame
 * @see io.netty.handler.codec.http.websocketx.CloseWebSocketFrame Control frame:
 *      a CLOSE request, close status code and a phrase
 * @see io.netty.handler.codec.http.websocketx.PingWebSocketFrame Control frame:
 *      requests the send of a PongWebSocketFrame
 * @see io.netty.handler.codec.http.websocketx.PongWebSocketFrame Control frame:
 *      sent as response to a PingWebSocketFrame
 * 由于 Netty 是一个服务器端技术, 重点是创建一个 WebSocket server.
 *  基本示例: {@link org.anonymous.netty.NettyPreview.WebSocketServerInitializer}
 *  示例:
 *     {@link org.anonymous.netty.NettyPreview.TextWebSocketFrameHandler}
 *     {@link org.anonymous.netty.NettyPreview.ChatServerInitializer}
 *
 * --
 * SPDY(发音: speedy, SPDY 是 Google 的一个商标, 不是首字母缩写)
 *  Google 开发的基于 TCP 的应用层协议, 操纵 HTTP 流量, 用以最小化网络延迟, 提升网络速度, 网络安全, 优化用户的网络使用体验.
 *  SPDY 并不是一种用于替代 HTTP 的协议, 而是对 HTTP 协议的增强. SPDY 实现技术:
 *    · 压缩报文头
 *    · 加密所有
 *    · 多路复用连接
 *    · 提供支持不同的传输优先级
 * Google 开发 SPDY 是为了解决扩展性问题, 主要任务是加载内容的速度更快, 做了如下工作:
 *   · 每个头都是压缩的, 消息体的压缩是可选的, 因为它可能对代理服务器有问题
 *   · 所有的加密都使用 TLS 每个连接设置多个转移是可能的, 数据集可以单独设置优先级, 使关键内容先被转移
 * SPDY 主要有 5 个版本:
 *    1. 初始化版本, 未使用
 *    2. 新特性, 包含服务器推送
 *    3. 新特性包含流控制和更新压缩
 *    3.1. 会话层流程控制
 *    4.0. 流量控制, 并于 HTTP 2.0 更加集成
 * SPDY 被很多浏览器支持, 包括 Google Chrome, Firefox, Opera.
 * 如果浏览器不支持 SPDY, 则会自动使用 HTTP(s).
 * SPDY 使用 TLS 的扩展称为 Next Protocol Negotiation(NPN). 在 Java 中, 有两种不同的方式选择的基于 NPN 的协议:
 *   · 使用 ssl_npn, NPN 的开源 SSL 提供者
 *   · 使用通过 Jetty 的 NPN 扩展库
 *   如果使用 ssl_npn, 请参考 https://github.com/benmmurphy/ssl_npn 项目文档.
 *  此处示例使用 Jetty NPN 库, Jetty-NPN 库是一个外部库, 而不是 Netty 本身的一部分. 它用于处理 Next Protocol Negotiation,
 *  这是用于检测 client 是否支持 SPDY.
 * 集成 Next Protocol Negotiation (NPN)
 *   Jetty 库提供了一个接口称为 ServerProvider, 确定所使用的协议和选择哪个 hook. 这个实现可能取决于不同版本的 HTTP 和 SPDY
 *   版本的支持.
 *   示例: {@link org.anonymous.netty.NettyPreview.DefaultServerProvider}
 * 实现各种 ChannelHandler
 *  第一个 ChannelInboundHandler 是用于不支持 SPDY 的情况下处理客户端 HTTP 请求, 如果不支持 SPDY 就回滚使用默认的 HTTP 协议.
 *  示例: {@link org.anonymous.netty.NettyPreview.HttpRequestHandler}
 *  第二个示例组件支持 SPDY 作为首选协议. Netty 提供了简单的处理 SPDY 方法. 这些将使你能重用 FullHttpRequest/FullHttpResponse 消息,
 *  通过 SPDY 透明的接收和发送它们.
 *  HttpRequestHandler 虽然是可以重用的代码, 我们将改变我们的内容写回客户端只是强调协议的变化; 通常您会返回相同的内容.
 *  示例: {@link org.anonymous.netty.NettyPreview.SpdyRequestHandler}
 *  可以实现两个处理程序逻辑, 将选择一个相匹配的协议. 然而添加以前写过的处理程序到 ChannelPipeline 是不够的; 正确的 编解码器还需要补充.
 *  它的责任是检测传输字节数, 然后使用 FullHttpResponse 和 FullHttpRequest 的抽象进行工作.
 *  Netty 附带一个基类, 完全能做这个. 所有需要做的是实现逻辑选择协议和选择适当的处理程序.
 *  示例: {@link org.anonymous.netty.NettyPreview.DefaultSpdyOrHttpChooser}
 * 设置 ChannelPipeline
 *  通过实现 ChannelInitializer 将所有的处理器连接到一起. 这将设置 ChannelPipeline 并添加所有需要的 ChannelHandler.
 *  SPDY 需要两个 ChannelHandler:
 *   · SslHandler, 用于检测 SPDY 是否通过 TLS 扩展
 *   · {@link org.anonymous.netty.NettyPreview.DefaultSpdyOrHttpChooser}, 用于当前协议被检测到时, 添加正确的
 *     ChannelHandler 到 ChannelPipeline.
 *   除了添加 ChannelHandler 到 ChannelPipeline, ChannelInitializer 还有另一个职责, 即分配之前创建的 DefaultServerProvider
 *   通过 SslHandler 到 SslEngine. 这将通过 Jetty-NPN 类库的 {@link org.eclipse.jetty.npn.NextProtoNego} 实现.
 *   整合示例: {@link org.anonymous.netty.NettyPreview.SpdyServer}
 * --
 * 空闲连接及超时
 *   检测空闲连接和超时是为了及时释放资源. 常见的方法发送消息用于测试一个不活跃的连接到远端来确定它是否还活着, 通常称为 "心跳".
 *   一个更激进的方法是简单的断开那些指定的时间间隔的不活跃连接.
 *   处理空闲连接是一项常见的任务, Netty 提供了几个 ChannelHandler 实现此目的.
 *   ChannelHandlers for idle connections and timeouts
 *   {@link io.netty.handler.timeout.IdleStateHandler} 如果连接闲置时间过长, 则会触发 {@link io.netty.handler.timeout.IdleStateEvent}
 *      事件. 覆盖 {@link io.netty.channel.ChannelInboundHandler#userEventTriggered} 方法来处理 IdleStateEvent.
 *   {@link io.netty.handler.timeout.ReadTimeoutHandler} 在指定事件间隔内没有接收到入站数据则会抛出 {@link io.netty.handler.timeout.ReadTimeoutException}
 *      并关闭 Channel. ReadTimeoutException 可以通过覆盖 {@link io.netty.channel.ChannelInboundHandler#exceptionCaught} 方法检测到.
 *   {@link io.netty.handler.timeout.WriteTimeoutHandler} {@link io.netty.handler.timeout.WriteTimeoutException} 可以通过覆盖
 *      {@link io.netty.channel.ChannelInboundHandler#exceptionCaught} 方法检测到.
 *   示例: {@link org.anonymous.netty.NettyPreview.IdleStateHandlerInitializer}
 * --
 * 解码分隔符和基于长度的协议
 *   使用 Netty 时会遇到需要解码以分隔符和长度为基础的协议.
 *  分隔符协议
 *   经常需要处理分隔符协议或创建基于它们的协议, 例如 SMTP, POP3, IMAP, Telnet 等.
 *   Netty 附带的解码器可以很容易的提取一些序列分隔:
 *   {@link io.netty.handler.codec.DelimiterBasedFrameDecoder} 接收 ByteBuf 由一个或多个分隔符拆分, 如 NUL 或换行符.
 *   {@link io.netty.handler.codec.LineBasedFrameDecoder} 接收 ByteBuf 以换行符结束, 如 "\n", "\r\n".
 *   示例: {@link org.anonymous.netty.NettyPreview.LineBasedHandlerInitializer}
 *        {@link org.anonymous.netty.NettyPreview.CmdHandlerInitializer}
 *  基于长度的协议
 *    基于长度的协议在帧头文件里定义了一个帧编码的长度, 而不是结束位置用一个特殊的分隔符来标记.
 *    Netty 提供了两个解码器, 用于处理这种类型的协议.
 *    Decoders for length-based protocols
 *    {@link io.netty.handler.codec.FixedLengthFrameDecoder} 提取固定长度: 构造方法指定长度
 *    {@link io.netty.handler.codec.LengthFieldBasedFrameDecoder} 读取头部指定的长度并提取帧的长度: 头部指定长度
 *    示例: {@link org.anonymous.netty.NettyPreview.LengthBasedInitializer}
 * ---
 * 写大型数据
 *   由于网络原因, 如何有效的写大数据在异步框架是一个特殊的问题. 因为写操作是非阻塞的, 即便是在数据不能写出时, 只是通知
 *   ChannelFuture 完成了. 当这种情况发生时, 你必须停止写操作或面临内存耗尽的风险. 所以写大量数据时, 我们需要做好准备
 *   来处理的这种情况下缓慢连接远端导致延迟释放内存的问题.
 *   参考示例: 写一个文件的内容到网络 {@link org.anonymous.netty.NettyPreview.FileRegionInitializer}
 * @see io.netty.handler.stream.ChunkedFile 当你使用的平台不支持 zero-copy 或者你需要转换数据, 从文件中一块一块的获取数据
 * @see io.netty.handler.stream.ChunkedNioFile 于 ChunkedFile 类似, 处理使用了 NIOFileChannel
 * @see io.netty.handler.stream.ChunkedStream 从 InputStream 中一块一块的转移内容
 * @see io.netty.handler.stream.ChunkedNioStream 从 {@link java.nio.channels.ReadableByteChannel} 中一块一块的转移内容
 * 示例: {@link org.anonymous.netty.NettyPreview.ChunkedWriteHandlerInitializer}
 *  使用 {@link ChunkedWriteHandler} 写大型数据避免 OOM.
 *  使用 ChunkedStream, 当 initChannel() 被调用来初始化显示的处理程序链的通道.
 *  当通道激活时, WriteStreamHandler 从文件一块一块的写入数据作为 ChunkedStream.
 *  最后通过 SslHandler 加密后传播.
 * ---
 * 序列化数据
 *   JDK 提供了 {@link java.io.ObjectInputStream} {@link java.io.ObjectOutputStream} 通过网络将原始数据类型
 *   和 POJO 进行序列化和反序列化. API 并不复杂, 可以应用到实现 {@link java.io.Serializable} 接口的任何对象.
 *   但它也不是非常高效.
 *   NettY 提供了与 JDK 的互操作
 *   {@link io.netty.handler.codec.serialization.CompatibleObjectEncoder} 该编码器使用 JDK 序列化,
 *     用于与非 Netty 进行互操作.
 *   {@link io.netty.handler.codec.serialization.ObjectDecoder} 基于 JDK 序列化来使用自定义序列化解码.
 *     外部依赖被排除在外时, 提供了一个速度提升. 否则选择其他序列化实现.
 *   {@link io.netty.handler.codec.serialization.ObjectEncoder} 基于 JDK 序列化来使用自定义序列化编码.
 *     外部依赖被排除在外时, 提供了一个速度提升. 否则选择其他序列化实现.
 * JBoss Marshalling 序列化
 *   可以使用 JBoss Marshalling 序列化, 比 JDK 序列化快且更简练. 与 {@link java.io.Serializable} 兼容.
 *   并添加一些新的可调参数和附加功能.
 *   {@link io.netty.handler.codec.marshalling.CompatibleMarshallingDecoder}
 *       为了与使用 JDK 序列化的端对端间兼容.
 *   {@link io.netty.handler.codec.marshalling.CompatibleMarshallingEncoder}
 *       为了与使用 JDK 序列化的端对端间兼容.
 *   {@link io.netty.handler.codec.marshalling.MarshallingDecoder}
 *       使用自定义序列化用于解码, 必须与 MarshallingEncoder 共用.
 *   {@link io.netty.handler.codec.marshalling.MarshallingEncoder}
 *       使用自定义序列化用于编码, 必须与 MarshallingDecoder 共用.
 *  示例: {@link org.anonymous.netty.NettyPreview.MarshallingInitializer}
 * ProtoBuf 序列化
 *   ProtoBuf 来自谷歌, 开源. 它使编解码数据更加紧凑和高效. 支持多语言, 适合跨语言项目.
 *   {@link io.netty.handler.codec.protobuf.ProtobufDecoder} 使用 ProtoBuf 解码消息
 *   {@link io.netty.handler.codec.protobuf.ProtobufEncoder} 使用 ProtoBuf 编码消息
 *   {@link io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder} 在消息的整型长度域中, 通过
 *      "Base 128 Varints" 将接收到的 ByteBuf 动态分割.
 *   示例: {@link org.anonymous.netty.NettyPreview.ProtoBufInitializer}
 * -----
 * Bootstap
 *   ChannelPipeline, ChannelHandler, Codec 提供工具, 可以处理一个广泛的数据处理需求. 但是, 我们创建了组件之后,
 *   如何将其组装成一个应用程序? 答案是 bootstrapping. 简单的理解, 引导就是配置应用程序的过程.
 *   Netty 包括两种不同类型的引导. 而不仅仅是当作 client/server 的引导, server 应用程序使用一个 parent-channel
 *   接受连接和创建 children-channel, 而 client 很可能只需要一个单一的、非 parent 对所有网络交互的管道(对无连接的比如 UDP 协议也是一样).
 *  基类: {@link io.netty.bootstrap.AbstractBootstrap}
 *   我们经常需要创建多个通道具有相似或者相同的设置. 支持这种模式不需要为每个通道创建和配置一个新的引导实例, AbstractBootstrap
 *   已经实现 {@link java.lang.Cloneable}. 调用 {@link io.netty.bootstrap.AbstractBootstrap#clone()} 返回一个
 *   已经配置好的 bootstrap, 且是立即可用的. 注意, 因为这将创建的只是 {@link io.netty.channel.EventLoopGroup} 的浅拷贝,
 *   后者将会共享所有克隆管道. 这是可接受的, 因为往往克隆的管道往往是 short-lived(典型示例是管道创建用于 HTTP 请求).
 * 引导客户端和无连接协议
 *   当需要引导客户端或一些无连接协议时, 需要使用 {@link io.netty.bootstrap.Bootstrap} 类.
 *   {@link io.netty.bootstrap.Bootstrap#group(io.netty.channel.EventLoopGroup)} 设置 EventLoopGroup
 *          用于处理所有的 Channel 事件.
 *   {@link io.netty.bootstrap.Bootstrap#channel}
 *   {@link io.netty.bootstrap.Bootstrap#channelFactory(io.netty.channel.ChannelFactory)}
 *          channel() 指定 Channel 的实现类. 如果类没有提供一个默认的构造函数, 可以调用 channelFactory()
 *          来指定一个工厂被 bind() 调用.
 *   {@link io.netty.bootstrap.Bootstrap#localAddress(java.net.SocketAddress)}
 *   {@link io.netty.bootstrap.Bootstrap#localAddress(int)}
 *   {@link io.netty.bootstrap.Bootstrap#localAddress(String, int)}
 *   {@link io.netty.bootstrap.Bootstrap#localAddress(java.net.InetAddress, int)}
 *          指定应该绑定到本地地址 Channel. 如果不提供, 将由操作系统创建一个随机的.
 *          或者, 可以使用 bind/connect 指定 localAddress.
 *   {@link io.netty.bootstrap.Bootstrap#option(io.netty.channel.ChannelOption, Object)}
 *          设置 ChannelOption 应用于新创建 Channel 的 {@link io.netty.channel.ChannelConfig}.
 *          这些选项将被 bind/connect 设置在通道, 这取决于哪个被首选调用. 这个方法在创建管道后没有影响.
 *          所支持的 ChannelOption 取决于使用的管道类型.
 *   {@link io.netty.bootstrap.Bootstrap#attr(io.netty.util.AttributeKey, Object)}
 *          这些选项将被 bind/connnect 设置在通道, 这取决于哪个被首先调用. 这个方法在创建管道后没有影响.
 *   {@link io.netty.bootstrap.Bootstrap#handler(io.netty.channel.ChannelHandler)}
 *          设置添加到 ChannelPipeline 中的 ChannelHandler 接收事件通知.
 *   {@link io.netty.bootstrap.Bootstrap#clone(io.netty.channel.EventLoopGroup)}
 *   {@link io.netty.bootstrap.Bootstrap#clone()}
 *          clone 当前 Bootstrap, 可指定新的 group.
 *   {@link io.netty.bootstrap.Bootstrap#remoteAddress(java.net.SocketAddress)}
 *   {@link io.netty.bootstrap.Bootstrap#remoteAddress(String, int)}
 *   {@link io.netty.bootstrap.Bootstrap#remoteAddress(java.net.InetAddress, int)}
 *          设置远程地址. 可用过 connect() 指定
 *   {@link io.netty.bootstrap.Bootstrap#connect} 连接到远端, 返回一个 ChannelFuture, 用于通知连接操作完成.
 *   {@link io.netty.bootstrap.Bootstrap#bind} 将通道绑定并返回一个 ChannelFuture, 用于通知绑定操作完成后,
 *          必须调用 {@link io.netty.channel.Channel#connect} 来建立连接
 * 如何引导客户端
 *   Bootstrap 类负责创建管道给客户或应用程序, 利用无连接协议和在调用 bind()/connect() 之后.
 *   示例: {@link org.anonymous.netty.NettyPreview.BootClient}
 * 兼容性
 *   Channel 的实现和 EventLoop 的处理过程在 EventLoopGroup 中必须兼容, 哪些 Channel 是和 EventLoopGroup 是兼容的可查看 API-doc.
 *   (一般而言, 兼容的 Channl 和 EventLoopGroup 在同包下, 类名具有相同的前缀, 如 Nio/Oio等)
 *   示例: 使用不兼容的 Channel 和 EventLoop {@link IncompatibleEventLoopGroupAndChannel}
 * EventLoop 和 EventLoopGroup
 *   EventLoop 分配给该 Channel 负责处理 Channel 的所有操作. 当你执行一个方法, 该方法返回一个 ChannelFuture, 它将再分配给
 *   Channel 的 EventLoop 执行. EventLoopGroup 包含许多 EventLoops.
 * 引导服务器
 *   {@link io.netty.bootstrap.ServerBootstrap#group(io.netty.channel.EventLoopGroup)}
 *   {@link io.netty.bootstrap.ServerBootstrap#group(io.netty.channel.EventLoopGroup, io.netty.channel.EventLoopGroup)}
 *       设置 EventLoopGroup 用于 ServerBootstrap. 这个 EventLoopGroup 提供了 ServerChannel 的 I/O 并且接收 Channel.
 *   {@link io.netty.bootstrap.ServerBootstrap#channel}
 *   {@link io.netty.bootstrap.ServerBootstrap#channelFactory(io.netty.channel.ChannelFactory)}
 *       channel() 指定 Channel 的实现类. 如果该 Channel 没有提供无参构造, 你可以提供一个自定义的 ChannelFactory,
 *       用来实例化 Channel.
 *   {@link io.netty.bootstrap.ServerBootstrap#localAddress(java.net.SocketAddress)}
 *   {@link io.netty.bootstrap.ServerBootstrap#localAddress(java.net.InetAddress, int)}
 *   {@link io.netty.bootstrap.ServerBootstrap#localAddress(String, int)}
 *   {@link io.netty.bootstrap.ServerBootstrap#localAddress(int)}
 *       指定 ServerChannel 实例化的类. 如果不提供, 将由操作系统随机指定.
 *       或者使用 bind()/connect() 指定 localAddress.
 *   {@link io.netty.bootstrap.ServerBootstrap#option(io.netty.channel.ChannelOption, Object)}
 *       指定一个 {@link io.netty.channel.ChannelOption} 来用于新创建的 ServerChannel 的 ChannelConfig.
 *       这些选项将被设置在管道的 bind/connect, 这取决于谁被首先调用. 在此调用这些方法之后设置或更改
 *       ChannelOption 是无效的. 所支持 ChannelOption 取决于使用的管道类型.
 *   {@link io.netty.bootstrap.ServerBootstrap#childOption(io.netty.channel.ChannelOption, Object)}
 *       当管道已被接收, 指定一个 ChannelOption 应用于 Channel 的 ChannelConfig.
 *   {@link io.netty.bootstrap.ServerBootstrap#attr(io.netty.util.AttributeKey, Object)}
 *       指定 ServerChannel 的属性. 这些属性可以被管道 bind() 设置. 当调用 bind() 之后, 修改它们不会生效.
 *   {@link io.netty.bootstrap.ServerBootstrap#childAttr(io.netty.util.AttributeKey, Object)}
 *       应用属性到接收到的管道上, 后续调用没有效果.
 *   {@link io.netty.bootstrap.ServerBootstrap#handler(io.netty.channel.ChannelHandler)}
 *       设置添加到 {@link io.netty.channel.ServerChannel} 的 {@link io.netty.channel.ChannelPipeline}
 *       中的 {@link io.netty.channel.ChannelHandler}.
 *   {@link io.netty.bootstrap.ServerBootstrap#childHandler(io.netty.channel.ChannelHandler)}
 *       设置添加到接收到的 Channel 的 ChannelPipeline 中的 ChannelHandler. handler() 和 childHandler()
 *       的区别在于, 前者是接收和处理 ServerChannel, 同时 childHandler() 添加处理器用于处理和接收 Channel.
 *       后者代表一个套接字绑定到一个远端.
 *   {@link io.netty.bootstrap.ServerBootstrap#clone()}
 *       克隆 ServerBootstrap 用于连接到不同的远端, 通过设置相同的原始 ServerBootstrap.
 *   {@link io.netty.bootstrap.ServerBootstrap#bind}
 *       绑定 ServerChannel 并且返回一个 ChannelFuture, 用于通知连接操作完成了(成功或失败)
 * 如何引导一个服务器
 *  ServerBootstrap 中的 childHandler(), childAttr(), childOption() 是常用的服务器应用的操作.
 *  具体来说, ServerChannel 的实现负责创建子 Channel, 它代表接受连接. 因此, 引导 ServerChannel 的 ServerBootstrap,
 *  提供这些方法来简化接收的 Channel 对 ChannelConfig 应用设置的任务.
 *  ServerBootstrap 在 bind() 后创建 ServerChannel, ServerChannle 管理大量的 子 Channel.
 *  child* 方法都是操作在 子 Channel, 被 ServerChannel 管理.
 *  示例: {@link org.anonymous.netty.NettyPreview.BootServer}
 * --
 * 从 Channel 引导客户端
 *  有时可能需要从另一个 Channel 引导客户端 Channel. 如果你正在编写一个代理或从需要从其他系统检索数据.
 *  后一种情况是常见的, 因为许多 Netty 的应用程序集成现有系统, 例如 Web 服务或数据库.
 *  你当然可以创建一个新的 Bootstrap 并使用它, 这个解决方案不一定有效. 至少, 你需要创建另一个 EventLoop 给
 *  新客户端 Channel, 并且 Channel 将会需要在不同的 Thread 间进行上下文切换.
 *  幸运的是, 由于 EventLoop 继承自 EventLoopGroup, 可以通过传递接收到的 Channel 的 EventLoop 到 Bootstrap
 *  group() 方法. 这允许客户端 Channel 来操作相同的 EventLoop, 这样就能消除额外的线程创建和所有相关的上下文切换的开销.
 * 为何共享 EventLoop 呢?
 *  当你分享一个 EventLoop, 你保证所有 Channel 分配给 EventLoop 将使用相同的线程, 消除上下文切换和相关的开销.
 *  (一个 EventLoop 分配给一个线程执行操作.)
 *  可参考截图: 共享EventLoop2021-02-26 093217.png
 *  示例: {@link org.anonymous.netty.NettyPreview.EventLoopShare}
 * --
 * 在一个引导中添加多个 ChannelHandler
 *   在前述示例中, 我们引导过程中通过 handler()/childHandler() 都只添加一个 ChannelHandler 实例, 对于简单的程序可能足够,
 *   但是对于复杂的程序则无法满足需求. 例如, 某个程序必须支持多个协议, 如 HTTP, WebSocket, 若在一个 ChannelHandler 中处理
 *   这些协议将导致一个庞大而复杂的 ChannelHandler. Netty 通过添加多个 ChannelHandler, 从而使每个 ChannelHandler 分工明确, 结构清晰
 *   Netty 的一个优势是可以在 ChannelPipeline 中堆叠很多 ChannelHandler 并且可以最大程度的重用代码. 如何添加多个 ChannelHandler 呢?
 *   Netty 提供了 {@link io.netty.channel.ChannelInitializer} 抽象类用来初始化 ChannelPipeline 中的 ChannelHandler.
 *   ChannelInitializer 是一个特殊的 ChannelHandler, 通道被注册到 EventLoop 后就会调用 ChannelInitializer, 并允许将
 *   ChannelHandler 添加到 ChannelPipeline, 完成初始化通道后, 这个特殊的 ChannelHandler 初始化器会从 ChannelPipeline 中自动删除.
 *   实例: {@link org.anonymous.netty.NettyPreview.BootstrapUsingChannelInitializer}
 * -
 * 使用 Netty 的 {@link io.netty.channel.ChannelOption} 及属性
 *   比较麻烦的是创建通道后不得不动手配置每个通道, 为了避免这种情况, Netty 提供了 ChannelOption 来帮助引导配置. 这些选项会自动引用到
 *   引导创建的所有通道, 可用的各种选项可以配置底层连接的详细信息, 如通道 "keep-alive", "timeout" 特性.
 *   Netty 应用程序通常会与组织或公司的其他软件进行集成, 在某些情况下, Netty 的组件如 Channel 在 Netty 正常生命周期外使用; Netty 提供了
 *   抽象 {@link io.netty.util.AttributeMap} 集合, 这是由 Netty 的管道和引导类, 和 {@link io.netty.util.AttributeKey} 用于设置和
 *   检索属性值. 属性允许您安全的关联任何数据项与客户端和服务器的 Channel.
 *   例如, 考虑一个服务器应用程序跟踪用户和 Channel 之间的关系. 这可以通过存储用户 ID 作为 Channel 的一个属性. 类似的技术可以用来路由消息
 *   到基于用户 ID 或关闭基于用户活动的一个管道.
 *   示例: {@link org.anonymous.netty.NettyPreview.UsingAttributes}
 * -
 * 关闭之前已经引导的客户端或服务器
 *   引导应用程序启动并运行, 但是迟早也得关闭. 当然可以让 JVM 处理所有退出单这不会满足"优雅"的定义, 指干净地释放资源.
 *   关闭一个 Netty 应用程序并不负责, 但有几件事要记住.
 *   主要是记住关闭 EventLoopGroup, 将处理任何悬而未决地事件和任务并随后释放所有活动线程.
 *   {@link io.netty.channel.EventLoopGroup#shutdownGracefully} 这个调用将返回一个 Future 用来通知关闭完成.
 *   注意, shutdownGracefully() 也是一个异步操作, 所以你需要阻塞, 直到它完成或注册一个监听器直到返回地 Future 来通知完成.
 *   示例: {@link org.anonymous.netty.NettyPreview.ShutdownGracefully}
 * ---
 * UDP
 *  面向连接的传输协议(如 TCP)管理建立一个两个网络端点之间调用(或 "连接"), 命令和可靠的消息传输在调用的生命周期期间,
 *  最后有序在调用终止时终止. 于此相反, 在无连接协议 UDP 没有持久连接的概念, 每个消息(UDP datagram) 是一个独立的传播.
 *  此外, UDP 没有 TCP 的纠错机制, 一个 TCP 连接就像一个电话交谈, 一系列的命令消息流在两个方向上. UDP 就像把一堆
 *  明信片丢进信箱. 我们不能知道它们到达目的地的顺序, 以及它们是否能够到达.
 *  虽然 UDP 存在某些方面的局限性, 这也解释了为什么它远远快于 TCP: 所有的握手和消息管理的开销已被消灭. 显然, UDP 只适合
 *  可以处理或容忍丢失消息, 而不是处理例如金钱交易.
 * --
 * EventLoop and 线程模型
 *  线程模型定义了应用或框架如何执行你的代码, 所以选择线程模型极其重要. Netty 提供了一个简单强大的线程模型来帮助简化代码.
 *  所有的 ChannelHandler, 包括业务逻辑, 都保证由一个 Thread 同时执行特定的 Channel. 这并不意味着 Netty 不支持多线程,
 *  只是 Netty 限制每个 Channel 都由一个 Thread 处理, 这种设计适用于非阻塞 I/O.
 *  EventLoop, 事件循环, 顾名思义, 它运行在一个循环里, 为一系列特定的事件接连运行, 直到它的终止.
 *  基本思想简单示例: {@link org.anonymous.netty.NettyPreview.EventLoopConcept}
 * @see io.netty.channel.EventLoop 扩展自 {@link io.netty.util.concurrent.EventExecutor}
 *   {@link java.util.concurrent.ScheduledExecutorService}, 代表事件循环, 可以直接将任务交给 EventLoop 执行.
 *  一个关于事件和任务执行顺序的重要细节是, 事件/任务执行顺序按照 FIFO. 这是必要的, 若事件不能按顺序处理, 则所处理的
 *  字节将不能保证正确的顺序. 这将导致问题, 所以这是不被允许的设计.
 *
 *
 *
 *
 */
public class NettyPreview {

    /**
     * Netty-EventLoop 的基本实现思想
     */
    static class EventLoopConcept {
        void eventLoop() {
            // 阻塞直到事件可运行
            List<Runnable> readyEvents = blockUtilEventsReady();
            // 循环所有事件, 并执行任务.
            readyEvents.forEach(Runnable::run);
        }

        private List<Runnable> blockUtilEventsReady() {
            // ...
            return Collections.emptyList();
        }
    }


    static class SecureChatServerInitializer extends ChatServerInitializer {

        private final SslContext context;

        SecureChatServerInitializer(ChannelGroup group, SslContext context) {
            super(group);
            this.context = context;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            super.initChannel(ch);
            final SSLEngine engine = context.newEngine(ch.alloc());
            engine.setUseClientMode(false);

            // 2. 向 ChannelPipeline 添加 SslHandler 实现加密.
            ch.pipeline().addFirst(new SslHandler(engine));
        }
    }


    /**
     * @see org.anonymous.netty.NettyPreview.TextWebSocketFrameHandler
     * @see org.anonymous.netty.NettyPreview.HttpRequestHandler1
     * 握手升级成功后 WebSocketServerProtocolHandler 替换 HttpRequestDecoder 为 WebSocketFrameDecoder,
     * HttpResponseEncoder 为 WebSocketFrameEncoder. 为了最大化性能, WebSocket 连接不需要的 ChannelHandler
     * 将会被移除. 其中就包括了 HttpObjectAggregator, HttpRequestHandler1.
     * Netty 支持各版本的 WebSocket 协议, 不同的实现类. 选择正确版本的 WebSocketFrameDecoder/WebSocketFrameEncoder
     * 是自动进行的, 这取决于客户端(browser)的支持.
     * @see org.anonymous.netty.netty.websocket.MyServer
     */
    static class ChatServerInitializer
            // 1. 扩展 ChannelInitializer
            extends ChannelInitializer<Channel> {

        private final ChannelGroup group;

        ChatServerInitializer(ChannelGroup group) {
            this.group = group;
        }

        // 2. 添加 ChannelHandler 到 ChannelPipeline
        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            // 各个 ChannelHandler 的职责
            // HttpServerCodec: decode bytes to HttpRequest, HttpContent, LastHttpContent,
            // encode HttpRequest, HttpContent, LastHttpContent to bytes.
            pipeline.addLast(new HttpServerCodec(),
                    // HttpObjectAggregator: This ChannelHandler aggregates an HttpMessage and
                    // its following HttpContents into a single FullHttpRequest or FullHttpResponse
                    // depending on if it used to handle requests or responses.
                    // With this installed the next ChannelHandler in the pipeline will receive only
                    // full HTTP requests.
                    new HttpObjectAggregator(64 * 1024),
                    // ChunkedWriteHandler: write the contents of a file.
                    new ChunkedWriteHandler(),
                    // Handle FullHttpRequests (those not sent to "/ws" UTI)
                    new HttpRequestHandler1("/ws"),
                    // As required by the WebSockets specification, handle the
                    // WebSocket Upgrade handshake, PingWebSocketFrames, PongWebSocketFrames
                    // and CloseWebSocketFrames.
                    new WebSocketServerProtocolHandler("/ws"),
                    // Handles TextWebSocketFrames and handshake completion events.
                    new TextWebSocketFrameHandler(group));
        }
    }

    /**
     * TextWebSocketFrameHandler 做了以下几件事:
     *  1. 当 WebSocket 与新客户端已成功握手完成, 通过写入信息到 ChannelGroup 中的 Channel 来通知
     *    所有连接的客户端, 然后添加新的 Channel 到 ChannelGroup
     *  2. 如果接收到 TextWebSocketFrame, 调用 retain(), 并将其 write, flush 到 ChannelGroup,
     *    使所有连接的 WebSocket Channel 都能接收到它. 和 {@link org.anonymous.netty.NettyPreview.HttpRequestHandler1}
     *    一样, retain() 是必需的, 因为当 channelRead0() 返回时, TextWebSocketFrame 的引用计数将递减. 由于所有操作都是异步的,
     *    writeAndFlush() 可能会在以后完成, 我们不希望它访问无效的引用.
     */
    static class TextWebSocketFrameHandler
            // 1. 扩展 SimpleChannelInboundHandler 用于处理 TextWebSocketFrame 信息
            extends SimpleChannelInboundHandler<TextWebSocketFrame> {

        private final ChannelGroup group;

        TextWebSocketFrameHandler(ChannelGroup group) {
            this.group = group;
        }

        // 2. overrider userEventTriggered() 方法来处理自定义事件
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            // super.userEventTriggered(ctx, evt);
            // if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) // deprecation
            if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
                // 3. 如果接收的事件表明握手成功, 就从 ChannelPipeline 中删除 HttpRequestHandler, 因为接下来不会接受 HTTP 消息了.
                ctx.pipeline().remove(HttpRequestHandler1.class);
                // 4. 写一条消息给所有的已连接 WebSocket 客户端, 通知它们建立了一个新的 Channel 连接
                group.writeAndFlush(new TextWebSocketFrame("Client" + ctx.channel() + " joined"));
                // 5. 添加新连接的 WebSocket Channel 到 ChannelGroup 中, 这样它就能收到所有消息
                group.add(ctx.channel());
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            // 6. 保留收到的消息, 并通过 writeAndFlush() 传递给所有连接的客户端
            group.writeAndFlush(msg.retain());
        }

    }


    /**
     * HttpRequestHandler 做了以下几件事:
     * 1. 如果该 HTTP 请求被发送到 URI "/ws", 则调用 {@link io.netty.handler.codec.http.FullHttpResponse#retain()}
     *  并通过调用 fireChannelRead(msg) 转发到下一个 ChannelInboundHandler. retain() 的调用是必要的, 因为 channelRead()
     *  完成后, 它会调用 {@link io.netty.handler.codec.http.FullHttpResponse#release()} 来释放其资源.
     * 2. 如果客户端发送的 HTTP 1.1 头是 "Except: 100-continue", 则发送 "100 Continue" 的响应.
     * 3. 在头被设置后, 写一个 HttpResponse 返回给客户端. 注意, 这不是 FullHttpResponse, 这只是响应的第一部分.
     *   另外, 这里也不使用 writeAndFlush(), 这个是留在最后完成.
     * 4. 如果传输过程既没有要求加密也没有要求压缩, 那么把 index.html 的内容存储在一个 DefaultFileRegion 里就可以达到最好的效率.
     *   这将利用零拷贝来执行传输. 处于这个原因, 我们要检查 ChannelPipeline 中是否有一个 SslHandler. 如果是的话, 我们就是用
     *   ChunkedNioFile.
     * 5. 写 LastHttpContent 来标记响应的结束, 并终止它.
     * 6. 如果不要求 keepalive, 添加 ChannelFutureListener 到 ChannelFuture 对象的最后写入, 并关闭连接.
     *    注意, 这里调用 writeAndFlush() 来刷新所有以前写的信息.
     */
    static class HttpRequestHandler1
            // 1. 扩展 SimpleChannelInboundHandler 用于处理 FullHttpRequest 信息
            extends SimpleChannelInboundHandler<FullHttpRequest> {

        private final String wsUri;
        private static final File INDEX;

        static {
            final URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
            try {
                String path = location.toURI() + "index.html";
                path = !path.contains("file:") ? path : path.substring(5);
                INDEX = new File(path.replace("classes", "resources"));
                System.out.println(INDEX);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Unable to locate index.html", e);
            }
        }

        HttpRequestHandler1(String wsUri) {
            this.wsUri = wsUri;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
            if (wsUri.equalsIgnoreCase(request.uri())) {
                // 2. 如果请求是一次升级了的 WebSocket 请求, 则递增引用计数器(retain) 并将它传递给
                // 在 ChannelPipeline 中的下个 ChannelInboundHandler
                ctx.fireChannelRead(request.retain());
            } else {
                if (HttpUtil.is100ContinueExpected(request)) {
                    // 3. 处理符合 HTTP 1.1 的 100 Continue 请求
                    send100Continue(ctx);
                }

                // 4. 读取 index.html
                RandomAccessFile file = new RandomAccessFile(INDEX, "r");

                final DefaultHttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
                final HttpHeaders headers = response.headers();
                headers.set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

                final boolean keepAlive = HttpUtil.isKeepAlive(request);

                // 5. 判断 keepalive 是否在请求头里面
                if (keepAlive) {
                    headers.set(HttpHeaderNames.CONTENT_LENGTH, file.length());
                    headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                }
                // 6. 写 HttpResponse 到客户端
                ctx.write(request);

                // 7. 写 index.html 到客户端, 根据 ChannelPipeline 中是否有
                // SslHandler 来决定使用 DefaultFileRegion 还是 ChunkedNioFile
                if (ctx.pipeline().get(SslHandler.class) == null) {
                    ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
                } else {
                    ctx.write(new ChunkedNioFile(file.getChannel()));
                }

                // 8. write and flush LastHttpContent 到客户端, 标记响应完成
                final ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

                if (!keepAlive) {
                    // 9. 如果请求头中不包含 keepalive, 当写完成时, 关闭 Channel
                    future.addListener(ChannelFutureListener.CLOSE);
                }
            }
        }

        private void send100Continue(ChannelHandlerContext ctx) {
            final DefaultHttpResponse response = new DefaultHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
            ctx.writeAndFlush(response);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // super.exceptionCaught(ctx, cause);
            cause.printStackTrace();
            ctx.close();
        }
    }


    //--------------------------------------------
    // Shutdown Gracefully
    //--------------------------------------------
    static class ShutdownGracefully {

        public static void main(String[] args) throws InterruptedException {
            // 1. 创建 EventLoopGroup 用于处理 I/O
            final NioEventLoopGroup group = new NioEventLoopGroup();
            // 2. 创建一个新地 Bootstrap 并配置它
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                            // do something
                        }
                    });
            // 3. 最终优雅地关闭 EventLoopGroup 释放资源. 这个也会关闭当前使用地 Channel
            // 或者可以调用 Channel.close() 显式的在所有活动管道之前调用
            final Future<?> future = group.shutdownGracefully();
            // block until the group has shutdown
            future.sync();
        }

    }


    //--------------------------------------------
    // Bootstrap
    //--------------------------------------------

    static class UsingAttributes {
        public static void main(String[] args) {
            // 1. 新建一个 AttributeKey 用来存储属性值
            final AttributeKey<Integer> id = AttributeKey.newInstance("ID");
            // 2. 新建 Bootstrap 用来创建客户端管道并连接它们
            final Bootstrap bootstrap = new Bootstrap();
            // 3. 指定 EventLoopGroups 从和接收到的管道来注册并获取 EventLoop
            bootstrap.group(new NioEventLoopGroup())
                    // 4. 指定 Channel 类
                    .channel(NioSocketChannel.class)
                    // 5. 设置处理器来处理管道的 I/O 和数据
                    .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                            System.out.println("Received data");
                        }

                        @Override
                        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                            // 6. 检索 AttributeKey 的属性及其值
                            final Integer idValue = ctx.channel().attr(id).get();
                            // do something with the idValue
                        }
                    });
            // 7. 设置 ChannelOption 将会设置在管道在连接或绑定
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
            // 8. 存储 id 属性
            bootstrap.attr(id, 123456);

            // 9. 通过配置的 Bootstrap 来连接到远程主机
            final ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.xxx.com", 80));
            future.syncUninterruptibly();
        }

    }


    /**
     * Bootstrap and using ChannelInitializer
     * 通过 ChannelInitializer, Netty 允许添加程序所需的多个 ChannelHandler 到 ChannelPipeline
     */
    static class BootstrapUsingChannelInitializer {

        public static void main(String[] args) throws InterruptedException {
            // 1. 创建一个新的 ServerBootstrap 来创建和绑定新的 Channel
            final ServerBootstrap bootstrap = new ServerBootstrap();
            // 2. 指定 EventLoopGroups 从 ServerChannel 和接收到的管道来注册并获取 EventLoops
            bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                    // 3. 指定 Channel 实现
                    .channel(NioServerSocketChannel.class)
                    // 4. 设置处理器用于处理接收到的管道的 I/O 和数据
                    .childHandler(new ChannelInitializerImpl());
            // 5. 配置引导, 绑定管道
            final ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
            future.sync();

        }

        // 6. ChannelInitializer 负责设置 ChannelPipeline
        private static class ChannelInitializerImpl extends ChannelInitializer<Channel> {
            // 7. 实现 initChannel() 来添加需要的处理器到 ChannelPipeline
            // 一旦完成了这方法 ChannelInitializer 会从 ChannelPipeline 删除自身
            @Override
            protected void initChannel(Channel ch) throws Exception {
                final ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new HttpClientCodec())
                        .addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
            }
        }

    }


    /**
     * EventLoop shared between channels with ServerBootstrap and Bootstrap
     * 注意, 新的 EventLoop 会创建一个新的 Thread. 处于该原因, EventLoop 示例应该尽量重用,
     * 或者限制实例数量来避免耗尽系统资源
     */
    static class EventLoopShare {

        public static void main(String[] args) {
            // 1. 新建 ServerBootstrap 来创建新的 SocketChannel 管道并绑定它们
            final ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(new NioEventLoopGroup(), // 2. 指定 EventLoopGroups 从 ServerChannel 和接收到的管道来注册并获取 EventLoops
                    new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class) // 3. 指定 Channel 类来使用
                    // 4. 设置处理器用于处理接收到的管道的 I/O 和数据
                    .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                            if (connectFuture.isDone()) {
                                // 10. 连接完成时处理业务逻辑(比如 proxy)
                                // do something with the data
                            }
                        }

                        private ChannelFuture connectFuture;

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 5. 创建一个新的 Bootstrap 来连接到远程主机
                            final Bootstrap bootstrap = new Bootstrap();
                            bootstrap.channel(NioServerSocketChannel.class) // 6. 设置管道类
                                    // 7. 设置处理器来处理 I/O
                                    .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                            System.out.println("Received data");
                                        }
                                    });
                            // 8. 使用相同的 EventLoop 作为分配到接收的管道
                            bootstrap.group(ctx.channel().eventLoop());
                            // 9. 连接到远端
                            connectFuture = bootstrap.connect(new InetSocketAddress("www.xxx.com", 80));
                        }
                    });
            // 11. 通过配置了的 Bootstrap 来绑定到管道
            final ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(8080));
            future.addListener(f -> {
                if (f.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bound attempt failed");
                    f.cause().printStackTrace();
                }
            });

        }

    }

    /**
     * Bootstrapping a server
     */
    static class BootServer {
        public static void main(String[] args) {
            final NioEventLoopGroup group = new NioEventLoopGroup();
            // 1. 新建 ServerBootstrap 来创建新的 SocketChannel 管道并绑定它们
            final ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(group) // 2. 指定 EventLoopGroup 用于从注册的 ServerChannel 中获取 EventLoop 和接收到的管道
                    .channel(NioServerSocketChannel.class) // 3. 指定要使用的管道类
                    // 4. 设置子处理器用于处理接收的管道的 I/O 和数据
                    .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                            System.out.println("Received data");
                            msg.clear();
                        }
                    });
            // 5. 通过配置引导来绑定管道
            final ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
            future.addListener(f -> {
                if (f.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bound attempt failed");
                    f.cause().printStackTrace();
                }
            });
        }
    }

    /**
     * Bootstrap client with incompatible EventLoopGroup
     * java.lang.IllegalStateException: incompatible event loop type: io.netty.channel.nio.NioEventLoop
     */
    static class IncompatibleEventLoopGroupAndChannel {
        public static void main(String[] args) {
            // EventLoopGroup 使用 Nio.
            final NioEventLoopGroup group = new NioEventLoopGroup();
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    // channel 使用 Oio
                    .channel(OioSocketChannel.class)
                    .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                            System.out.println("Received data");
                            msg.clear();
                        }
                    });
            // 尝试连接到远端. 当 NioEventLoopGroup 和 OioSocketChannel 不兼容时, 会抛出
            // IllegalStateException
            // java.lang.IllegalStateException: incompatible event loop type: io.netty.channel.nio.NioEventLoop
            final ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.xxx.com", 80));
            future.syncUninterruptibly();
        }
    }


    static class BootClient {

        public static void main(String[] args) {

            final NioEventLoopGroup group = new NioEventLoopGroup();
            // 1. 创建一个新的 Bootstrap 来创建和连接到新的客户端管道
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group) // 2. 指定 EventLoopGroup
                    .channel(NioSocketChannel.class) // 3. 指定 Channel 实现来使用
                    // 4. 设置处理器给 Channel 的事件和数据
                    .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                            System.out.println("Received data");
                            msg.clear();
                        }
                    });
            // 5. 连接到远端主机
            final ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.xxx.com", 80));

            future.addListener(f -> {
                if (f.isSuccess()) {
                    System.out.println("Connection established");
                } else {
                    System.err.println("Connection attempt failed");
                    f.cause().printStackTrace();
                }
            });

        }

    }


    //--------------------------------------------
    // Serialization
    //--------------------------------------------

    static class ProtoBufInitializer extends ChannelInitializer<Channel> {

        private final MessageLite lite;

        ProtoBufInitializer(MessageLite lite) {
            this.lite = lite;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            // 添加 ProtobufVarint32FrameDecoder 用来分割帧
            pipeline.addLast(new ProtobufVarint32FrameDecoder())
                    // 添加 ProtobufEncoder 用来处理消息的编码
                    .addLast(new ProtobufEncoder())
                    // 添加 ProtobufDecoder 用来处理消息的解码
                    .addLast(new ProtobufDecoder(lite))
                    // 添加 ObjectHandler 用来处理解码了的消息
                    .addLast(new ObjectHandler());
        }

        private static final class ObjectHandler extends SimpleChannelInboundHandler<Object> {

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                // Do something with the object
            }

        }

    }


    // JBoss Marshalling
    static class MarshallingInitializer extends ChannelInitializer<Channel> {

        private final MarshallerProvider marshallerProvider;
        private final UnmarshallerProvider unmarshallerProvider;

        MarshallingInitializer(MarshallerProvider marshallerProvider, UnmarshallerProvider unmarshallerProvider) {
            this.marshallerProvider = marshallerProvider;
            this.unmarshallerProvider = unmarshallerProvider;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new MarshallingDecoder(unmarshallerProvider))
                    .addLast(new MarshallingEncoder(marshallerProvider))
                    .addLast(new ObjectHandler());
        }

        private static final class ObjectHandler extends SimpleChannelInboundHandler<Serializable> {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, Serializable msg) throws Exception {
                // Do something
            }

        }

    }


    /**
     * Transfer file content with FileRegion
     */
    static class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {
        private final Path file;
        private final SslContext context;

        ChunkedWriteHandlerInitializer(Path file, SslContext context) {
            this.file = file;
            this.context = context;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            // 1. 创建 SslHandler 到 ChannelPipeline
            pipeline.addLast(new SslHandler(context.newEngine(ch.alloc())))
                    // 2. 添加 ChunkedWriteHandler 用来处理作为 ChunkedInput 传进的数据
                    .addLast(new ChunkedWriteHandler())
                    // 3. 当建立连接时, WriteStreamHandler 开始写文件的内容
                    .addLast(new WriteStreamHandler());
        }

        private class WriteStreamHandler extends ChannelInboundHandlerAdapter {

            // 4. 当连接建立时, channelActive() 触发使用 ChunkedInput 来写文件的内容
            @Override
            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                super.channelActive(ctx);
                ctx.writeAndFlush(new ChunkedStream(Files.newInputStream(file)));
            }
        }

    }


    /**
     * Transferring file contents with FileRegion
     * NIO zero-copy, 消除移动一个文件的内容从文件系统到网络堆栈的复制步骤.
     * 所有这一切发生在 Netty 的核心, 因此所有所需的应用程序代码是使用 {@link io.netty.channel.FileRegion}
     * 的实现.
     * 此例演示通过 zero-copy 将文件内容从 FileInputStream 创建 DefaultFileRegion 并使用 Channel 写入.
     * 此例只适用于直接传输一个文件的内容, 没有执行数据处理.
     * 在相反情况下, 将数据从文件系统复制到用户内存是必须的, 可以使用 {@link io.netty.handler.stream.ChunkedWriteHandler}
     * 这个类提供了支持异步写大数据流不引起高内存消耗.
     * @see org.anonymous.netty.NettyPreview.ChunkedWriteHandlerInitializer
     */
    static class FileRegionInitializer extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final FileChannel channel = new FileInputStream("").getChannel();
            // 创建 DefaultFileRegion 用于文件的完整长度
            final DefaultFileRegion region = new DefaultFileRegion(channel, 0, channel.size());
            // 发送 DefaultFileRegion 并且注册一个 ChannelFutureListener
            ch.writeAndFlush(region)
                    .addListener((ChannelFutureListener) future -> {
                        if (!future.isSuccess()) {
                            // 处理发送失败
                            final Throwable cause = future.cause();
                            // Do something
                        }
                    });
        }

    }


    //--------------------------------------------
    // Delimiter
    //--------------------------------------------

    static class LengthBasedInitializer extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            // 1. 添加一个 LengthFieldBasedFrameDecoder, 用于
            // 提取基于帧编码长度 8 个字节的帧
            pipeline.addLast(
                    new LengthFieldBasedFrameDecoder(
                            65 * 1024, 0, 8)
            );
            // 2. 添加 FrameHandler 用来处理每帧
            pipeline.addLast(new FrameHandler());
        }

        static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                // 3. 处理帧数据
                // Do something with the frame
            }

        }

    }


    /**
     * 使用 DelimiterBasedFrameDecoder 可以方便处理特定分隔符作为数据结构体的这类情况
     * 1. 传入的数据流是一系列的帧, 每个由换行("\n")分隔
     * 2. 每帧包括一系列项目, 每个由单个空格字符分隔
     * 3. 一帧的内容代表一个 "命令": 一个名字后跟一些变量参数
     */
    static class CmdHandlerInitializer // Decoder for command and the handler
            extends ChannelInitializer<Channel> {
        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            // 1. 添加一个 CmdDecoder 到管道; 将提取 Cmd 对象和转发到在管道中的下一个处理器
            pipeline.addLast(new CmdDecoder(65 * 1024));
            // 2. 添加 CmdHandler 将接收和处理 Cmd 对象
            pipeline.addLast(new CmdHandler());
        }

        // 3. 命令也是 POJO
        static final class Cmd {
            private final ByteBuf name;
            private final ByteBuf args;

            Cmd(ByteBuf name, ByteBuf args) {
                this.name = name;
                this.args = args;
            }

            public ByteBuf name() {
                return name;
            }

            public ByteBuf args() {
                return args;
            }
        }

        private static class CmdDecoder extends LineBasedFrameDecoder {

            public CmdDecoder(int maxLength) {
                super(maxLength);
            }

            @Override
            protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
                // return super.decode(ctx, buffer);
                // 4. super.decode() 通过结束分隔从 ByteBuf 提取帧
                final ByteBuf frame = (ByteBuf) super.decode(ctx, buffer);
                // 5. frame 是空时, 则返回 null
                if (frame == null) {
                    return null;
                }

                // 6. 找到第一个空字符的索引. 开始是命令名; 接下来是参数
                final int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), (byte) ' ');

                // 7. 实例化一个新的 Cmd 对象: cmd-name args
                return new Cmd(frame.slice(frame.readerIndex(), index),
                        frame.slice(index + 1, frame.writerIndex()));
            }

        }

        private static final class CmdHandler extends SimpleChannelInboundHandler<Cmd> {

            @Override
            protected void channelRead0(ChannelHandlerContext ctx, Cmd msg) throws Exception {
                // 8. 处理通过 pipeline 的 Cmd 对象
                // Do something with the command
            }

        }

    }

    /**
     * Handling line-delimited frames
     */
    static class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            // 1. 添加一个 LineBasedFrameDecoder 用于提取帧并把数据包转发到下一个管道中的处理
            // 程序, 在这种情况下就是 FrameHandler.
            pipeline.addLast(new LineBasedFrameDecoder(65 * 1024));
            // 2. 添加 FrameHandler 用于接收帧
            pipeline.addLast(new FrameHandler());
        }

        private static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
            // 3. 每次调用都需要传递一个单帧的内容.
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                // Do somthing with the frame.
            }

        }

    }

    //---------------------------------------------
    // Idle connection and timeouts
    //---------------------------------------------

    /**
     * Sending heartbeats
     * 这个例子说明如何使用 IdleStateHandler 测试远端是否还活着,
     * 如果不是就关闭连接释放资源.
     */
    static class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            // 1. 如果连接没有接收或发送给数据超过 60s.
            // IdleStateHandler 将通过 IdleStateEvent 调用 userEventTriggered,
            pipeline.addLast(new IdleStateHandler(
                    0, 0, 60, TimeUnit.SECONDS));
            pipeline.addLast(new HeartbeatHandler());
        }

        private static class HeartbeatHandler extends ChannelInboundHandlerAdapter {

            // 2. 心跳发送到远端
            private final static ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                    Unpooled.copiedBuffer("HEARTBEAT", StandardCharsets.ISO_8859_1)
            );

            @Override
            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                // super.userEventTriggered(ctx, evt);
                // 3. 发送并添加监听器, 如果发送失败将关闭连接.
                if (evt instanceof IdleStateEvent) {
                    ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                            .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                } else {
                    // 4. 事件不是一个 IdleStateEvent 的话, 就将它传递给下一个处理程序.
                    super.userEventTriggered(ctx, evt);
                }
            }
        }
    }

    //---------------------------------------------
    // SPDY
    //---------------------------------------------

    /**
     * org.eclipse.jetty.npn:npn-api:8.1.2.v20120308
     * todo: 该 SDPY 测试无法正常实现, 启动访问报错: (关于 SSL/TSL 该如何设置?)
     * 二月 25, 2021 9:11:16 上午 io.netty.handler.ssl.ApplicationProtocolNegotiationHandler handshakeFailure
     * 警告: [id: 0x6228b7b3, L:0.0.0.0/0.0.0.0:9999 ! R:/127.0.0.1:4374] TLS handshake failed:
     * javax.net.ssl.SSLException: Received fatal alert: certificate_unknown
     * --
     * 源启动参数: -Xbootclasspath/p:npn-api-8.1.2.v20120308.jar
     * 访问 https://localhost:port
     * 暂不确定如何设置启动参数才能正常访问...
     */
    static class SpdyServer {

        // 1. 构建新的 NioEventLoopGroup 用于处理 I/O
        private final NioEventLoopGroup group = new NioEventLoopGroup();
        private final SslContext context;
        private Channel channel;

        // 2. 传递 SslContext 用于加密
        SpdyServer(SslContext context) {
            this.context = context;
        }

        public ChannelFuture start(InetSocketAddress address) {
            // 3. 新建 ServerBootstrap 用于配置服务器
            final ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new MySpdyChannelInitializer(context)); // 4 配置 ServerBootstrap
            // 5. 绑定服务器用于接收指定地址的连接
            final ChannelFuture future = bootstrap.bind(address);
            future.syncUninterruptibly();
            channel = future.channel();

            return future;
        }

        // 6. 销毁服务器, 用于关闭管道和 NioEventLoopGroup
        public void destroy() {
            if (channel != null) {
                channel.close();
            }
            group.shutdownGracefully();
        }

        public static void main(String[] args) throws CertificateException, SSLException {
            if (args.length != 1) {
                System.out.println("Please give port as argument");
                System.exit(1);
            }
            int port = Integer.parseInt(args[0]);

            final SelfSignedCertificate cert = new SelfSignedCertificate();
            // 7. 从 BogusSslContextFactory 获取 SSLContext, 这是一个虚拟实现进行测试.
            // 真正的实现将为 SslContext 配置适当的密钥存储库
            final SslContext context = SslContextBuilder
                    .forServer(cert.certificate(), cert.privateKey())
                    .build();

            final SpdyServer endpoint = new SpdyServer(context);

            final ChannelFuture future = endpoint.start(new InetSocketAddress(port));

            Runtime.getRuntime().addShutdownHook(new Thread(endpoint::destroy));

            future.channel().closeFuture().syncUninterruptibly();
        }

    }


    /**
     * @see org.anonymous.netty.NettyPreview.DefaultSpdyOrHttpChooser
     * 继承 ChannelInitializer 是一个简单的开始
     * 支持 SPDY 的 ChannelPipeline 用于连接客户端的配置:
     *  一个 client-pipeline 中按次序添加的 handler: 责任链(responsiblities, reponsibility-chain)
     * @see io.netty.handler.ssl.SslHandler 加解密两端交换的数据
     * @see io.netty.handler.codec.spdy.SpdyFrameDecoder 从接收到的 SPDY 帧中解码字节
     * @see io.netty.handler.codec.spdy.SpdyFrameEncoder 编码 SPDY 帧到字节
     * @see io.netty.handler.codec.spdy.SpdySessionHandler 处理 SPDY session
     * @see io.netty.handler.codec.spdy.SpdyHttpEncoder 编码 HTTP 消息到 SPDY 帧
     * @see io.netty.handler.codec.spdy.SpdyHttpDecoder 解码 SPDY 帧到 HTTP 消息
     * @see io.netty.handler.codec.spdy.SpdyHttpResponseStreamIdHandler 处理基于 SPDY ID 请求和响应之间的映射关系
     * @see org.anonymous.netty.NettyPreview.SpdyRequestHandler 处理 FullHttpRequest, 用于从 SPDY 帧中解码, 因此允许 SPDY 透明传输
     * 每个 ChannelHandler 负责一个小部分工作, 这个就是对基于 Netty 构造的应用程序最完美的诠释.
     * --
     * 当协议是 HTTP(s) 时, ChannelPipeline 中的 handlers 与 SPDY 不同
     * @see io.netty.handler.ssl.SslHandler 加解密两端交换的数据
     * @see io.netty.handler.codec.http.HttpRequestDecoder 从接收到的 HTTP 请求中解码字节
     * @see io.netty.handler.codec.http.HttpResponseEncoder 编码 HTTP 响应到字节
     * @see io.netty.handler.codec.http.HttpObjectAggregator 处理 SPDY session
     * @see org.anonymous.netty.NettyPreview.HttpRequestHandler 解码时处理 FullHttpRequest
     * --
     * 所有东西组合在一起
     * 所有的 ChannelHandler 实现已经准备好, 现在组合成一个 SpdyServer
     * @see org.anonymous.netty.NettyPreview.SpdyServer
     */
    static class MySpdyChannelInitializer extends ChannelInitializer<io.netty.channel.socket.SocketChannel> {

        private final SslContext context;

        // 2. 传递 SslContext 用于创建 SSLEngine.
        MySpdyChannelInitializer(SslContext context) {
            this.context = context;
        }

        @Override
        protected void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            // 3. 新建 SSLEngine, 用于新的管道和连接
            final SSLEngine engine = context.newEngine(ch.alloc());
            // 4. 配置 SSLEngine 用于非客户端使用
            engine.setUseClientMode(false);

            // 5. 通过 NexProtoNego 类绑定 DefaultServerProvider 到 SSLEngine.
            NextProtoNego.put(engine, new DefaultServerProvider());
            NextProtoNego.debug = true;

            // 6. 添加 SslHandler 到 ChannelPipeline, 这将会在协议检测到时保存在 ChannelPipeline
            pipeline.addLast("ssl", new SslHandler(engine));
            // 7. 添加 DefaultSpdyOrHttpChooser 到 ChannelPipeline.
            // 这个实现将会监测协议. 添加正确的 ChannelHandler 到 ChannelPipeline, 并移除自身.
            // 实际的 ChannelPipeline 设置将会在 DefaultSpdyOrHttpChooser 实现之后完成,
            // 因为在这一点上它可能只需要知道客户端是否支持 SPDY
            pipeline.addLast("spdy", new DefaultSpdyOrHttpChooser());
        }

    }

    /**
     * @code io.netty.handler.codec.spdy.SpdyOrHttpChooser netty 4.0.56.Final+ 移除
     * @see org.anonymous.netty.NettyPreview.MySpdyChannelInitializer
     */
    static class DefaultSpdyOrHttpChooser extends ApplicationProtocolNegotiationHandler {
        /**
         * Creates a new instance with the specified fallback protocol name.
         *
         */
        protected DefaultSpdyOrHttpChooser() {
            super(ApplicationProtocolNames.HTTP_1_1);
        }

        @Override
        protected void configurePipeline(ChannelHandlerContext ctx, String protocol) throws Exception {
            // 可参考父类注释文档的示例
            switch (protocol) {
                case ApplicationProtocolNames.HTTP_1_1:
                    configureHttp1(ctx);
                    break;
                case ApplicationProtocolNames.HTTP_2:
                    configureHttp2(ctx);
                    break;
                case ApplicationProtocolNames.SPDY_1:
                    configureSpdy1(ctx);
                    break;
                case ApplicationProtocolNames.SPDY_2:
                    configureSpdy2(ctx);
                    break;
                case ApplicationProtocolNames.SPDY_3:
                    configureSpdy3(ctx);
                    break;
                case ApplicationProtocolNames.SPDY_3_1:
                    configureSpdy3_1(ctx);
                    break;
                default:
                    throw new IllegalStateException("unknown protocol: " + protocol);
            }
        }

        private void configureSpdy3_1(ChannelHandlerContext ctx) {

        }

        private void configureSpdy3(ChannelHandlerContext ctx) {

        }

        private void configureSpdy2(ChannelHandlerContext ctx) {
        }

        private void configureSpdy1(ChannelHandlerContext ctx) {
        }

        private void configureHttp2(ChannelHandlerContext ctx) {

        }

        private void configureHttp1(ChannelHandlerContext ctx) {

        }
    }

    /**
     * implementation that handles SPDY
     *
     */
    @ChannelHandler.Sharable
    static class SpdyRequestHandler extends HttpRequestHandler { // 1. 继承 HttpRequestHandler, 共享逻辑

        @Override
        protected String getContent() {
            // 2. 生产内容写到 payload. 这个重写了 HttpRequestHandler 的 getContent() 的实现
            return "This content is transmitted via SPDY\r\n";
        }

    }

    /**
     * implementation that handles HTTP
     * 这就是 Netty 处理标准的 HTTP. 可能需要分别处理特定 URL, 应对不同的状态代码, 这取决于资源存在与否, 但基本的概念相同.
     */
    @ChannelHandler.Sharable
    static class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
        /**
         * 1. 重写 channelRead0(), 可以被所有接收到的 FullHttpRequest 调用
         */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx); // 2. 检查如果接下来的响应是预期的, 就写入.
            }

            // 3. 新建 FullHttpResponse, 用于对请求的响应
            final DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    request.protocolVersion(), HttpResponseStatus.OK);
            // 4. 生成响应的内容, 将它写入 payload
            response.content().writeBytes(getContent().getBytes(CharsetUtil.UTF_8));
            // 5. 设置头文件, 这样客户端就能知道如何与响应的 payload 交互
            final HttpHeaders headers = response.headers();
            headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

            final boolean keepAlive = HttpUtil.isKeepAlive(request);

            // 6. 检查请求设置是否启用了 keep-alive
            // 如果启用了, 则将 header 设置为符合 HTTP RPC
            if (keepAlive) {
                headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
                headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
            // 7. 写响应给客户端, 并获取到 Future 的引用, 用于写完成时, 获取到通知.
            final ChannelFuture future = ctx.writeAndFlush(response);

            if (!keepAlive) {
                // 8. 如果响应不是 keep-alive, 在写完成时关闭连接
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }

        protected String getContent() { // 9. 返回内容作为响应的 payload
            return "This content is transmitted via HTTP\r\n";
        }

        // 10. 生成了 100-continue 的响应, 并写给客户端.
        private void send100Continue(ChannelHandlerContext ctx) {
            final DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
            ctx.writeAndFlush(response);
        }

        /**
         * 11. 若执行阶段抛出异常, 则关闭管道
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            // super.exceptionCaught(ctx, cause);
            cause.printStackTrace();
            ctx.close();
        }
    }


    /**
     * implementation of ServerProvider
     * 在 ServerProvider 的实现, 支持下面 3 种协议
     *  SPDY2, SPDY3, HTTP 1.1
     *  如果客户端不支持 SPDY, 则默认使用 HTTP 1.1
     */
    static class DefaultServerProvider implements NextProtoNego.ServerProvider {

        // 1. 定义所有的 ServerProvider 实现的协议
        private static final List<String> PROTOCOLS =
                Collections.unmodifiableList(Arrays.asList("spdy/2", "spdy/3", "http/1.1"));

        private String protocol;

        @Override
        public void unsupported() {
            protocol = "http/1.1"; // 2. 设置如果 SPDY 协议失败了就转到 http/1.1
        }

        @Override
        public List<String> protocols() {
            return PROTOCOLS; // 3. 返回支持的协议列表
        }

        @Override
        public void protocolSelected(String protocol) {
            this.protocol = protocol; // 4. 设置选择的协议
        }

        public String getSelectedProtocol() {
            return protocol; // 5. 返回选择的协议
        }
    }

    static class WebSocketServerInitializer extends ChannelInitializer<Channel> {

        private final SslContext context;

        WebSocketServerInitializer(SslContext context) {
            this.context = context;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            // 如需加密 WebSocket 只需将 SslHandler 作为 pipeline 的第一个 ChannelHandler
            pipeline.addFirst("ssl", new SslHandler(context.newEngine(ch.alloc())));

            pipeline.addLast(
                    new HttpServerCodec(), // 这里以 server 为例.
                    // 1. 添加 HttpObjectAggregator 用于提供在握手时聚合 HttpRequest
                    new HttpObjectAggregator(65536),
                    // 2. 添加 WebSocketServerProtocolHandler 用于处理 websocket 请求
                    // 当升级完成后, 它将会处理 Ping/Pong/Close 帧
                    new WebSocketServerProtocolHandler("/websocket"),
                    // 3. TextFrameHandler 将会处理 TextWebSocketFrames
                    new TextFrameHandler(),
                    // 4. BinaryFrameHandler 将会处理 BinaryWebSocketFrames
                    new BinaryFrameHandler(),
                    // 5. ContinuationFrameHandler 将会处理 ContinuationWebSocketFrames
                    new ContinuationFrameHandler()
            );
        }

        private static class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
                // handle text frame
            }
        }

        private static class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
                // handle binary frame
            }
        }

        private static class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
                // handle continuation frame
            }
        }
    }


    /**
     * Using HTTPS
     * 此示例解释了 Netty 的架构是如何让 "重用" 变成 "杠杆".
     * 可以添加一个新的功能, 甚至是一样重要的加密支持,
     * 只需添加一个 ChannelHandler(此处是 SslHandler) 到 ChannelPipeline.
     */
    static class HttpsCodecInitializer extends ChannelInitializer<Channel> {

        private final SslContext context;

        private final boolean client;

        HttpsCodecInitializer(SslContext context, boolean client) {
            this.context = context;
            this.client = client;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            final SSLEngine engine = context.newEngine(ch.alloc());
            // 1. 添加 SslHandler 到 pipeline 来启用 HTTPS
            pipeline.addFirst("ssl", new SslHandler(engine));

            if (client) {
                // 2. 添加 HttpClientCodec
                pipeline.addLast("codec", new HttpClientCodec());
            } else {
                // 3. 添加 HttpServerCodec, 如果是 server 模式的话
                pipeline.addLast("codec", new HttpServerCodec());
            }
        }

    }


    /**
     * automatically compress HTTP messages
     * 客户端可以通过下面的头现实支持加密模式. 然而服务器不是, 所以不得不压缩它发送的数据:
     * GET /encrypted-area HTTP/1.1
     * Host: www.example.com
     * Accept-Encoding: gzip, deflate
     */
    static class HttpAggregatorInitializer1 extends ChannelInitializer<Channel> {

        private final boolean isClient;

        HttpAggregatorInitializer1(boolean isClient) {
            this.isClient = isClient;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            if (isClient) {
                // 1. 添加 HttpClientCodec
                pipeline.addLast("codec", new HttpClientCodec());
                // 2. 添加 HttpContentDecompressor 用于处理来自服务器的压缩的内容.
                pipeline.addLast("decompressor", new HttpContentDecompressor());
            } else {
                // 3. HttpServerCodec
                pipeline.addLast("codec", new HttpServerCodec());
                // 4. HttpContentCompressor 用于压缩来自 client 支持的 HttpContentCompressor
                pipeline.addLast("compressor", new HttpContentCompressor());
            }
        }

    }

    static class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

        private final boolean client;

        HttpAggregatorInitializer(boolean client) {
            this.client = client;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            if (client) {
                // 1. 添加 HttpClientCodec
                pipeline.addLast("codec", new HttpClientCodec());
            } else {
                // 2. 添加 HttpServerCodec
                pipeline.addLast("codec", new HttpServerCodec());
            }
            // 3. 添加 HttpObjectAggregator 到 ChannelPipeline, 使用最大消息值为 512kb.
            pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
        }
    }

    /**
     * add support for HTTP
     */
    static class HttpPipelineInitializer extends ChannelInitializer<Channel> {

        private final boolean client;

        public HttpPipelineInitializer(boolean client) {
            this.client = client;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            if (client) { // 客户端: 编码请求, 解码响应
                // 1. client: 添加 HttpResponseDecoder 用于处理来自 server 的响应
                pipeline.addLast("decoder", new HttpResponseDecoder());
                // 2. client: 添加 HttpRequestEncoder 用于发送请求到 server
                pipeline.addLast("encoder", new HttpRequestEncoder());
            } else { // 服务端: 编码响应, 解码请求
                // 3. server: 添加 HttpRequestDecoder 用于接收来自 client 的请求
                pipeline.addLast("decoder", new HttpRequestDecoder());
                // 4. server: 添加 HttpResponseEncoder 用来发送响应给 client
                pipeline.addLast("encoder", new HttpResponseEncoder());
            }
        }

    }


    static class SslChannelInitializer extends ChannelInitializer<Channel> {

        private final SslContext context;
        private final boolean startTls; // 是否启用 tls
        private final boolean client; // 使用 client 或者 server 模式

        // 1. constructor
        public SslChannelInitializer(SslContext context, boolean client, boolean startTls) {
            this.context = context;
            this.startTls = startTls;
            this.client = client;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            // 2. 从 SslContext 获得一个新的 SslEngine.
            // 给每个 SslHandler 实例使用一个新的 SslEngine
            final SSLEngine engine = context.newEngine(ch.alloc());
            // 3. 设置 SslEngine 是 client 或者 server 模式
            engine.setUseClientMode(client);
            // 4. 添加 SslHandler 到 pipeline 作为第一个处理器. add-first
            ch.pipeline().addFirst("ssl", new SslHandler(engine, startTls));
        }

    }


    /**
     * @see org.anonymous.netty.NettyPreview.ByteToCharDecoder
     * @see org.anonymous.netty.NettyPreview.CharToByteEncoder
     * 1. CombinedByteCharCodec 的参数是解码器和编码器的实现用于处理进站字节和出站消息
     * 2. 传递 ByteToCharDecoder 和 CharToByteEncoder 实例到 super 构造函数来委托调用使它们结合起来.
     */
    static class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
        public CombinedByteCharCodec() {
            super(new ByteToCharDecoder(), new CharToByteEncoder());
        }
    }

    /**
     * encode char to byte
     * @see org.anonymous.netty.NettyPreview.ByteToCharDecoder
     */
    static class CharToByteEncoder extends MessageToByteEncoder<Character> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Character msg, ByteBuf out) throws Exception {
            out.writeChar(msg); // write char to ByteBuf.
        }

    }

    /**
     * decode byte to char.
     * @see org.anonymous.netty.NettyPreview.CharToByteEncoder
      */
    static class ByteToCharDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            if (in.readableBytes() >= 2) {
                out.add(in.readChar()); // write char to MessageBuf
            }
        }

    }

    static class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame, WebSocketConvertHandler.WebSocketFrame> {

        @Override
        protected void encode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
            final ByteBuf payload = msg.getData().duplicate().retain();
            outByType(msg, out, payload);
        }

        private void outByType(WebSocketFrame msg, List<Object> out, ByteBuf payload) {
            switch (msg.getType()) {
                case BINARY:
                    out.add(new BinaryWebSocketFrame(payload));
                    break;
                case TEXT:
                    out.add(new TextWebSocketFrame(payload));
                    break;
                case CLOSE:
                    out.add(new CloseWebSocketFrame(true, 0, payload));
                    break;
                case CONTINUATION:
                    out.add(new ContinuationWebSocketFrame(payload));
                    break;
                case PONG:
                    out.add(new PongWebSocketFrame(payload));
                    break;
                case PING:
                    out.add(new PingWebSocketFrame(payload));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported websocket msg " + msg);
            }
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, io.netty.handler.codec.http.websocketx.WebSocketFrame msg, List<Object> out) throws Exception {
            inByType(msg, out);
        }

        private void inByType(io.netty.handler.codec.http.websocketx.WebSocketFrame msg, List<Object> out) {
            final ByteBuf copy = msg.content().copy();
            if (msg instanceof BinaryWebSocketFrame) {
                out.add(new WebSocketFrame(WebSocketFrame.FrameType.BINARY, copy));
            } else if (msg instanceof CloseWebSocketFrame) {
                out.add(new WebSocketFrame(WebSocketFrame.FrameType.CLOSE, copy));
            } else if (msg instanceof PingWebSocketFrame) {
                out.add(new WebSocketFrame(WebSocketFrame.FrameType.PING, copy));
            } else if (msg instanceof PongWebSocketFrame) {
                out.add(new WebSocketFrame(WebSocketFrame.FrameType.PONG, copy));
            } else if (msg instanceof TextWebSocketFrame) {
                out.add(new WebSocketFrame(WebSocketFrame.FrameType.TEXT, copy));
            } else if (msg instanceof ContinuationWebSocketFrame) {
                out.add(new WebSocketFrame(WebSocketFrame.FrameType.CONTINUATION, copy));
            } else {
                throw new IllegalArgumentException("Unsupported websocket msg " + msg);
            }
        }


        public static final class WebSocketFrame {
            public WebSocketFrame(FrameType type, ByteBuf data) {
                this.type = type;
                this.data = data;
            }

            enum FrameType {
                BINARY,
                CLOSE,
                PING,
                PONG,
                TEXT,
                CONTINUATION
            }

            private final FrameType type;
            private final ByteBuf data;

            public FrameType getType() {
                return type;
            }

            public ByteBuf getData() {
                return data;
            }
        }
    }


    /**
     * encoder 从出站字节流提取 Integer, 以 String 形式传递给 ChannelPipeline 中的下一个 ChannelOutboundHandler.
     * encodes integer to string
     */
    static class IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
            out.add(String.valueOf(msg)); // 转 Integer 为 String, 并添加到 MessageBuf.
        }

    }


    /**
     * encodes shorts into a ByteBuf.
     * 每个 Short 将占用 ByteBuf 的两个字节.
     */
    static class ShortToByteEncoder extends MessageToByteEncoder<Short> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
            out.writeShort(msg); // 写 Short 到 ByteBuf
        }

    }


    /**
     * 这种保护很重要, 尤其是当你解码一个有可变帧大小的协议的时候.
     */
    static class SafeByteToMessageDecoder extends ByteToMessageDecoder {

        // 缓冲区数据允许最大值
        private static final int MAX_FRAME_SIZE = 1024;

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            final int readableBytes = in.readableBytes();
            if (readableBytes > MAX_FRAME_SIZE) {
                // 忽略所有可读的字节, 并抛出 TooLongFrameException 来通知
                // ChannelPipeline 中的 ChannelHandler 这个帧数据超长.
                in.skipBytes(readableBytes);
                throw new TooLongFrameException("Frame too big!");
            }
        }

    }


    /**
     * 将 Integer 转为 String.
     * 入站消息是按照在类定义中声明的参数类型(这里是 Integer)而不是 ByteBuf 来解析的.
     * 解码消息(String 类型)将被添加到 List, 并传递给下个 ChannelInboundHandler.
     */
    static class IntegerToStringDecoder extends MessageToMessageDecoder<Integer> {

        @Override
        protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
            out.add(String.valueOf(msg)); // 消息转换: Integer -> String
        }

    }


    static class ToIntegerDecoder2 extends ReplayingDecoder<Void> {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            out.add(in.readInt()); // 从入站 ByteBuf 读取整型, 并添加到解码消息的 list 中.
        }
    }


    static class ToIntegerDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            // 可读的字节是否至少四字节: int 四字节.
            if (in.readableBytes() >= 4) {
                // 从入站的 ByteBuf 读取 int, 添加到解码消息的 List 中.
                out.add(in.readInt());
            }
        }
    }


    /**
     * 这段代码的问题是它持有状态: 一个实例变量保持了方法调用的计数. 将这个类的一个实例添加到 ChannelPipeline
     * 并发访问通道时很可能产生错误.
     * 当然, 可以通过在 channelRead 方法加 synchronized 关键字来修正.
     */
    @ChannelHandler.Sharable
    static class NotSharableHandler extends ChannelInboundHandlerAdapter {
        private int count;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // super.channelRead(ctx, msg);
            count++;

            System.out.println("inboundBufferUpdated(...) called the " + count + " time");
            ctx.fireChannelRead(msg); // 传递到下一个 ChannelHandler
        }
    }


    @ChannelHandler.Sharable
    static class SharableHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // super.channelRead(ctx, msg);
            System.out.println("channel read message " + msg);
            ctx.fireChannelRead(msg); // 传递到下一个 ChannelHandler
        }
    }


    static class WriteHandler extends ChannelHandlerAdapter {
        private ChannelHandlerContext ctx;

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            // super.handlerAdded(ctx);
            this.ctx = ctx; // 存储 ctx 的引用供以后使用
        }

        public void send(String msg) {
            ctx.writeAndFlush(msg); // 使用之前存储的 ctx 来发送消息
        }
    }


    /**
     * 消息将会从下一个 ChannelHandler 开始流过 ChannelPipeline, 绕过所有在它之前的 ChannelHandler.
     */
    static class ChannelPipelineTest extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            ctx.write(Unpooled.copiedBuffer("", StandardCharsets.UTF_8));
        }
    }


    /**
     * Accessing the ChannelPipeline from a ChannelHandlerContext
     */
    static class ChannelHandlerContextTest1 extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            final ChannelPipeline pipeline = ctx.pipeline();
            pipeline.write(Unpooled.copiedBuffer("", StandardCharsets.UTF_8));
        }
    }

    /**
     * Accessing the Channel from a ChannelHandlerContext
     */
    static class ChannelHandlerContextTest extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(io.netty.channel.ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            final Channel channel = ctx.channel();
            channel.write(Unpooled.copiedBuffer("", StandardCharsets.UTF_8));
        }
    }

    static class ModifyChannelPipeline extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            final ChannelPipeline pipeline = ctx.pipeline();
            pipeline.addFirst("firstHandler", new ChannelInboundHandlerAdapter());
            pipeline.addLast("lastHandler", new ChannelInboundHandlerAdapter());
            final ChannelInboundHandlerAdapter afterFirst = new ChannelInboundHandlerAdapter();
            pipeline.addAfter("firstHandler", "afterFirst", afterFirst);
            pipeline.addBefore("firstHandler", "b4First", new ChannelInboundHandlerAdapter());

            pipeline.remove(afterFirst);
            pipeline.remove("firstHandler");
            pipeline.remove(ChannelInboundHandlerAdapter.class);

            pipeline.replace("lastHandler", "newLastHandler", new ChannelInboundHandlerAdapter());
            pipeline.replace(ChannelInboundHandlerAdapter.class, "newReplaceHandler", new ChannelInboundHandlerAdapter());
            pipeline.replace(afterFirst, "newReplace", new ChannelInboundHandlerAdapter());
        }
    }


    /**
     * 处理写操作, 并丢弃消息时, 需要释放它.
     */
    static class DiscardOutboundHandler extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            // super.write(ctx, msg, promise);
            ReferenceCountUtil.release(msg); // 释放资源

            promise.setSuccess(); // 通知 ChannelPromise 数据已经被处理
        }
    }


    /**
     * @see io.netty.channel.SimpleChannelInboundHandler 自动释放资源
     * io.netty.channel.SimpleChannelInboundHandler#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)
     */
    static class SimpleDiscardHandler extends SimpleChannelInboundHandler<Object> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 在此方法中无需释放资源,
            // SimpleChannelInboundHandler#channelRead 中已经释放
            // 此方法只是一个 hook, 作为模板方法.
        }
    }

    /**
     * @see io.netty.util.ReferenceCounted#release
     * 释放资源
     * Netty 用一个 WARN-level 日志条目记录未释放的资源, 使其能相当简单地找到代码中地违规实例.
     * 然而, 由于手工管理资源会很繁琐, 可以使用 {@link io.netty.channel.SimpleChannelInboundHandler}
     * 简化问题 {@link org.anonymous.netty.NettyPreview.SimpleDiscardHandler}
     */
    @ChannelHandler.Sharable
    static class DiscardHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // super.channelRead(ctx, msg);
            // 丢弃收到的信息.
            ReferenceCountUtil.release(msg);
        }
    }


    static class ReferenceCounting extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            final Channel channel = ctx.channel();
            final ByteBufAllocator alloc = channel.alloc();
            // ...
            final ByteBuf buf = alloc.directBuffer();

            assert buf.refCnt() == 1;

            /*
            * release 将会递减对象引用的数目. 当这个引用计数达到 0 时, 对象已被释放,
            * 并且该方法返回 true.
            * 如果尝试访问已经释放的对象, 将抛出 {@code IllegalReferenceCountException} 异常.
            * 需要注意的时, 一个特定的类可以定义自己独特的释放计数的规则.
            * 例如, release 可以将引用计数器直接记为 0 而不管当前引用的对象数目.
            * 谁负责 release?
            * 一般情况下, 最后访问的对象负责释放它.
            * */
            final boolean release = buf.release();
            //...
        }
    }


    /**
     * 获取 {@link io.netty.buffer.ByteBufAllocator} 实现的方式
     * @see io.netty.channel.ChannelHandlerContext#alloc()
     * @see io.netty.channel.Channel#alloc()
     */
    static class ByteBufAllocatorTest extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            final ByteBufAllocator all = ctx.alloc();

            // ...
            final Channel channel = ctx.channel();
            final ByteBufAllocator alloc = channel.alloc();
            // ...
        }
    }



    /**
     * Read/write byte
     * read/write 操作, 对 readerIndex/writerIndex 操作, 用于从 ByteBuf 读取就像它是一个流.
     *
     */
    static class ReadWriteByte {

        void readWrite() {
            final ByteBuf buf = Unpooled.copiedBuffer("", StandardCharsets.UTF_8);
            System.out.println(((char) buf.readByte()));

            final int readerIndex = buf.readerIndex();
            final int writerIndex = buf.writerIndex();

            buf.writeByte((byte) '?');

            // write but not read.
            assert readerIndex == buf.readerIndex();
            assert writerIndex != buf.writerIndex();
        }

    }

    /**
     * Get/set byte
     */
    static class GetSetByte {

        void getSet() {
            final ByteBuf buf = Unpooled.copiedBuffer("", StandardCharsets.UTF_8);
            System.out.println(((char) buf.getByte(0)));

            final int readerIndex = buf.readerIndex();
            final int writerIndex = buf.writerIndex();

            buf.setByte(0, (byte) 'x');

            System.out.println(((char) buf.getByte(0)));

            // get/set 不会改变索引
            assert readerIndex == buf.readerIndex();
            assert writerIndex == buf.writerIndex();
        }

    }

    /**
     * Copying a ByteBuf
     */
    static class CopyAByteBuf {

        void copy() {
            final ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", StandardCharsets.UTF_8);

            final ByteBuf copy = buf.copy(0, 1);
            System.out.println(copy.toString(StandardCharsets.UTF_8));

            buf.setByte(0, (byte) 'x');

            // 拷贝数据与源数据不共享
            assert buf.getByte(0) != copy.getByte(0); // true
        }

    }

    /**
     * Slice a ByteBuf
     */
    static class SliceAByteBuf {

        void slice() {
            final ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", StandardCharsets.UTF_8);

            // 创建从索引 0 开始, 长度为 1 的 ByteBuf 的新 slice.
            final ByteBuf sliced = buf.slice(0, 1);
            System.out.println(sliced.toString(StandardCharsets.UTF_8));

            buf.setByte(0, (byte) 'x');

            // buf 的修改将在 slice 可见.
            assert buf.getByte(0) == sliced.getByte(0); // true
        }

    }


    static class ByteBufProcessor {

        /**
         * 寻找 回车 "\r".
         */
        void process() {
            final ByteBuf buf = Unpooled.copiedBuffer("", StandardCharsets.UTF_8);
            final int indexOfCR = buf.forEachByte(ByteProcessor.FIND_CR);
        }

    }


    /**
     * 填充随机整数到缓冲区
     */
    static class WriteData {
        void write() {
            final Random r = new Random();
            final ByteBuf buf = Unpooled.copiedBuffer("", StandardCharsets.UTF_8);
            while (buf.writableBytes() >= 4) {
                buf.writeInt(r.nextInt());
            }
        }
    }


    /**
     * 遍历缓冲区的可读字节
     */
    static class ReadAllData {
        void read() {
            final ByteBuf buf = Unpooled.copiedBuffer("", StandardCharsets.UTF_8);
            while (buf.isReadable()) {
                System.out.println(buf.readByte());
            }
        }
    }


    static class ZeroBasedIndexing {
        /**
         * 遍历 ByteBuf 的所有字节.
         * 通过索引访问不会修改 readIndex/writeIndex.
         * This method does not modify {@code readerIndex} or {@code writerIndex} of this buffer.
         */
        void buf() {
            final ByteBuf buf = Unpooled.copiedBuffer("", StandardCharsets.UTF_8);
            final int length = buf.capacity();
            for (int i = 0; i < length; i++) {
                final byte b = buf.getByte(i);
                System.out.println((char) b);
            }
        }

    }


    /**
     * 一条消息由 header/body 两部分组成, header/body 组装成一条消息发送出去,
     * 可能 body 相同, header 不同, 使用 CompositeByteBuf 就不用每次都重新分配一个新的缓冲区.
     * @see io.netty.buffer.CompositeByteBuf 容器
     */
    static class ByteBufCompositeBuf {

        /**
         * CompositeByteBuf 不允许访问其内部可能存在的支持数组, 也不允许直接访问数据,
         * 这一点类似于直接缓冲区模式.
         */
        void accessData() {
            final CompositeByteBuf buf = Unpooled.compositeBuffer();
            final int length = buf.readableBytes();
            final byte[] array = new byte[length];
            buf.getBytes(buf.readerIndex(), array);

            handleArray(array, 0, length);
        }

        private void handleArray(byte[] array, int i, int length) {
            // 逻辑代码...
        }

        /**
         * 使用 Netty 的 CompositeByteBuf
         */
        void nettyBuf() {
            final CompositeByteBuf buf = Unpooled.compositeBuffer();
            // 可以任选 堆缓冲或直接缓冲
            final ByteBuf header = Unpooled.copiedBuffer("header", StandardCharsets.UTF_8);
            final ByteBuf body = Unpooled.directBuffer();

            buf.addComponents(header, body);
            // ... 其他服务
            buf.removeComponent(0); // 移除头

            /*final int count = buf.numComponents();
            for (int i = 0; i < count; i++) {
                System.out.println(buf.component(i).toString(StandardCharsets.UTF_8));
            }*/
            // CompositeByteBuf 实现 Iterable 接口
            buf.forEach(b -> System.out.println(b.toString(StandardCharsets.UTF_8)));
        }

        /**
         * JDK 的方式, 这种做法显然是低效的, 分配和复制操作不是最优的方法,
         * 操纵数组使代码现得很笨拙.
         */
        void jdkBuf() {
            final ByteBuffer header = ByteBuffer.allocate(1);
            final ByteBuffer body = ByteBuffer.allocate(1);
            // 使用数组保存消息的各个部分
            ByteBuffer[] message = {header, body};

            // 使用副本来合并这两部分
            final ByteBuffer msg = ByteBuffer.allocate(header.remaining() + body.remaining());
            msg.put(header).put(body).flip();
        }
    }


    /**
     * Direct Buffer: 直接缓冲区
     * 由于无法获取直接缓冲的数据, 必须手动复制到数组. 因此, 如果事先就知道容器里的
     * 数据将作为一个数组被访问, 使用堆内存将会更简单.
     */
    static class ByteBufDirectBuf {

        void buf() {
            final ByteBuf buf = Unpooled.directBuffer();
            // 1. 检查 ByteBuf 是否由数组支持, 若不是, 则为一个直接缓冲区
            if (!buf.hasArray()) {
                // 获取可读的字节数
                final int length = buf.readableBytes();
                // 分配新的数组来保存字节
                final byte[] array = new byte[length];
                // 将字节复制到数组
                buf.getBytes(buf.readerIndex(), array);
                handleArray(array, 0, length);
            }
        }

        private void handleArray(byte[] array, int i, int length) {
            // 逻辑处理...
        }
    }


    /**
     * Heap-buffer: 堆缓冲区
     * @see java.nio.ByteBuffer#hasArray()
     * @see java.nio.ByteBuffer#array()
     * -
     * @see io.netty.buffer.ByteBuf#hasArray()
     * @see io.netty.buffer.ByteBuf#array()
     * 访问 非堆缓冲区 ByteBuf 的数组会导致 UnsupportedOperationException, 必须先使用
     * #hasArray() 方法检查当前 ByteBuf 是否支持访问数组.
     * 此用法 JDK-ByteBuf 与 Netty-ByteBuffer 类似.
     */
    static class ByteBufBackingArray {
        void buf() {
            final ByteBuf heapBuf = Unpooled.copiedBuffer("", StandardCharsets.UTF_8);
            /*
            * Returns {@code true} if and only if this buffer has a backing byte array.
             * If this method returns true, you can safely call {@link #array()} and
             * {@link #arrayOffset()}.
            * */
            if (heapBuf.hasArray()) { // 1. 检查 ByteBuf 是否有 支持数组(backing-array)
                // 2. 有则获取数组引用
                final byte[] array = heapBuf.array();
                // 3. 得到第一个字节的偏移量
                final int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
                // 4. 获取可读的字节数
                final int length = heapBuf.readableBytes();
                // 处理数组
                handleArray(array, offset, length);
            }
        }

        private void handleArray(byte[] array, int offset, int length) {
            // 逻辑代码...
        }
    }


    static class MultiThreadChannel extends ChannelInboundHandlerAdapter {
        /**
         * todo: {@link io.netty.channel.ChannelHandlerContext#write}
         *       {@link io.netty.channel.Channel#write}
         *       以上两种方式的 read/write 有何区别?
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            final Channel channel = ctx.channel();
            // 1. data-buffer
            final ByteBuf buf = Unpooled.copiedBuffer("your data", StandardCharsets.UTF_8).retain();
            // 2. 用于写数据到 channel 的 Runnable 任务
            Runnable writer = () -> {
                channel.writeAndFlush(buf.duplicate());
            };
            // 3. 使用线程池来执行 write 任务.
            final ExecutorService executor = Executors.newCachedThreadPool();

            // 4. 一个任务执行
            executor.execute(writer);
            // 5. 另一个任务执行
            executor.execute(writer);
        }
    }

    /**
     * 示例: Writing to a channel
     */
    static class WriteToAChannel extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelRegistered(ctx);
            // 获取 channel 的引用
            final Channel channel = ctx.channel();
            // create data-buffer
            final ByteBuf buf = Unpooled.copiedBuffer("your data", StandardCharsets.UTF_8);
            // write and flush
            final ChannelFuture cf = channel.writeAndFlush(buf);
            // 添加回调: 写操作完成后收到通知
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    // 成功
                    if (future.isSuccess()) {
                        System.out.println("Write successful!");
                    } else { // 错误
                        System.err.println("Write error!");
                        future.cause().printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Asynchronous networking with Netty
     */
    static class NettyNioServer {
        void server(int port) throws InterruptedException {
            final ByteBuf buf = Unpooled.unreleasableBuffer(
                    Unpooled.copiedBuffer("Hi!\r\n", StandardCharsets.UTF_8));
            final NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
            final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                // 1. 创建一个 ServerBootstrap
                final ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        // 2. 使用 NioEventLoopGroup 允许 non-blocking 模式
                        .channel(NioServerSocketChannel.class)
                        .localAddress(new InetSocketAddress(port))
                        // 指定 ChannelInitializer 将给每个接受的连接调用
                        .childHandler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
                            @Override
                            protected void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
                                // 添加 ChannelInboundHandlerAdapter() 接收事件并处理
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        // super.channelActive(ctx);
                                        // 5. 写信息到客户端, 并添加 ChannelFutureListener, 一旦消息写入就关闭连接
                                        ctx.writeAndFlush(buf.duplicate())
                                                .addListener(ChannelFutureListener.CLOSE);

                                    }
                                });
                            }
                        });
                // 6. 绑定服务器来接受连接
                final ChannelFuture f = b.bind().sync();
                // 释放资源
                f.channel().closeFuture().sync();
            } finally {
                bossGroup.shutdownGracefully().sync();
                workerGroup.shutdownGracefully().sync();
            }

        }
    }

    /**
     * Blocking networking with Netty
     */
    @SuppressWarnings("deprecation")
    static class NettyOioServer {
        void server(int port) throws InterruptedException {
            final ByteBuf buf = Unpooled.unreleasableBuffer(
                    Unpooled.copiedBuffer("Hi!\r\n", StandardCharsets.UTF_8));

            EventLoopGroup group = new OioEventLoopGroup();
            try {
                // 1. 创建一个 ServerBootstrap
                final ServerBootstrap b = new ServerBootstrap();
                b.group(group) // 2. 使用 OioEventLoopGroup
                        .channel(OioServerSocketChannel.class)
                        .localAddress(new InetSocketAddress(port))
                        // 3. 指定 ChannelInitializer 将给每个接受的连接调用
                        .childHandler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
                            @Override
                            protected void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
                                // 4. 添加的 ChannelHandler 拦截事件, 并允许它们做出反应
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        // super.channelActive(ctx);
                                        // 5. 写信息到客户端, 并添加 ChannelFutureListener, 一旦消息写入就关闭连接
                                        ctx.writeAndFlush(buf.duplicate())
                                                .addListener(ChannelFutureListener.CLOSE);
                                    }
                                });
                            }
                        });
                // 6. 绑定服务器来接受连接.
                final ChannelFuture f = b.bind().sync();
                f.channel().closeFuture().sync();
            } finally {
                // 7. 释放所有资源
                group.shutdownGracefully().sync();
            }
        }
    }

    /**
     * Asynchronous networking without Netty
     */
    static class PlainNioServer {

        void server(int port) {
            try (
                    final ServerSocketChannel serverChannel = ServerSocketChannel.open();
            ) {
                // non-blocking
                serverChannel.configureBlocking(false);
                final ServerSocket socket = serverChannel.socket();
                // 绑定服务器到指定端口
                socket.bind(new InetSocketAddress(port));

                // 打开 selector 处理 channel
                final Selector selector = Selector.open(); // 不需要 close() 吗?
                // 注册 ServerSocketChannel 到 Selector, 并指定这是专门用于接受(accept)连接.
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);

                final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes(StandardCharsets.UTF_8));

                for (; ; ) {
                    // 等待新的事件来处理, 阻塞, 直到一个 channel 注册.
                    selector.select();
                    final Set<SelectionKey> readKeys = selector.selectedKeys();
                    // 遍历所有注册到 Selector 的 channel 关联的 SelectionKey
                    readKeys.forEach(k -> {
                        readKeys.remove(k); // 避免重复执行当前 SK 的 channel 事件.
                        try {
                            // 是否此 SK 的 channel 准备好 accept 新的连接.
                            // Tests whether this key's channel is ready to accept a new socket connection.
                            if (k.isAcceptable()) {
                                // 获取此 SK 相关联的 channel
                                final ServerSocketChannel server = (ServerSocketChannel) k.channel();
                                // 接受 client
                                final SocketChannel client = server.accept();
                                // non-blocking
                                client.configureBlocking(false);
                                // 将此 client-channel 注册到 selector: 该 channel 关注 write/read 事件
                                client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());

                                System.out.println("Accepted connection from " + client);
                            }

                            // Tests whether this key's channel is ready for writing.
                            if (k.isWritable()) {
                                final SocketChannel client = (SocketChannel) k.channel();
                                final ByteBuffer buf = (ByteBuffer) k.attachment();

                                while (buf.hasRemaining()) {
                                    // 将数据写入到所连接的 client
                                    // 如果网络饱和, 连接是可写的, 那么这个循环将写入数据, 直到该缓冲区是空的.
                                    if (client.write(buf) == 0) {
                                        break;
                                    }
                                }
                                // 关闭连接.
                                client.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            k.cancel();
                            try {
                                k.channel().close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Blocking networking without Netty
     * 虽然可以正常工作, 但是这种阻塞模式在大连接数的情况下就会有很严重的问题,
     * 如客户端连接超时, 服务器响应严重延迟, 性能无法扩展. 为了解决这种情况, 我们可以使用
     * 异步网络处理所有的并发连接, 但问题在于 NIO 和 OIO 的 API 是完全不同的, 所以一个用 OIO
     * 开发的网络应用程序想要使用 NIO 重构代码几乎是重新开发.
     * @see org.anonymous.netty.NettyPreview.PlainNioServer
     */
    static class PlainOioServer {

        void server(int port) throws IOException {
            // 绑定服务器到指定端口
            final ServerSocket server = new ServerSocket(port);
            for (; ; ) {
                // 接受一个连接
                final Socket client = server.accept(); // 阻塞
                System.out.println("Accept connection from " + client);

                // 创建新线程来处理连接
                new Thread(() -> {
                    try (
                            OutputStream out = client.getOutputStream()
                    ) {
                        // 将消息发送到当前连接指向的客户端
                        out.write("Hi!\r\n".getBytes(StandardCharsets.UTF_8));
                        out.flush();

                        // 消息写入, 刷新完毕后关闭连接
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            client.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }).start(); // 启动线程
            }
        }

    }

    /**
     * 异步连接到远程地址示例
     */
    static class ChannelFutureListenerTest extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            final Channel channel = ctx.channel();
            // non-blocking: 立即返回 ChannelFuture
            final ChannelFuture future = channel.connect(new InetSocketAddress("1", 0));
            // 注册 ChannelFutureListener
            future.addListener((ChannelFutureListener) future1 -> {
                // 当 io.netty.util.concurrent.GenericFutureListener.operationComplete 完成时
                // 检查操作结果状态
                if (future.isSuccess()) {
                    // 如果成功, 就创建一个 io.netty.buffer.ByteBuf 保存数据
                    // 注意这里不是 java.nio.ByteBuffer
                    final ByteBuf buf = Unpooled.copiedBuffer("", Charset.defaultCharset());
                    // 异步发送数据到远程. 再次返回 ChannelFuture
                    final ChannelFuture wf = future.channel().writeAndFlush(buf);
                    // ..
                } else { // 失败: 获取失败信息
                    // 根据需要处理失败情形
                    // 可以在失败之后建立新的连接
                    final Throwable cause = future.cause();
                    cause.printStackTrace();
                    // ...
                }
            });
        }
    }

    /**
     * 异步示例: channel.connect 直接返回.
     */
    static class ChannelFutureTest extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            final Channel channel = ctx.channel();
            final ChannelFuture future = channel.connect(new InetSocketAddress("xxxxx", 24));
        }
    }


    /**
     * 回调示例: 一旦一个新的连接建立, 调用 channelActive(), 打印连接者的 address.
     */
    static class ConnectHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            // super.channelActive(ctx);
            final Channel channel = ctx.channel();
            final SocketAddress socketAddress = channel.remoteAddress();
            System.out.println("Client [" + socketAddress + "]" + " connected.");
        }

    }

}
