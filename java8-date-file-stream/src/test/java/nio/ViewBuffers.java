package nio;

import java.nio.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/29 15:42
 */
public class ViewBuffers {
    public static void main(String[] args) {
        // **ByteBuffer** 通过“包装”一个 8 字节数组生成，然后通过所有不同基本类型的视图缓冲区显示该数组。
        ByteBuffer bb = ByteBuffer.wrap(new byte[]{0, 0, 0, 0, 0, 0, 0, 'a'});

        bb.rewind();
        System.out.print("Byte Buffer -- 一个字节 * 8 -- ");
        while (bb.hasRemaining())
            System.out.print(bb.position() + " -> " + bb.get() + ", ");
        System.out.println();

        CharBuffer cb = ((ByteBuffer) bb.rewind()).asCharBuffer();
        System.out.print("Char Buffer -- 两个字节 * 4 -- ");
        while (cb.hasRemaining())
            System.out.print(cb.position() + " -> " + cb.get() + ", ");
        System.out.println();

        FloatBuffer fb = ((ByteBuffer) bb.rewind()).asFloatBuffer();
        System.out.print("Float Buffer -- 两个字节 * 4 -- ");
        while (fb.hasRemaining())
            System.out.print(fb.position() + " -> " + fb.get() + ", ");
        System.out.println();

        IntBuffer ib = ((ByteBuffer) bb.rewind()).asIntBuffer();
        System.out.print("Int Buffer -- 四个字节 * 2 -- ");
        while (ib.hasRemaining())
            System.out.print(ib.position() + " -> " + ib.get() + ", ");
        System.out.println();

        LongBuffer lb = ((ByteBuffer) bb.rewind()).asLongBuffer();
        System.out.print("Long Buffer -- 八个字节 * 1 -- ");
        while (lb.hasRemaining())
            System.out.print(lb.position() + " -> " + lb.get() + ", ");
        System.out.println();

        ShortBuffer sb = ((ByteBuffer) bb.rewind()).asShortBuffer();
        System.out.print("Short Buffer -- 两个字节 * 4 -- ");
        while (sb.hasRemaining())
            System.out.print(sb.position() + " -> " + sb.get() + ", ");
        System.out.println();

        DoubleBuffer db = ((ByteBuffer) bb.rewind()).asDoubleBuffer();
        System.out.print("Double Buffer -- 八个字节 * 1 -- ");
        while (db.hasRemaining())
            System.out.print(db.position() + " -> " + db.get() + ", ");
    }

}
