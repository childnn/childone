package nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/29 15:51
 * 不同的机器可以使用不同的字节存储顺序（Endians）来存储数据。
 * “高位优先”（Big Endian）：将最重要的字节放在最低内存地址中，
 * 而“低位优先”（Little Endian）：将最重要的字节放在最高内存地址中。
 * <p>
 * 当存储大于单字节的数据时，如 **int**、**float** 等，我们可能需要考虑字节排序问题。
 * **ByteBuffer** 以“高位优先”形式存储数据；通过网络发送的数据总是使用“高位优先”形式。
 * 我们可以 使用 **ByteOrder** 的 `order()` 方法和参数 **ByteOrder.BIG_ENDIAN**
 * 或 **ByteOrder.LITTLE_ENDIAN** 来改变它的字节存储次序。
 * ---
 * 将数据作为 **short** 型来读取（`ByteBuffer.asshortbuffer()`)），生成数字 97 （00000000 01100001）。
 * 更改为“低位优先”后 将生成数字 24832 （01100001 00000000）。
 */
public class Endians {
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[12]); // 如果减小这里的字节分配数, java.nio.BufferOverflowException
        bb.asCharBuffer().put("abcdef"); // 六个字母, 十二个字节
        System.out.println(Arrays.toString(bb.array()));

        bb.rewind();

        bb.order(ByteOrder.BIG_ENDIAN);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));

        bb.rewind();

        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));
    }
}
