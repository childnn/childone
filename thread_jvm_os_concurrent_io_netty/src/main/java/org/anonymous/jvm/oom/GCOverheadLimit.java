package org.anonymous.jvm.oom;

import java.util.HashMap;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/7/7 20:08
 * <p>
 * JVM 内置了垃圾回收机制GC，所以作为 Javaer 的我们不需要手工编写代码来进行内存分配和释放，
 * 但是当 Java 进程花费 98% 以上的时间执行 GC，但只恢复了不到 2% 的内存，且该动作连续重复了 5 次，
 * 就会抛出 java.lang.OutOfMemoryError:GC overhead limit exceeded 错误（俗称：垃圾回收上头）。
 * 简单地说，就是应用程序已经基本耗尽了所有可用内存， GC 也无法回收。
 * <p>
 * 假如不抛出 GC overhead limit exceeded 错误，那 GC 清理的那么一丢丢内存很快就会被再次填满，
 * 迫使 GC 再次执行，这样恶性循环，CPU 使用率 100%，而 GC 没什么效果。
 */
public class GCOverheadLimit {
    /**
     * 3.2 解决方案
     * 添加 JVM 参数-XX:-UseGCOverheadLimit 不推荐这么干，没有真正解决问题，只是将异常推迟
     * 检查项目中是否有大量的死循环或有使用大内存的代码，优化代码
     * dump内存分析，检查是否存在内存泄露，如果没有，加大内存
     * <p>
     * VM-args:  -Xmx14m -XX:+PrintGCDetails
     * 期望结果： java.lang.OutOfMemoryError: GC overhead limit exceeded
     */
    public static void main(String[] args) {
        HashMap<Key, String> map = new HashMap<>();
        while (true) {
            for (int i = 0; i < 1000; i++) {
                if (!map.containsKey(new Key(i))) {
                    map.put(new Key(i), "Number: " + i);
                }
            }
            System.out.println("map.size() = " + map.size());
        }
    }

    /**
     * 从输出结果可以看到，我们的限制 1000 条数据没有起作用，map 容量远超过了 1000，而且最后也出现了我们想要的错误，
     * 这是因为类 Key 只重写了 hashCode() 方法，却没有重写 equals() 方法，我们在使用 containsKey() 方法其实就出现了问题，
     * 于是就会一直往 HashMap 中添加 Key，直至 GC 都清理不掉。
     */
    static class Key {
        Integer id;

        public Key(Integer id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }
}