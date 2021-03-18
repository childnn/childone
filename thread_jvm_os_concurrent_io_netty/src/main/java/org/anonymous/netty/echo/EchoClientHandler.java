package org.anonymous.netty.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/2/5 13:33
 * 关于 {@link io.netty.channel.SimpleChannelInboundHandler} 与 {@link io.netty.channel.ChannelInboundHandler}
 * 在客户端, 当 channelRead0() 完成, 我们已经拿到入站信息. 当方法返回时, SimpleChannelInboundHandler 会小心的释放对
 * ByteBuf(保存信息)的引用. 而在 服务端, 我们需要入站信息返回给发送者, 由于 write() 是异步的, 在 channelRead() 返回时,
 * 可能还未完成. 所以, 使用 {@link io.netty.channel.ChannelInboundHandlerAdapter}, 无需释放信息.
 * 最后在 channelReadComplete() 调用 writeAndFlush() 来释放信息.
 */
@ChannelHandler.Sharable // 标记这个类的实例可以在 channel 里共享
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 服务器的连接被建立后调用: 将消息发送到服务端.
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // super.channelActive(ctx);
        // 当被通知该 channel 是活动的时候就发送消息.
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", StandardCharsets.UTF_8));
    }

    /**
     * 接收消息时调用
     * 由服务器所发送的消息可以以块的形式被接收. 即, 当服务器发送 5 个字节时, channelRead0() 方法可以被调用两次,
     * 第一次用一个 ByteBuf(Netty 字节容器)装载 3 个字节, 第二次用一个 ByteBuf 装载 2 个字节.
     * 唯一要保证的是, 该字节将按照它们发送的顺序分别被接收.
     * read 完毕直接输出, 与服务端不同, 这里不需要其他操作, 实现 channelRead0() 即可, ByteBuf 会自动释放
     * 而 server 读取信息后, 还要往 client 信息. 因此实现方式不同.
     * @param ctx
     * @param in
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        System.out.println("Client received: " + in.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }

}
