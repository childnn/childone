package pojo;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/10 12:19
 */
@DisallowConcurrentExecution // 关闭 quartz 的默认并发执行: 等待上一个任务执行完毕才执行下一个 (如果任务执行时间大于设置的周期时间, 则下一个任务会在上一个任务结束后立即执行)
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
        System.err.println("Hello! Hello Job is executing." + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("ctx = " + ctx);

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JobDetail jobDetail = ctx.getJobDetail();

        JobKey key = jobDetail.getKey();

        JobDataMap dataMap = jobDetail.getJobDataMap();
        dataMap.forEach((k, v) -> System.err.println(k + ": " + v));
        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");

        System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
    }
}
