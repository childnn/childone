package nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/29 14:49
 */
public class IntBufferDemo {
    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(BSIZE);
        IntBuffer ib = bb.asIntBuffer();
        // 保存 int 数组：
        ib.put(new int[]{11, 42, 47, 99, 143, 811, 1016});
        //绝对位置读写：
        System.out.println(ib.get(3));
        ib.put(3, 1811);
        // 在重置缓冲区前设置新的限制
        ib.flip();
        while (ib.hasRemaining()) {
            int i = ib.get();
            System.out.println(i);
        }
    }
}
