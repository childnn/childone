package org.anonymous.thread.lock;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @see java.util.concurrent.locks.Condition 替换 Object.wait(), Object.notify()
 * @since 2024/9/18
 */
public class ConditionTest {
    /*
    使用ReentrantLock比直接使用synchronized更安全，可以替代synchronized进行线程同步。
        但是，synchronized可以配合wait和notify实现线程在条件不满足时等待，
        条件满足时唤醒，用ReentrantLock我们怎么编写wait和notify的功能呢？
        答案是使用Condition对象来实现wait和notify的功能。

     */

    static class TaskQueue {
        private final Lock lock = new ReentrantLock();
        // 可见，使用Condition时，引用的Condition对象必须从Lock实例的newCondition()返回，这样才能获得一个绑定了Lock实例的Condition实例
        private final Condition condition = lock.newCondition();
        private final Queue<String> queue = new LinkedList<>();

        /*
        Condition提供的await()、signal()、signalAll()原理和
          synchronized锁对象的wait()、notify()、notifyAll()是一致的，并且其行为也是一样的：
            await()会释放当前锁，进入等待状态；
            signal()会唤醒某个等待线程；
            signalAll()会唤醒所有等待线程；
            唤醒线程从await()返回后需要重新获得锁。

         */
        public void addTask(String s) {
            lock.lock();
            try {
                queue.add(s);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public String getTask() throws InterruptedException {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    condition.await();
                }
                return queue.remove();
            } finally {
                lock.unlock();
            }
        }
    }


}
