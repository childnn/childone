package onjava8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/10 20:08
 * 死锁的四个条件
 * 1. 互斥。任务使用的至少一种资源必须不可共享。在这里，筷子一次只能由一位哲学家使用。
 * 2. 至少一个任务必须拥有资源，并等待获取当前由另一任务拥有的资源。也就是说，要使僵局发生，哲学家必须握住一根筷子，等待另一根筷子。
 * 3. 不能抢先从任务中夺走资源。任务仅作为正常事件释放资源。我们的哲学家很有礼貌，他们不会抓住其他哲学家的筷子。
 * 4. 可能发生循环等待，即一个任务等待另一个任务持有的资源，而该任务又等待另一个任务持有的资源，
 * 依此类推，直到一个任务正在等待另一个任务持有的资源。第一项任务，从而使一切陷入僵局。
 * 在**DiningPhilosophers.java**中，发生循环等待是因为每个哲学家都先尝试获取右筷子，然后再获取左筷子。
 * 必须满足所有这些条件才能导致死锁
 * 防止死锁的一种简单方法是打破第四个条件
 */
public class StickHolder {
    private Chopstick stick = new Chopstick();
    private BlockingQueue<Chopstick> holder = new ArrayBlockingQueue<>(1);

    public StickHolder() {
        putDown();
    }

    public void pickUp() {
        try {
            holder.take(); // Blocks if unavailable
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void putDown() {
        try {
            holder.put(stick);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Chopstick {
    }
}

class Philosopher implements Runnable {
    private final int seat;
    private final StickHolder left, right;

    public Philosopher(int seat, StickHolder left, StickHolder right) {
        this.seat = seat;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "P" + seat;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Thinking");
            //right.pickUp();
            left.pickUp();
            System.out.println(this + " eating");
            right.putDown();
            left.putDown();
        }
    }
}

class DiningPhilosophers {
    private StickHolder[] sticks;
    private Philosopher[] philosophers;

    public DiningPhilosophers(int n) {
        sticks = new StickHolder[n];
        Arrays.setAll(sticks, i -> new StickHolder());
        philosophers = new Philosopher[n];
        Arrays.setAll(philosophers, i -> new Philosopher(i, sticks[i], sticks[(i + 1) % n])); // [1]
        // Fix by reversing stick order for this one:
        // philosophers[1] = // [2]
        // new Philosopher(0, sticks[0], sticks[1]);
        Arrays.stream(philosophers).forEach(CompletableFuture::runAsync); // [3]
    }

    public static void main(String[] args) {
        // Returns right away:
        new DiningPhilosophers(5); // [4]
        // Keeps main() from exiting:
        new Nap(3, "Shutdown");
    }
}

class Allocator {
    private List<Object> als = new ArrayList<>();

    // 一次性申请所有资源
    synchronized boolean apply(Object from, Object to) {
        if (als.contains(from) || als.contains(to)) {
            return false;
        } else {
            als.add(from);
            als.add(to);
        }
        return true;
    }

    // 归还资源
    synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
    }
}

// wait()、notify()、notifyAll() 这三个方法能够被调用的前提是已经获取了相应的互斥锁，
// 所以我们会发现 wait()、notify()、notifyAll() 都是在 synchronized{}内部被调用的。
// 如果在 synchronized{}外部调用，或者锁定的 this，而用 target.wait() 调用的话，
// JVM 会抛出一个运行时异常：java.lang.IllegalMonitorStateException。
class Allocator1 {
    private List<Object> als;

    // 一次性申请所有资源
    synchronized void apply(Object from, Object to) {
        // 经典写法
        while (als.contains(from) || als.contains(to)) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        als.add(from);
        als.add(to);
    }

    // 归还资源
    synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
        notifyAll();
    }
}

class Account {
    // actr 应该为单例
    private Allocator actr;
    private int balance;

    // 转账
    void transfer(Account target, int amt) {
        // 一次性申请转出账户和转入账户，直到成功
        while (!actr.apply(this, target)) ;
        try {
            // 锁定转出账户
            synchronized (this) {
                // 锁定转入账户
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            actr.free(this, target);
        }
    }
}