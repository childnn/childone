package org.anonymous.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/5 10:40
 * 业务核心处理逻辑
 * 服务器 handler: 这个组件实现了服务器的业务逻辑, 决定了连接创建后和接收到信息后该如何处理
 * 通过 ChannelHandler 来实现服务器的逻辑
 * EchoServer 将 接收到的数据的拷贝 发送给客户端. 实现 {@link io.netty.channel.ChannelInboundHandler}
 * 定义处理入站事件的方法. 此应用很简单, 只需要继承 {@link io.netty.channel.ChannelInboundHandlerAdapter}
 * 只需覆盖 {@link io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, Object)}
 * 1. ChannelHandler 是给不同类型的事件调用的
 * 2. 应用程序实现或扩展 ChannelHandler 挂接到事件生命周期和提供自定义应用逻辑.
 * ---
 * @see io.netty.channel.ChannelInitializer 官方文档.
 */
@ChannelHandler.Sharable // 标识这个类的实例之间可以在 channel 里共享
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 每个信息入站都会调用此方法.
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // super.channelRead(ctx, msg);
        final ByteBuf in = (ByteBuf) msg;
        System.out.println("Server received: " + in.toString(StandardCharsets.UTF_8));

        // 注意: 此处 write 之后没有 flush
        // 实际的 flush 在 channelReadComplete() 方法中进行.
        ctx.write(in); // 将接收的信息直接返回给发送者
    }

    /**
     * 通知处理器最后的 channelRead() 是当前批处理中的最后一条消息时调用.
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // super.channelReadComplete(ctx);
        // 可以使用链式编程, 这里为了便于理解每个步骤, 分开写.
        // flush 所有待审消息到远程节点. 关闭通道后, 操作完成.
        final ChannelFuture channelFuture = ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * read 操作时捕获到异常时调用
     * 如果不捕获异常, 或发生什么?
     * 每个 Channel 都有一个关联的 {@link io.netty.channel.ChannelPipeline}
     * 它代表了 {@link io.netty.channel.ChannelHandler} 实例的链.
     * 适配器处理的实现只是将一个处理方法调用转发到链中的下一个处理器. 因此, 如果一个 Netty 应用
     * 不覆盖 exceptionCaught, 那么这些错误将最终到达 ChannelPipeline, 并且结束警告将被记录.
     * 因此, 应该至少提供一个实现 exceptionCaught 的 ChannelHandler.
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

}
