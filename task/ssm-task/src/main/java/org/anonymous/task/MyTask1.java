package org.anonymous.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/9/7 21:16
 */
@Component
public class MyTask1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyTask1.class);

    @Schedules({
            // @Scheduled(fixedRate = 5000), // 上次执行开始时间后 5 秒执行. -- 开始.
            // @Scheduled(fixedDelay = 5000), // 上次执行完毕后 5 秒执行. -- 结束.
            // @Scheduled(initialDelay = 3000, fixedRate = 5000), // 第一次延迟 3 秒, 以后每隔 5 秒执行一次.
            @Scheduled(cron = "0/3 * * * * *"), // 每隔 3 秒执行一次.
    })
    public void test1() {
        LOGGER.info("============== 测试定时任务 1 开始 ======================");

        System.err.println(LocalDateTime.now());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("================ 测试定时任务 1 结束 ===================");
    }

    @Scheduled(fixedRate = 3000)
    public void test2() {
        LOGGER.error("============ 测试定时任务 2 开始 ==============");

        System.err.println(LocalDateTime.now());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.error("=============== 定时任务 2 结束 ======================");
    }
}
