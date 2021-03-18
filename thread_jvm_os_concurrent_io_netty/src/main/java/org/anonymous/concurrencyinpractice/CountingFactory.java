package org.anonymous.concurrencyinpractice;

import org.anonymous.concurrencyinpractice.annotation.ThreadSafe;

import java.util.concurrent.atomic.AtomicLong;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/3 18:31
 */
@ThreadSafe
public class CountingFactory /* implements Servlet*/ {

    // AtomicLong 可以确保所有访问计数器状态的操作都是原子的
    // 计数器的状态就是 Servlet 的状态, 因此该 Servlet 线程安全.
    // 这是这个无状态类中的唯一状态元素, 而这个状态完全被线程安全的对象所管理
    // 但状态的数量从一个增加到多个的时候, 远不像从 0 增加到 1 这么简单.
    private final AtomicLong count = new AtomicLong(0);

    public long getCount() { return count.get(); }

    public void service(/*req, resp*/) {
        // ...
        count.incrementAndGet(); // 返回自增之后的值, 按需接收.
        // ...
    }

}
