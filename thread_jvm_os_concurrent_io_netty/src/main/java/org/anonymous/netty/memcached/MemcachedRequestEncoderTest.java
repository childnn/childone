package org.anonymous.netty.memcached;

import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/2 10:29
 */
public class MemcachedRequestEncoderTest {

    @Test
    public void testMemcachedRequestEncoder() {
        // 1. 数据源: request encode to ByteBuf
        final MemcachedRequest request = new MemcachedRequest(Opcode.SET, "key1", "value1");

        // 2. Test-channel
        final EmbeddedChannel channel = new EmbeddedChannel(new MemcachedRequestEncoder());
        // 3. write request to channel and check encode or not
        channel.writeOutbound(request);

        ByteBuf encoded = channel.readOutbound();

        // 4. check 是否编码成功
        Assertions.assertNotNull(encoded);
        // 5. check magic 是否正确写入 ByteBuf
        Assertions.assertEquals(request.magic(), encoded.readUnsignedByte());
        // 6. check opCode(SET) 是否正确写入
        Assertions.assertEquals(request.opCode(), encoded.readByte());
        // 7. 检查 key 长度
        Assertions.assertEquals(4, encoded.readShort());
        // 8. 检查是否包含额外信息
        Assertions.assertEquals((byte) 0x08, encoded.readByte());
        // 9. 检查数据类型
        Assertions.assertEquals((byte) 0, encoded.readByte());
        // 10. 检查是否保留数
        Assertions.assertEquals(0, encoded.readShort());
        // 11. 检查 body 的整体大小: 计算方式 key.length + body.length + extras
        Assertions.assertEquals(4 + 6 + 8, encoded.readInt());
        // 12. check id
        Assertions.assertEquals(request.id(), encoded.readInt());
        // 13. check cas
        Assertions.assertEquals(request.cas(), encoded.readLong());
        // 14. check flag
        Assertions.assertEquals(request.flags(), encoded.readInt());
        // 15. check expires
        Assertions.assertEquals(request.expires(), encoded.readInt());

        // 16. 检查 key, body 是否正确
        final byte[] data = new byte[encoded.readableBytes()];
        encoded.readBytes(data);
        Assertions.assertArrayEquals((request.key() + request.body()).getBytes(StandardCharsets.UTF_8), data);
        // 17. check if readable
        Assertions.assertFalse(encoded.isReadable());

        Assertions.assertFalse(channel.finish());
        Assertions.assertNull(channel.readInbound());
    }

}
