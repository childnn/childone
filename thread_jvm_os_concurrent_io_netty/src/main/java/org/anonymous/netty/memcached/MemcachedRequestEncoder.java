package org.anonymous.netty.memcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/1 19:13
 */
public class MemcachedRequestEncoder
        extends MessageToByteEncoder<MemcachedRequest> { // 1. 该类负责编码 MemcachedRequest 为一系列字节

    Charset UTF8 = StandardCharsets.UTF_8;

    @Override
    protected void encode(ChannelHandlerContext ctx,
                          MemcachedRequest msg, ByteBuf out) throws Exception {
        // 转换 key, body 为字节数组
        final byte[] key = msg.key().getBytes(UTF8);
        final byte[] body = msg.body().getBytes(UTF8);
        // total size of the body = key size + content size + extras size
        // 3. 计算 body 大小
        int bodySize = key.length + body.length + (msg.hasExtras() ? 8 : 0);

        // 4. write magic byte
        out.writeByte(msg.magic())
                // 5. write opcode byte
                .writeByte(msg.opCode())
                // 6. write key length (2 byte)
                .writeShort(key.length); // key length is max 2 bytes i.e. a Java short
        // 7. write extras length (1 byte)
        final int extraSize = msg.hasExtras() ? 0x08 : 0x0;
        out.writeByte(extraSize);

        // 8. byte is the data type, not currently implemented in Memcached but required
        out.writeByte(0); // 数据类型, 默认为 0

        // 9. next two bytes are reserved, not currently implemented but are required
        out.writeShort(0);

        // 10. write total body length (4 bytes - 32 bit int)
        out.writeInt(bodySize);
        // 11. write opaque(4 bytes) - a 32 bit int that is returned in the response
        out.writeInt(msg.id());

        // 24 byte header finishes with the CAS
        // 12. write CAS(8 bytes)
        out.writeLong(msg.cas());

        if (msg.hasExtras()) {
            // 13. write extras (flag and expiry, 4 bytes each) - 8 bytes total
            out.writeInt(msg.flags())
                    .writeInt(msg.expires());
        }

        // 14. write key
        out.writeBytes(key)
                // 15. write value
                .writeBytes(body);

    }
}
