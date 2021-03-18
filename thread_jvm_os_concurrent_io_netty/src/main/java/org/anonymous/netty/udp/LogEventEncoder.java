package org.anonymous.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 14:43
 * 继承 MessageToMessageEncoder 简化配置: LogEvent -> DatagramPacket
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    private final InetSocketAddress remoteAddress;

    // 1. LogEventEncoder 创建了 DatagramPacket 消息发送到指定的 InetSocketAddress
    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent logEvent, List<Object> out) throws Exception {
        // 2. 写文件名到 ByteBuf
        final byte[] file = logEvent.getLogFile().getBytes(StandardCharsets.UTF_8);
        final byte[] msg = logEvent.getMsg().getBytes(StandardCharsets.UTF_8);
        final ByteBuf buf = ctx.alloc().buffer(file.length + msg.length + 1);
        buf.writeBytes(file)
                .writeByte(LogEvent.SEPARATOR) // 3. 添加一个 分隔符
                .writeBytes(msg); // 4. 写一个日志消息到 ByteBuf
        // 此处是 netty 的 DatagramPacket 而不是 java.net.DatagramPacket
        out.add(new DatagramPacket(buf, remoteAddress)); // 5. 添加新的 DatagramPacket 到出站消息
    }

}
