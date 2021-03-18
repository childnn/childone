package org.anonymous.netty.memcached;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/2 10:48
 */
public class MemcachedResponseDecoderTest {

    @Test
    public void testMemcachedResponseDecoder() {
        final EmbeddedChannel channel = new EmbeddedChannel(new MemcachedResponseDecoder());

        byte magic = 1;
        byte opCode = Opcode.SET;

        final byte[] key = "Key1".getBytes(StandardCharsets.US_ASCII);
        final byte[] body = "Value".getBytes(StandardCharsets.US_ASCII);
        final int id = (int) System.currentTimeMillis();
        final long cas = System.currentTimeMillis();

        // 2. new buffer and write data, 与二进制协议的结构相匹配
        final ByteBuf buffer = Unpooled.buffer();
        buffer.writeByte(magic)
                .writeByte(opCode)
                .writeShort(key.length)
                .writeByte(0)
                .writeByte(0)
                .writeShort(Status.KEY_EXISTS)
                .writeInt(body.length + key.length)
                .writeInt(id)
                .writeLong(cas)
                .writeBytes(key)
                .writeBytes(body);

        // 3. write buffer to channel and check if response exists
        Assertions.assertTrue(channel.writeInbound(buffer));

        final MemcachedResponse response = channel.readInbound();
        // 4. check MemcachedResponse and the expected values.
        assertResponse(response, magic, opCode, Status.KEY_EXISTS, 0, 0, id, cas, key, body);
    }

    private void assertResponse(MemcachedResponse response, byte magic,
                                byte opCode, short status, int expires,
                                int flags, int id, long cas, byte[] key, byte[] body) {
        Assertions.assertEquals(magic, response.magic());
        Assertions.assertArrayEquals(key, response.key().getBytes(StandardCharsets.US_ASCII));
        Assertions.assertEquals(opCode, response.opCode());
        Assertions.assertEquals(status, response.status());
        Assertions.assertEquals(cas, response.cas());
        Assertions.assertEquals(expires, response.expires());
        Assertions.assertEquals(flags, response.flags());
        Assertions.assertArrayEquals(body, response.data().getBytes(StandardCharsets.US_ASCII));
        Assertions.assertEquals(id, response.id());
    }

    @Test
    public void testMemcachedResponseDecoderFragments() {
        // 5.
        final EmbeddedChannel channel = new EmbeddedChannel(new MemcachedResponseDecoder());

        byte magic = 1;
        byte opCode = Opcode.SET;

        final byte[] key = "Key1".getBytes(StandardCharsets.US_ASCII);
        final byte[] body = "Value".getBytes(StandardCharsets.US_ASCII);
        int id = (int) System.currentTimeMillis();
        long cas = System.currentTimeMillis();

        // 6. write new buffer, 与写入数据的二进制协议相匹配
        final ByteBuf buffer = Unpooled.buffer()
                .writeByte(magic)
                .writeByte(opCode)
                .writeShort(key.length)
                .writeByte(0)
                .writeByte(0)
                .writeShort(Status.KEY_EXISTS)
                .writeInt(body.length + key.length)
                .writeInt(id)
                .writeLong(cas)
                .writeBytes(key)
                .writeBytes(body);

        // 7. 缓冲区分割成三个片段
        final ByteBuf fragment1 = buffer.readBytes(8);
        final ByteBuf fragment2 = buffer.readBytes(24);
        ByteBuf fragment3 = buffer;

        // 8. 写第一个片段, 没有 response 创建, 因为不是所有数据都准备好了
        Assertions.assertFalse(channel.writeInbound(fragment1));
        // 9. write the second fragment and check, no new MemcachedResponse been created
        // since not all data prepared
        Assertions.assertFalse(channel.writeInbound(fragment2));
        // 10. write the last fragment and check, return true since we receive all of the data
        Assertions.assertTrue(channel.writeInbound(fragment3));

        final MemcachedResponse response = channel.readInbound();
        // 11. expect values.
        assertResponse(response, magic, opCode, Status.KEY_EXISTS, 0, 0, id, cas, key, body);
    }

}
