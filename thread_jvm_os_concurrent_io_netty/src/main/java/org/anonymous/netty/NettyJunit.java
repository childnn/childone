package org.anonymous.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see io.netty.channel.embedded.EmbeddedChannel 测试用例, 嵌入式传输
 * @see io.netty.channel.embedded.EmbeddedChannel#writeInbound(Object...)
 * 写一个入站消息到 EmbeddedChannel, 如果数据能从 EmbeddedChannel 通过 readInbound() 读到, 则返回 true.
 * @see io.netty.channel.embedded.EmbeddedChannel#readInbound()
 * 从 EmbeddedChannel 读到 inbound 消息. 任何返回遍历整个 ChannelPipeline. 如果读取还没有准备, 则返回 null.
 * @see io.netty.channel.embedded.EmbeddedChannel#writeOutbound(Object...)
 * 写一个出站消息到 EmbeddedChannel. 如果数据能从 EmbeddedChannel 通过 readOutbound() 读到, 则返回 true.
 * @see io.netty.channel.embedded.EmbeddedChannel#readOutbound()
 * 从 EmbeddedChannel 读到出站消息. 任何返回遍历整个 ChannelPipeline. 如果读取还未准备, 则返回 null.
 * @see io.netty.channel.embedded.EmbeddedChannel#finish()
 * @see io.netty.channel.embedded.EmbeddedChannel#finishAndReleaseAll()
 * 如果从 inbound/outbound 中能读到数, 标记 EmbeddedChannel 完成并返回. 这同时会调用 EmbeddedChannel 的关闭方法.
 * 参考图片: EmbeddeChanel-2021-02-26 144622.png
 * 测试异常处理
 * 有时候传输的入站或出站数据不够, 通常这种情况也需要处理, 例如抛出一个异常. 这可能时你错误的输入或处理大的资源或其他异常导致.
 * @since 2021/2/26 13:46
 * 一个入站/出站数据写入一个 EmbeddedChannel 然后检查是否到达 ChannelPipeline 结束.
 * 这样可以确定消息编码或解码和 ChannelHandler 是否操作被触发.
 */
public class NettyJunit {

    @Test
    public void test() {
        System.out.println((char) 0xFF);
        System.out.println((char) 0x80);
        System.out.println((char) 0x7f);
    }

    //--------------------------------------------
    // Test-Netty
    //--------------------------------------------
    @Test // 1.
    public void testFramesDecoded() {
        // 2. 新建 buffer, 填充长度为 9.
        final ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        final ByteBuf input = buffer.duplicate();

        // 3. 指定最小读取帧长度
        final EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        // 4. 往 channel 写数据, 帧长度必须大于最小指定帧长度, writeInbound 才会返回 true.
        Assertions.assertFalse(channel.writeInbound(input.readBytes(2))); // 第一次写 2 个 bytes, 小于 FixedLengthFrameDecoder 的最小读取帧
        Assertions.assertTrue(channel.writeInbound(input.readBytes(7))); // 第二次写 input 中的剩下 7 个 bytes, 可以正常写入, 正常被 FixedLengthFrameDecoder 读取

        // 5. 标记 finish
        Assertions.assertTrue(channel.finish());

        // 6. read 并校验
        // 上面是从 inbound 写入, 这里从 inbound 读出
        // 每次 3 个 可读字节的 ByteBuf, 分三次读取
        ByteBuf read = channel.readInbound();
        Assertions.assertEquals(buffer.readSlice(3), read);
        System.out.println(read.array());
        read.release();

        read = channel.readInbound();
        Assertions.assertEquals(buffer.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        Assertions.assertEquals(buffer.readSlice(3), read);
        read.release();

        // 读取完毕
        Assertions.assertNull(channel.readInbound());
        buffer.release();
    }

    @Test
    public void testEncoded() {
        final ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 10; i++) {
            // 2. 新建 ByteBuf 并写入负整数
            buffer.writeInt(i * -1);
        }

        // 3. 新建 EmbeddedChannel 并安装 AbsIntegerEncoder 来测试
        final EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());

