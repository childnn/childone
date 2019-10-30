package nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/29 17:39
 * 缓冲区由数据和四个索引组成，以有效地访问和操作该数据：mark、position、limit 和 capacity（标记、位置、限制和容量）。
 * 伴随着的还有一组方法可以设置和重置这些索引，并可查询它们的值。
 * <p>
 * | :-------------------- | :----------------------------------------------------------- |
 * | capacity()        | 返回缓冲区的 capacity                                        |
 * | clear()           | 清除缓冲区，将 position 设置为零并 设 limit 为 capacity;可调用此方法来覆盖现有缓冲区 |
 * | flip()            | 将 limit 设置为 position，并将 position 设置为 0;此方法用于准备缓冲区，以便在数据写入缓冲区后进行读取 |
 * | limit()           | 返回 limit 的值                                              |
 * | limit(int limit)  | 重设 limit                                                   |
 * | mark()            | 设置 mark 为当前的 position                                  |
 * | position()        | 返回 position                                                |
 * | position(int pos) | 设置 position                                                |
 * | remaining()       | 返回 limit 到 position                                       |
 * | hasRemaining()    | 如果在 position 与 limit 中间有元素，返回 `true`             |
 */
public class UsingBuffers {

    // position 指向缓冲区中的第一个元素，capacity 和 limit 紧接在最后一个元素之后。在`symmetricgrab()` 中，
    // **while** 循环迭代到 position 等于 limit。当在缓冲区上调用相对位置的 `get()` 或 `put()` 函数时，
    // 缓冲区的位置会发生变化。你可以调用绝对位置的 `get()` 和 `put()` 方法，它们包含索引参数：`get()` 或 `put()`
    // 发生的位置。这些方法不修改缓冲区 position 的值。
    private static void symmetricScramble(CharBuffer buffer) {
        // 循环一次, 指针 position 移动两个位置
        //在下一次循环中，将 mark 设置为 position 的当前值：
        while (buffer.hasRemaining()) {
            // 先定位, 便于 reset
            buffer.mark();
            // 获取当前位置, 移动指针
            char c1 = buffer.get();
            char c2 = buffer.get();
            buffer.reset(); // Resets this buffer's position to the previously-marked position.
            //执行交换，我们在位置 0 处编写 `c2`，在位置 1 处编写 `c1`。
            buffer.put(c2).put(c1);
        }
    }

    // java.nio.CharBuffer.toString() 显示 position 和 limit 之间的字符
    public static void main(String[] args) {
        char[] data = "UsingBuffers".toCharArray();
        ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
        CharBuffer cb = bb.asCharBuffer();
        cb.put(data);
        // rewind: The position is set to zero, the mark is discarded.
        //在 **while** 循环的末尾，position 位于缓冲区的末尾。如果显示缓冲区，则只显示位置和限制之间的字符。
        // 因此，要显示缓冲区的全部内容，必须使用 `rewind()` 将 position 设置为缓冲区的开始位置。
        System.out.println(cb.rewind());
        symmetricScramble(cb);
        System.out.println(cb.rewind());
        symmetricScramble(cb);
        System.out.println(cb.rewind());
    }

}
