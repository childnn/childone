package test;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.utils.Key;
import pojo.MyJob;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see Scheduler -- 与调度程序交互的主要 API.
 * @see Job -- 由希望由调度程序执行的组件实现的接口.
 * @see JobDetail -- 用于定义作业的实例.
 * @see JobBuilder -- 用于定义/构建 JobDetail 实例, 用于定义作业的实例.
 * @see Trigger -- 触发器. 定义执行给定作业的计划的组件.
 * @see TriggerBuilder -- 用于定义/构建触发器实例.
 *
 * {@link Scheduler} 的声明周期, 从 {@link SchedulerFactory} 创建它开始, 到 {@link Scheduler}
 * 调用 {@link Scheduler#shutdown()} 方法结束; Scheduler 被创建后, 可以增加、删除和列举 Job 和 Trigger,
 * 以及执行其他与调度相关的操作(如暂停 Trigger). 但是, Scheduler 只有在调用 {@link Scheduler#start()} 方法后,
 * 才会真正出发 trigger(执行 Job).
 * @since 2019/11/9 19:19
 */
public class QuartzTest {
    @SuppressWarnings({"deprecation", "serial"})
    @Test
    public void test() {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

            JobDataMap dataMap = new JobDataMap() {{
                put("jobSays", "say what");
                put("myFloatValue", 2.333F);
            }};


            JobKey jobKey = JobKey.jobKey("myJob", "group1");

            JobDetail job = JobBuilder.newJob(MyJob.class)
                    .withIdentity(jobKey)
                    .setJobData(dataMap)
                    .build();

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(TriggerKey.triggerKey("myTrigger", "group1"))
                    // .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 8-17 * * ?")) // 每天上午 8 点至下午 5 点, 周期一分钟.
                    // .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(10, 42)) // 每天上午 10:42 执行.
                    // .withSchedule(CronScheduleBuilder.cronSchedule("0 42 10 * * ?")) // 每天上午 10:42 执行.
                    // .withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(DateBuilder.WEDNESDAY, 10, 42)
                    //         .inTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()))) // 每周三上午 10:42. 指定时区.
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 42 10 ? * WED")
                            .inTimeZone(TimeZone.getTimeZone("Asia/Shanghai"))) // 每周三上午 10:42. 指定时区.
                    .forJob(jobKey)
                    .build();


            /*SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    // .withSchedule(scheduler)
                    .withIdentity("myTrigger", "group1")
                    .forJob(jobKey)
                    // .startNow()
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.MINUTE))
                    .withSchedule(SimpleScheduleBuilder
                            .simpleSchedule()
                            .withIntervalInSeconds(1) // 周期 1s.
                            *//*.withRepeatCount(10)*//* // 10 次.
                            .repeatForever()) //
                    .endAt(DateBuilder.dateOf(22, 0, 0))
                    .build();*/

            Date date = scheduler.scheduleJob(job, trigger);

            System.err.println("date = " + date.toLocaleString());

            System.in.read();

            // scheduler.shutdown();
        } catch (SchedulerException | IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test1() {
        String uniqueName = Key.createUniqueName(null);
        System.out.println("uniqueName = " + uniqueName);
    }


}
