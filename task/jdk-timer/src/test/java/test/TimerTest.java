package test;

import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/9 13:55
 * @see Timer
 * @see TimerTask
 */
public class TimerTest {
    @Test
    public void test() throws IOException {
        Timer timer = new Timer("my-timer");
        System.out.println("now: " + LocalDateTime.now());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "---1秒一次。。。" + LocalDateTime.now());
            }
        }, 2000L, 1000L); // delay: 第一次开始前延迟毫秒数, period: 每次间隔周期毫秒数 -- 不设置就只执行一次.

        System.in.read();
        // timer.cancel();
    }

    @Test
    public void test1() throws IOException {
        Timer timer = new Timer("my-timer-01");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "---1秒一次。。。" + LocalDateTime.now());
            }
        }, new Date(System.currentTimeMillis() + 1000L), 2000L); // period 不设值就只执行一次.


        System.in.read();
    }

}
