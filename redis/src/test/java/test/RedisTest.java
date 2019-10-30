package test;

import org.anonymous.config.LockCase2;
import org.anonymous.config.UniCodeConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/10 15:30
 */
public class RedisTest {
    @Test
    public void test() {
        //定义线程池
        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, 10,
                1, TimeUnit.SECONDS,
                new SynchronousQueue<>());

        //添加10个线程获取锁
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                try {
                    Jedis jedis = new Jedis("localhost");
                    LockCase2 lock = new LockCase2(jedis, "lockName");
                    lock.lock();

                    //模拟业务执行15秒
                    lock.sleepBySecond(15);

                    lock.unlock();
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        }

        //当线程池中的线程数为0时，退出
        while (pool.getPoolSize() != 0) {}
    }

    @Test
    public void test1() {
        String s = UniCodeConfig.UnicodeToCN("\\u9ED8\\u8BA4\\u53EA\\u5F00\\u653E");
        System.out.println("s = " + s);
        String u = UniCodeConfig.CNToUnicode("开放节点");
        System.out.println("u = " + u);
        System.out.println(new String("\\u5F00\\u653E\\u8282\\u70B9".toCharArray()));

    }
}
