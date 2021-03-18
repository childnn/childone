package org.anonymous.netty.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 16:15
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
        String sb = msg.getReceivedTimestamp() +
                " [" +
                msg.getSource().toString() +
                "] [" +
                msg.getMsg();

        System.out.println(sb);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
