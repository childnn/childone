package multithread;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 * <p>
 * ThreadLocalMap 中使用的 key 为 ThreadLocal 的弱引用,而 value 是强引用。
 * 所以，如果 ThreadLocal 没有被外部强引用的情况下，在垃圾回收的时候，key 会被清理掉，
 * 而 value 不会被清理掉。这样一来，ThreadLocalMap 中就会出现key为null的Entry。
 * 假如我们不做任何措施的话，value 永远无法被GC 回收，这个时候就可能会产生内存泄露。
 * ThreadLocalMap实现中已经考虑了这种情况，在调用 set()、get()、remove() 方法的时候，
 * 会清理掉 key 为 null 的记录。使用完 ThreadLocal方法后 最好手动调用remove()方法
 *
 * @author MiaoOne
 * @see Runnable#run()
 * @see Callable#call()
 * run --> call
 * @see Executors#callable(java.lang.Runnable)
 * @see Executors#callable(java.lang.Runnable, java.lang.Object)
 * @see Executor#execute(java.lang.Runnable)
 * @see ExecutorService#submit(java.util.concurrent.Callable)
 * @see ExecutorService#submit(java.lang.Runnable, java.lang.Object)
 * @see ExecutorService#submit(java.lang.Runnable)
 * execute()方法和submit()方法的区别是什么呢？
 * execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功与否；
 * submit()方法用于提交需要返回值的任务。
 * 线程池会返回一个 Future 类型的对象，通过这个 Future 对象可以判断任务是否执行成功，
 * 并且可以通过 Future 的 get()方法来获取返回值，get()方法会阻塞当前线程直到任务完成，
 * 而使用 get（long timeout，TimeUnit unit）方法则会阻塞当前线程一段时间后立即返回，
 * 这时候有可能任务没有执行完。
 * --------------------------------------------------------------
 * @see Executors#newSingleThreadExecutor()
 * 方法返回一个只有一个线程的线程池。若多余一个任务被提交到该线程池，
 * 任务会被保存在一个任务队列中，待线程空闲，按先入先出的顺序执行队列中的任务。
 * @see Executors#newFixedThreadPool(int)
 * 该方法返回一个固定线程数量的线程池。该线程池中的线程数量始终不变。
 * 当有一个新的任务提交时，线程池中若有空闲线程，则立即执行。
 * 若没有，则新的任务会被暂存在一个任务队列中，待有线程空闲时，便处理在任务队列中的任务。
 * @see Executors#newCachedThreadPool()
 * 该方法返回一个可根据实际情况调整线程数量的线程池。线程池的线程数量不确定，
 * 但若有空闲线程可以复用，则会优先使用可复用的线程。
 * 若所有线程均在工作，又有新的任务提交，则会创建新的线程处理任务。
 * 所有线程在当前任务执行完毕后，将返回线程池进行复用。
 * @see Executors#newScheduledThreadPool(int)
 * Executors 返回线程池对象的弊端如下：
 * -- FixedThreadPool 和 SingleThreadExecutor ：允许请求的 队列长度 为 Integer.MAX_VALUE ，可能堆积大量的请求，从而导致OOM。
 * -- CachedThreadPool 和 ScheduledThreadPool ：允许创建的 线程数量 为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致OOM。
 * @see ThreadPoolExecutor
 * @since 2020/1/6 17:47
 * --------------------------------------------------
 */
public class ThreadLocalTest {
    // JDK 8.
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT_THREAD_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));
}
