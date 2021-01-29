package org.anonymous.test;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/9/27 20:24
 */
public class SpringQuartzTaskXML implements Trigger {

    private static final long serialVersionUID = 3444816961389142614L;

    public void execute() {

    }

    @Override
    public TriggerKey getKey() {
        return null;
    }

    @Override
    public JobKey getJobKey() {
        System.err.println("not key..................");
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getCalendarName() {
        return null;
    }

    @Override
    public JobDataMap getJobDataMap() {
        return null;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean mayFireAgain() {
        return false;
    }

    @Override
    public Date getStartTime() {
        return null;
    }

    @Override
    public Date getEndTime() {
        return null;
    }

    @Override
    public Date getNextFireTime() {
        return null;
    }

    @Override
    public Date getPreviousFireTime() {
        return null;
    }

    @Override
    public Date getFireTimeAfter(Date afterTime) {
        return null;
    }

    @Override
    public Date getFinalFireTime() {
        return null;
    }

    @Override
    public int getMisfireInstruction() {
        return 0;
    }

    @Override
    public TriggerBuilder<? extends Trigger> getTriggerBuilder() {
        return null; // NPE
    }

    @Override
    public ScheduleBuilder<? extends Trigger> getScheduleBuilder() {
        return null;
    }

    @Override
    public int compareTo(Trigger other) {
        return 0;
    }
}
