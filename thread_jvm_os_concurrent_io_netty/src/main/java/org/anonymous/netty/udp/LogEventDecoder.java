package org.anonymous.netty.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 16:09
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        // 1. 获取 DatagramPacket 中数据的引用
        final ByteBuf data = msg.content();
        // 2. 获取 SEPARATOR 的索引
        final int i = data.indexOf(0, data.readableBytes(), LogEvent.SEPARATOR);
        // 3. 从数据中读取文件名
        final String fileName = data.slice(0, i).toString(StandardCharsets.UTF_8);
        // 4. 读取数据中的日志信息
        final String logMsg = data.slice(i + 1, data.readableBytes()).toString(StandardCharsets.UTF_8);

        // System.out.println(logMsg);

        // 5. 构造新的 LogEvent 对象并将其添加到列表中
        final LogEvent event = new LogEvent(msg.recipient(), System.currentTimeMillis(), fileName, logMsg);

        out.add(event);
    }

}
