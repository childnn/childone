package org.anonymous.config;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/08/20
 */
public class RedisTemplateLockDemo {

    static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {
        System.out.println(tableSizeFor(16));
    }

    static final int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    // redisson
    public void redisson() {
        RedissonClient rc = Redisson.create();
        RLock lock = rc.getLock("lock-key");

        try {
            lock.lock(1000, TimeUnit.SECONDS);
            // 其他业务逻辑

        } finally {
            lock.unlock();
        }

    }

    // 分布式锁
    public void distributionLock() {
        String key = "lock-key";
        String clientId = UUID.randomUUID().toString();
        RedisTemplate<String, Object> rt = new RedisTemplate<>();

        // setnx: 如果不存在则设置, 存在则返回 false
        // key: 分布式锁的业务ID
        // value: 当前线程的唯一标识
        // 为什么要特殊设置 value？
        // 如果不设置, 在释放锁的时候可能出现 a 线程释放了 b 线程锁的情形
        // 因为不同线程的实际执行时间不一致
        // 比如 a 线程执行需要 15s, 在第十秒时, key 已经被过期
        // 期间 b 获取到锁, a 执行完毕后, 释放锁实际上是 b 的锁
        // 那么, 如何解决过期时间的问题-如何确定一个 key 的过期时间
        // 方式一: 在获取到锁后, 开启一个子线程, 定时刷新过期时间
        // 实际上, 该定时刷新过程存在很多潜在问题待解决
        try {
            Boolean lock = rt.opsForValue().setIfAbsent(key, clientId, 10, TimeUnit.SECONDS);
            if (lock == null || !lock) {
                return;
            }

            // 每 3s 执行一次, 将过期时间重置为 10s
            new Timer("刷新过期时间", true)
                    .schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // TODO: 潜在问题...
                            // 当前线程只能释放当前线程设置的锁
                            if (clientId.equals(rt.opsForValue().get(key))) {
                                rt.opsForValue().set(key, clientId, 10, TimeUnit.SECONDS);
                            }

                        }
                    }, 0, 3000);

            // 其他业务逻辑


        } finally {

            // 当前线程只能释放当前线程设置的锁
            if (clientId.equals(rt.opsForValue().get(key))) {
                rt.delete(key);
            }
        }

    }

}
