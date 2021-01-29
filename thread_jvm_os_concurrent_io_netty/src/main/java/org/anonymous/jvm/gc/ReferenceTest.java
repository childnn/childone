package org.anonymous.jvm.gc;

import java.lang.ref.WeakReference;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/7 18:42
 */
public class ReferenceTest {
    public static void main(String[] args) {
        // 注意以下两种声明方式的区别: 如果使用字面量, 系统会使用常量池来管理这个字符串直接量(使用强引用来引用它)
        // new 对象方式在堆中
        String str = "crazy";
        // String str = "crazy";

        WeakReference<String> wr = new WeakReference<>(str);

        str = null; // 切断堆中 crazy 对象的引用, 使其称为不可达对象
        System.out.println(wr.get()); // 取出弱引用所引用的对象

        // 强制 GC
        System.gc();
        System.runFinalization();

        // 再次取出弱引用所引用的对象
        System.out.println(wr.get());

    }
}
