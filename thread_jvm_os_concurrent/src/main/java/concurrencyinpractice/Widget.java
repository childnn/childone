package concurrencyinpractice;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/12 10:49
 * synchronized 可重入性
 * 如果内部锁不是可重入的，super.doSomething 的调用者就永远无法得到 Widget 的锁,
 * 因为锁已经被占用,导致线程会永久的延迟,等待着一个永远无法获得的锁.
 * 重入 避免了这种死锁.
 */
public class Widget {
    public synchronized void doSomething() {

    }
}

class LoggingWidget extends Widget {
    @Override
    public synchronized void doSomething() {
        System.out.println(toString() + ": calling doSomething");
        super.doSomething();
    }
}