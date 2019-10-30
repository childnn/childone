package concurrencyinpractice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/17 17:50
 */
public class ListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    /**
     * 线程安全
     *
     * @param x
     * @return
     */
    public boolean putIfAbsent(E x) {
        synchronized (list) {
            if (!list.contains(x)) {
                list.add(x);
                return true;
            }
            return false;
        }
    }

    /**
     * 在方法层面加锁, 非线程安全. list 的其他操作并不是原子化的.
     * 不能保证 putIfAbsent 执行时, 另一个线程不会修改 list
     *
     * @param x
     * @return
     */
    @Deprecated
    public synchronized boolean putIfAbsent1(E x) {
        boolean absent = !list.contains(x);
        if (absent) {
            list.add(x);
        }
        return absent;
    }

}
