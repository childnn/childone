package org.anonymous.command;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/4 21:51
 */
public class CeilingFanHighCommand implements Command {
    private CeilingFan ceilingFan;
    // 增加局部状态, 以便追踪吊扇之前的速度.
    private Integer prevSpeed;

    public CeilingFanHighCommand(CeilingFan ceilingFan) {
        this.ceilingFan = ceilingFan;
    }

    /**
     * 在 execute() 中, 在我们改变吊扇速度之前, 需要先将它之前的状态记录起来,
     * 以便需要撤销时使用.
     */
    @Override
    public void execute() {
        prevSpeed = ceilingFan.getSpeed();
        ceilingFan.high();
    }

    /**
     * 将吊扇的速度设置回之前的值, 达到撤销的目的.
     */
    @Override
    public void undo() {
        if (prevSpeed.equals(CeilingFan.HIGH)) {
            ceilingFan.high();
        } else if (prevSpeed.equals(CeilingFan.MEDIUM)) {
            ceilingFan.medium();
        } else if (prevSpeed.equals(CeilingFan.LOW)) {
            ceilingFan.low();
        } else if (prevSpeed.equals(CeilingFan.OFF)) {
            ceilingFan.off();
        } else {
            // do nothing.
        }
    }
}
