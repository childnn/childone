package org.anonymous.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/10 13:49
 */
public class LockCase1 extends RedisLock {
    public LockCase1(Jedis jedis, String key) {
        super(jedis, key);
    }

    @Override
    public void lock() {
        while (true) {
            String result = jedis.set(key, "value", SetParams.setParams().ex(4));
            if (OK.equals(result)) {
                System.out.println(Thread.currentThread().getName() + " 加锁成功！");
                break;
            }
        }
    }

    @Override
    public void unlock() {
        jedis.del(key);
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        LockCase1 lock = new LockCase1(jedis, "lockName");
        lock.lock();

    }
}
