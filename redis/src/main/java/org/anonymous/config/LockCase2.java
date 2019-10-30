package org.anonymous.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/10 15:14
 */
public class LockCase2 extends RedisLock {
    public LockCase2(Jedis jedis, String key) {
        super(jedis, key);
    }

    @Override
    public void lock() {
        while (true) {
            String result = jedis.set(key, value, SetParams.setParams().nx()); // set if the key not exists.
            if (OK.equals(result)) {
                System.out.println(Thread.currentThread().getName() + " 加锁成功！");

                //开启定时刷新过期时间
                isOpenExpirationRenewal = true;
                scheduleExpirationRenewal();
                break;
            }
            System.out.println(Thread.currentThread().getName() + "获取锁失败，休眠10秒!时间:"+ LocalDateTime.now());
            //休眠10秒
            sleepBySecond(10);
        }
    }

    @Override
    public void unlock() {
        String value = jedis.get(key);
        if (Objects.equals(this.value, value)) {
            jedis.del(key);
        }
    }
}
