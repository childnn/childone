package collection;

import java.lang.ref.*;
import java.util.LinkedList;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/26 14:20
 * **java.lang.ref** 中库包含一组类，这些类允许垃圾收集具有更大的灵活性。特别是当拥有可能导致内存耗尽的大对象时，
 * 这些类特别有用。这里有三个从抽象类 **Reference** 继承来的类： **SoftReference** （软引用）， **WeakReference** （弱引用）
 * 和 **PhantomReference** （虚引用）继承了三个类。如果一个对象只能通过这其中的一个 **Reference** 对象访问，
 * 那么这三种类型每个都为垃圾收集器提供不同级别的间接引用（indirection）。
 * <p>
 * 如果一个对象是 *可达的*（reachable），那么意味着在程序中的某个位置可以找到该对象。这可能意味着在栈上有一个
 * 直接引用该对象的普通引用，但也有可能是引用了一个对该对象有引用的对象，这可以有很多中间环节。如果某个对象是可达的，
 * 则垃圾收集器无法释放它，因为它仍然被程序所使用。如果某个对象是不可达的，则程序无法使用它，那么垃圾收集器回收该对象就是安全的。
 * <p>
 * 使用 **Reference** 对象继续保持对该对象的引用，以到达该对象，但也允许垃圾收集器释放该对象。因此，程序可以使用该对象，
 * 但如果内存即将耗尽，则允许释放该对象。
 * <p>
 * 可以通过使用 **Reference** 对象作为你和普通引用之间的中介（代理）来实现此目的。此外，必须没有对象的普通引用
 * （未包含在 **Reference** 对象中的对象）。如果垃圾收集器发现对象可通过普通引用访问，则它不会释放该对象。
 * <p>
 * 按照 **SoftReference** ， **WeakReference** 和 **PhantomReference** 的顺序，每个都比前一个更“弱”，
 * 并且对应于不同的可达性级别。软引用用于实现对内存敏感的缓存。弱引用用于实现“规范化映射”（ canonicalized mappings）
 * ——对象的实例可以在程序的多个位置同时使用，以节省存储，但不会阻止其键（或值）被回收。虚引用用于调度 pre-mortem 清理操作，
 * 这是一种比 Java 终结机制（Java finalization mechanism）更灵活的方式。
 * <p>
 * 使用 **SoftReference** 和 **WeakReference** ，可以选择是否将它们放在 **ReferenceQueue** （用于 pre-mortem 清理操作的设备）
 * 中，但 **PhantomReference** 只能在 **ReferenceQueue** 上构建。
 */
public class References {
    private static ReferenceQueue<VeryBig> rq =
            new ReferenceQueue<>();

    public static void checkQueue() {
        Reference<? extends VeryBig> inq = rq.poll();
        if (inq != null)
            System.out.println("In queue: " + inq.get());
    }

    public static void main(String[] args) {
        int size = 10;
        // Or, choose size via the command line:
        if (args.length > 0)
            size = Integer.parseInt(args[0]);
        LinkedList<SoftReference<VeryBig>> sa =
                new LinkedList<>();
        for (int i = 0; i < size; i++) {
            sa.add(new SoftReference<>(
                    new VeryBig("Soft " + i), rq));
            System.out.println(
                    "Just created: " + sa.getLast());
            checkQueue();
        }
        LinkedList<WeakReference<VeryBig>> wa =
                new LinkedList<>();
        for (int i = 0; i < size; i++) {
            wa.add(new WeakReference<>(
                    new VeryBig("Weak " + i), rq));
            System.out.println(
                    "Just created: " + wa.getLast());
            checkQueue();
        }
        SoftReference<VeryBig> s =
                new SoftReference<>(new VeryBig("Soft"));
        WeakReference<VeryBig> w =
                new WeakReference<>(new VeryBig("Weak"));
        System.gc();
        LinkedList<PhantomReference<VeryBig>> pa =
                new LinkedList<>();
        for (int i = 0; i < size; i++) {
            pa.add(new PhantomReference<>(
                    new VeryBig("Phantom " + i), rq));
            System.out.println(
                    "Just created: " + pa.getLast());
            checkQueue();
        }
    }
}

class VeryBig {
    private static final int SIZE = 10000;
    private long[] la = new long[SIZE];
    private String ident;

    VeryBig(String id) {
        ident = id;
    }

    @Override
    public String toString() {
        return ident;
    }

    @Override
    protected void finalize() {
        System.out.println("Finalizing " + ident);
    }
}