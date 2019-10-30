package pojo;

import org.quartz.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/10 12:19
 */
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("Hello! Hello Job is executing." + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        JobDetail jobDetail = context.getJobDetail();

        JobKey key = jobDetail.getKey();

        JobDataMap dataMap = jobDetail.getJobDataMap();
        dataMap.forEach((k, v) -> System.err.println(k + ": " + v));
        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");

        System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
    }
}
