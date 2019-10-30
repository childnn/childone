package jvm.gc;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/5/21 17:40
 * 即使在可达性分析算法中不可达的对象, 也并非是 "非死亡不可" 的, 这时候它们暂时处于 "缓刑" 阶段,
 * 要真正宣告一个对象死亡, 至少要经历两次标记过程: 如果对象在进行可达性分析后发现没有与 GC Roots 相连接
 * 的引用链, 那它将会被第一次标记并且进行一次筛选, 筛选的条件是此对象是否有必要执行 finalize() 方法.
 * 当对象没有覆盖 finalize() 方法, 或者 finalize() 方法已经被虚拟机调用过, 虚拟机将这两种情况都视为
 * "没有必要执行".
 * 如果这个对象被判定为有必要执行 finalize() 方法, 那么这个对象将会放置在一个叫做 F-Queue 的队列之中,
 * 并在稍后一个由虚拟机自动建立的、低优先级的 Finalizer 线程去执行它. 这里所谓的 "执行" 是指虚拟机会
 * 处罚这个方法, 但并不承诺会等待它运行结束, 这样做的原因是, 如果一个对象在 finalize() 方法中执行缓慢,
 * 或者发生了死循环(更极端的情况), 将很可能会导致 F-Queue 队列中其他对象永久处于等待, 甚至导致整个内存回收
 * 系统崩溃. finalize() 方法是对象逃脱死亡明运的最后一次机会, 稍后 GC 将对 F-Queue 中的对象进行第二次小规模
 * 的标记, 如果对象要在 finalize() 中拯救自己 -- 只要重新与引用链上的任何一个对象建立关联即可, 譬如把自己
 * (this) 赋值给某个类变量或者对象的成员变量, 那在第二次标记时它将被移除出 "即将回收" 的集合; 如果对象这时候
 * 还没有逃脱, 那基本上它就真的被回收了.
 */
public class FinalizeEscapeGC {
    static FinalizeEscapeGC SAVE_HOOK = null;

    /**
     * JDK 1.8 运行结果:
     * finalize method executed!
     * yes, i am still alive :)
     * no, i am dead :(
     * SAVE_HOOK 对象的 finalize() 方法确实被 GC 收集器触发过, 并且在被收集前成功逃脱了.
     * 另外一个值得注意的地方是, 代码中有两段完全一样的代码片段, 执行结果却是一次逃脱成功,
     * 一次失败, 这是因为任何一个对象的 finalize() 方法都只会被系统自动调用一次, 如果对象
     * 面临下一次回收, 它的 finalize() 方法不会被再次执行, 因此第二段代码的自救行动失败了.
     */
    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();

        // 对象第一次成功拯救自己
        SAVE_HOOK = null;
        System.gc();

        // 因为 finalize 方法优先级很低, 所以暂停 0.5s 以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }

        // 下面这段代码与上面的完全相同, 但是这次自救却失败了
        SAVE_HOOK = null;
        System.gc();

        // 因为 finalize 方法优先级很低, 所以暂停 0.5s 以等待它
        Thread.sleep(500);
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no, i am dead :(");
        }
    }

    void isAlive() {
        System.out.println("yes, i am still alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

}
