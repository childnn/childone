package org.anonymous.config;

import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/10 13:46
 * EX second ：设置键的过期时间为 second 秒。 SET key value EX second 效果等同于 SETEX key second value 。
 * PX millisecond ：设置键的过期时间为 millisecond 毫秒。 SET key value PX millisecond 效果等同于 PSETEX key millisecond value 。
 * NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
 * XX ：只在键已经存在时，才对键进行设置操作。
 */
public abstract class RedisLock implements Lock {
    public static final String OK = "OK";
    protected String value;
    protected Jedis jedis;
    protected String key;
    protected volatile boolean isOpenExpirationRenewal = true;

    public RedisLock(Jedis jedis, String key) {
        this(jedis, key, UUID.randomUUID().toString() + Thread.currentThread().getId());
    }

    public RedisLock(Jedis jedis, String key, String value) {
        this.jedis = jedis;
        this.key = key;
        this.value = value;
    }


    public void sleepBySecond(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启定时刷新
     */
    protected void scheduleExpirationRenewal() {
        Thread renewalThread = new Thread(new ExpirationRenewal());
        renewalThread.start();
    }

    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * 刷新key的过期时间
     */
    private class ExpirationRenewal implements Runnable {
        @Override
        public void run() {
            while (isOpenExpirationRenewal) {
                System.out.println("执行延迟失效时间中...");

                String checkAndExpireScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                        "return redis.call('expire',KEYS[1],ARGV[2]) " +
                        "else " +
                        "return 0 end";

                jedis.eval(checkAndExpireScript, 1, key, value, "30");

                //休眠10秒
                sleepBySecond(10);
            }
        }
    }
}
