package org.anonymous.jvm.gc;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/7 18:48
 * 虚引用主要用于跟踪对象被垃圾回收的状态, 虚引用不能单独使用
 * 虚引用必须和引用队列 {@link java.lang.ref.ReferenceQueue} 联合使用
 */
public class PhantomReferenceTest {
    public static void main(String[] args) {
        String s = "Crazy";

        ReferenceQueue<String> rq = new ReferenceQueue<>();
        PhantomReference<String> pf = new PhantomReference<>(s, rq);

        s = null;

        // 无法通过虚引用获取被引用对象
        System.out.println(pf.get());

        // 可以注释此两行, 看结果
        // 当被引用的对象被回收后, 对应的虚引用将被添加到关联的引用队列中.
        System.gc();
        System.runFinalization();

        System.out.println(rq.poll() == pf);

    }
}
