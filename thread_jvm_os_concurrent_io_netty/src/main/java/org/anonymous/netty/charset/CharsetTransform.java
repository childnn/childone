package org.anonymous.netty.charset;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/21 16:51
 */
public class CharsetTransform {

    public static void main(String[] args) throws CharacterCodingException {
        final Charset utf8 = StandardCharsets.UTF_8;
        final CharsetEncoder encoder = utf8.newEncoder();
        final CharsetDecoder decoder = utf8.newDecoder();

        // 创建一个 CharBuffer 对象
        final CharBuffer cb = CharBuffer.allocate(3);
        cb.put('孙');
        cb.put('b');
        cb.put('c');

        System.out.println("cb.capacity() = " + cb.capacity());

        // 读写切换
        cb.flip();

        //
        final ByteBuffer bb = encoder.encode(cb);
        final int capacity = bb.capacity();
        System.out.println("capacity = " + capacity);

        // clear 的目的就是让 limit 值设置为 capacity 的值
        bb.clear(); // 如果不 clear, 下面使用 capacity 会报错, 因为 limit 的限制, limit-position 之间都为空值, 无法访问.

        for (int i = 0; i < /*bb.limit()*/capacity; i++) { // 如果使用 capacity, 会报 IOOBE,
            System.out.println(bb.get(i) + " "); // 索引: 绝对定位取值, 不会改变 position 的值
        }

        // 将 ByteBuffer 的数据解码成字符序列: CharBuffer
        System.out.println(decoder.decode(bb));
    }

}
