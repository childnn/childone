package org.anonymous.onjava8;

import java.util.List;
import java.util.Queue;
import java.util.SplittableRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/9 12:38
 */
public class PriorityBlockingQueueDemo {

    public static void main(String[] args) {
        PriorityBlockingQueue<Prioritized> queue = new PriorityBlockingQueue<>();
        CompletableFuture.runAsync(new Producer(queue));
        CompletableFuture.runAsync(new Producer(queue));
        CompletableFuture.runAsync(new Producer(queue));
        CompletableFuture.runAsync(new Consumer(queue)).join();
    }

}

class Prioritized implements Comparable<Prioritized> {
    private static final AtomicInteger counter = new AtomicInteger();
    private static final List<Prioritized> sequence = new CopyOnWriteArrayList<>();
    private final int id = counter.getAndIncrement();
    private final int priority;

    Prioritized(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public int compareTo(Prioritized arg) {
        return Integer.compare(arg.priority, priority);
    }

    @Override
    public String toString() {
        return String.format("[%d] Prioritized %d", priority, id);
    }

    public void displaySequence() {
        int count = 0;
        for (Prioritized pt : sequence) {
            System.out.printf("(%d:%d)", pt.id, pt.priority);
            if (++count % 5 == 0)
                System.out.println();
        }
    }

    public static class EndSentinel extends Prioritized {
        EndSentinel() {
            super(-1);
        }
    }
}

class Producer implements Runnable {
    private static final AtomicInteger seed = new AtomicInteger(47);
    private final SplittableRandom rand = new SplittableRandom(seed.getAndAdd(10));
    private final Queue<Prioritized> queue;

    Producer(Queue<Prioritized> q) {
        queue = q;
    }

    @Override
    public void run() {
        rand.ints(10, 0, 20)
                .mapToObj(Prioritized::new)
                .peek(p -> new Nap(rand.nextDouble() / 10))
                .forEach(p -> queue.add(p));
        queue.add(new Prioritized.EndSentinel());
    }
}

class Consumer implements Runnable {
    private final PriorityBlockingQueue<Prioritized> q;
    private final SplittableRandom rand =
            new SplittableRandom(47);

    Consumer(PriorityBlockingQueue<Prioritized> q) {
        this.q = q;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Prioritized pt = q.take();
                System.out.println(pt);
                if (pt instanceof Prioritized.EndSentinel) {
                    pt.displaySequence();
                    break;
                }
                new Nap(rand.nextDouble() / 10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}