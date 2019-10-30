package onjava8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 11:11
 */
public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        final AttemptLocking al = new AttemptLocking();
        al.untimed(); // True -- lock is available
        al.timed();   // True -- lock is available
        // Now create a second task to grab the lock:
        CompletableFuture.runAsync(() -> {
            al.lock.lock();
            System.out.println("acquired");
        });
        new Nap(0.1);  // Give the second task a chance
        al.untimed(); // False -- lock grabbed by task
        al.timed();   // False -- lock grabbed by task
    }

    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println("tryLock(): " + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }

    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(
                    "tryLock(2, TimeUnit.SECONDS): " + captured);
        } finally {
            if (captured)
                lock.unlock();
        }
    }

}
