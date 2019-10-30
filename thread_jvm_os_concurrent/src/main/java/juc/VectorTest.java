package juc;

import java.util.Arrays;
import java.util.Vector;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/6/16 9:51
 */
public class VectorTest {
    private static Vector<Integer> vector = new Vector<>();

    // 在多线程环境下,如果 删除线程恰好在错误的时间里删除了一个元素,导致序号 i 已经不再可用
    // 再访问 i 就会抛出 AIOOBE.
    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }
            Thread removeThread = new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    vector.remove(i);
                }
            });

            Thread printThread = new Thread(() -> {
                for (int i = 0; i < vector.size(); i++) {
                    // java.lang.ArrayIndexOutOfBoundsException: Array index out of range:xxx
                    System.out.print(vector.get(i));
                }
            });

            removeThread.start();
            printThread.start();

            while (Thread.activeCount() > 20) ;

        }
    }
}

class VectorTest1 {
    public static void main(String[] args) {
        while (true) {
            Vector<Integer> vector = new Vector<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
            Thread removeT = new Thread(() -> {
                synchronized (vector) {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            });

            Thread printT = new Thread(() -> {
                synchronized (vector) {
                    for (int i = 0; i < vector.size(); i++) {
                        System.out.println(vector.get(i));
                    }
                }
            });

            while (Thread.activeCount() > 20) ;
        }
    }
}