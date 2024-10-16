package org.anonymous.thread.lock;

import java.util.UUID;
import java.util.concurrent.Semaphore;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @see java.util.concurrent.Semaphore
 * Semaphore本质上就是一个信号计数器，用于限制同一时间的最大访问数量。
 * @since 2024/9/18
 */
public class SemaphoretTest {

    /*
    前面我们讲了各种锁的实现，本质上锁的目的是保护一种受限资源，保证同一时刻只有一个线程能访问（ReentrantLock），或者只有一个线程能写入（ReadWriteLock）。
        还有一种受限资源，它需要保证同一时刻最多有N个线程能访问，比如同一时刻最多创建100个数据库连接，最多允许10个用户下载等。
        这种限制数量的锁，如果用Lock数组来实现，就太麻烦了。

     */
    static class AccessLimitControl {
        // 任意时刻仅允许最多3个线程获取许可:
        final Semaphore semaphore = new Semaphore(3);

        public String access() throws Exception {
            // 如果超过了许可数量,其他线程将在此等待:
            semaphore.acquire();
            try {
                // TODO:
                return UUID.randomUUID().toString();
            } finally {
                semaphore.release();
            }

            // 使用Semaphore先调用acquire()获取，然后通过try ... finally保证在finally中释放。
            // 调用acquire()可能会进入等待，直到满足条件为止。也可以使用tryAcquire()指定等待时间：
            /*
            if (semaphore.tryAcquire(3, TimeUnit.SECONDS)) {
                // 指定等待时间3秒内获取到许可:
                try {
                    // TODO:
                } finally {
                    semaphore.release();
                }
            }

             */

        }
    }

}