        // 4. 写 ByteBuf 并预测 readOutbound() 产生的数据
        Assertions.assertTrue(channel.writeOutbound(buffer));

        // 5. 标记 finish
        Assertions.assertTrue(channel.finish());

        for (int i = 0; i < 10; i++) {
            // 6. 读取产生的消息, 检查负值已经编码为绝对值
            Assertions.assertEquals(i, (Integer) channel.readOutbound());
        }
        Assertions.assertNull(channel.readOutbound());
    }

    @Test
    public void testFramesDecoded2() {
        final ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        final ByteBuf input = buffer.duplicate();

        // 3.
        final EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));

        // 4. 写入 2 个字节并预测生产的新帧
        Assertions.assertTrue(channel.writeInbound(input.readBytes(2)));

        try {
            // 5. 写入帧长度大于定义的最大长度
            channel.writeInbound(input.readBytes(4));
            // 6. 如果上一行没有抛异常, 此处将测试失败.
            // 如果实现类中 exceptionCaught() 方法自己处理了异常, 此处不会抛出.
            Assertions.fail();
        } catch (TooLongFrameException e) {
            // e.printStackTrace();
            System.out.println("意料之中的错误:" + e);
        }

        // 7.
        Assertions.assertTrue(channel.writeInbound(input.readBytes(3)));

        // 8. 标记 channel finish
        Assertions.assertTrue(channel.finish());

        ByteBuf read = channel.readInbound();
        // 9. 第一次写入的 2 个 bytes
        Assertions.assertEquals(buffer.readSlice(2), read);
        read.release();

        read = channel.readInbound();
        // 跳过 第二次异常的 4 个 bytes, 读取第三次写入的 3 个 bytes
        Assertions.assertEquals(buffer.skipBytes(4).readSlice(3), read);
        read.release();

        buffer.release();
    }

    // 集成 ByteToMessageDecoder 用于解码入站字节到消息
    static class FrameChunkDecoder extends ByteToMessageDecoder {
        private final int maxFrameSize;

        // 2. 指定最大需要的帧
        FrameChunkDecoder(int maxFrameSize) {
            this.maxFrameSize = maxFrameSize;
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            final int readableBytes = in.readableBytes();
            if (readableBytes > maxFrameSize) {
                // 3. 如果帧太大就丢弃并抛出 TLFE
                // discard the bytes
                in.clear();
                throw new TooLongFrameException();
            }
            // 4. 同时从 ByteBuf 读到新帧
            final ByteBuf buf = in.readBytes(readableBytes);
            // 5. 添加帧到解码消息 List
            out.add(buf);
        }

    }


    // 1. 集成 MessageToMessageEncoder 用于编码消息到另外一种格式
    static class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

        @Override
        protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            // 2. 检查是否有足够的字节用于编码
            while (in.readableBytes() >= 4) {
                // 3. 读取下一个输入 ByteBuf 产出的 int 值, 并计算绝对值.
                int value = Math.abs(in.readInt());
                // 4. 写 int 到编码的消息 List.
                out.add(value);
            }
        }

    }


    /**
     * 该 handler 的作用: 有足够的 入站 数据时可以读取产生固定大小的包,
     * 如果没有足够的数据可以读取, 则会等待下一个数据块并再次检查是否可以产生一个完整包.
     */
    static class FixedLengthFrameDecoder
            extends ByteToMessageDecoder { // 1. 集成 ByteToMessageDecoder 用来处理入站的字节并将它们解码为消息

        private final int frameLength;

        // 2. 指定产出的帧的长度
        FixedLengthFrameDecoder(int frameLength) {
            if (frameLength <= 0) {
                throw new IllegalArgumentException("frameLength must be a positive integer: " + frameLength);
            }
            this.frameLength = frameLength;
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            // 3. 检查是否有足够的字节用于读到下个帧
            if (in.readableBytes() >= frameLength) {
                // 4. 从 ByteBuf 读取新帧
                final ByteBuf buf = in.readBytes(frameLength);
                // 5. 添加帧到解码好的消息 List
                out.add(buf);
            }
        }

    }
}
