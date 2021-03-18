package org.anonymous.netty.memcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/2 10:06
 * 一个 Memcached-response 有 24 bytes 头, 我们不知道是否输入的 ByteBuf 包含所有数据.
 * 为确保解码有效的数据, 检查可读的字节数量至少为 24 bytes.
 */
public class MemcachedResponseDecoder extends ByteToMessageDecoder {

    // 当前解析状态
    private enum State {
        HEADER,
        BODY
    }

    private State state = State.HEADER;
    private int totalBodySize;
    private byte magic;
    private byte opCode;
    private short keyLength;
    private byte extraLength;
    private short status;
    private int id;
    private long cas;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 3. 根据解析状态切换
        switch (state) {
            case HEADER:
                if (in.readableBytes() < 24) {
                    // 4. 如果不是至少 24 字节可读, 它不可能读整个头部, 所以 return.
                    // 等待再通知一次数据准备 read
                    return; // response header is 24 bytes
                }
                // 5. read 所有头字段
                magic = in.readByte();
                opCode = in.readByte();
                keyLength = in.readShort();
                extraLength = in.readByte();
                in.skipBytes(1);
                status = in.readShort();
                totalBodySize = in.readInt();
                id = in.readInt(); // referred to in the protocol spec as opaque
                cas = in.readLong();

                state = State.BODY;
                // break;
            case BODY:
                if (in.readableBytes() < totalBodySize) {
                    // 6. until we have the entire payload return
                    return;
                }
                int flags = 0;
                int expires = 0;
                int actualBodySize = totalBodySize;
                // 7. 检查是否额外 flag
                if (extraLength > 0) {
                    flags = in.readInt();
                    actualBodySize -= 4;
                }
                // 8. 检查响应包含 expire 字段
                if (extraLength > 4) {
                    expires = in.readInt();
                    actualBodySize -= 4;
                }
                String key = "";
                // 9. 检查响应是否包含一个 key
                if (keyLength > 0) {
                    final ByteBuf keyBytes = in.readBytes(keyLength);
                    key = keyBytes.toString(StandardCharsets.UTF_8);
                    actualBodySize -= keyLength;
                }
                // 10. 读实际的 body 的 payload
                final ByteBuf body = in.readBytes(actualBodySize);
                final String data = body.toString(StandardCharsets.UTF_8);
                out.add(new MemcachedResponse(magic, opCode, (byte) 0, status, id, cas, flags, expires, key, data));
                state = State.HEADER;
        }

    }

}
