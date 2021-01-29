package org.anonymous.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.InitializingBean;

import java.time.LocalDateTime;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/9/27 20:41
 */
//@Component("myJob") // 不使用 MethodInvokingJobDetailFactoryBean 不需要加入 IOC. 实现 quartz-Job 接口(为了实现方法传参)
@DisallowConcurrentExecution // 禁止 quartz 并发执行: 必须等待上一个任务执行完毕, 才能执行下一个任务
public class MyQuartzJob implements Job, InitializingBean {

    // 任务不开始, 不会创建对象
    // 每次任务都是新的对象
    public MyQuartzJob() {
        System.out.println(this + ": " + LocalDateTime.now());
    }

    // 两次执行间隔 5s(任务执行时间): 不会间隔
    @Override
    public void execute(JobExecutionContext ctx) {
        System.out.println("before" + Thread.currentThread() + LocalDateTime.now());
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JobDataMap map = ctx.getMergedJobDataMap();
        map.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println("after" + Thread.currentThread() + LocalDateTime.now());
    }

    // 非 spring-ioc 中的 bean 不会执行
    @Override
    public void afterPropertiesSet() throws Exception {
        System.err.println("start time: " + LocalDateTime.now());
    }
}
