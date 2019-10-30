package org.anonymous.command;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/4 20:59
 */
public class RemoteControl {
    /**
     * 这个时候, 遥控器要处理 7 个开与关的命令,
     * 使用相应数组记录这些命令.
     */
    private Command[] onCommands;
    private Command[] offCommands;

    /**
     * 在构造器中, 只需实例化并初始化这两个开与关的数组.
     */
    public RemoteControl() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    /**
     * setCommand() 方法须有 3 个参数, 分别是插槽的位置, 开的命令, 关的命令.
     * 这些命令将记录在开关数组中对应的插槽位置, 以供稍后使用.
     * @param slot
     * @param onCommand
     * @param offCommand
     */
    public void setCommand(Integer slot, Command onCommand, Command offCommand) {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    /**
     * 当按下开或关的按钮, 硬件就会负责调用对应的方法, 也就是
     * @see #onButtonWasPushed(Integer) or
     * @see #offButtonWasPushed(Integer)
     * @param slot
     */
    public void onButtonWasPushed(Integer slot) {
        onCommands[slot].execute();
    }

    public void offButtonWasPushed(Integer slot) {
        offCommands[slot].execute();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n----- Remote Control ----\n");
        for (int i = 0; i < onCommands.length; i++) {
            sb.append("[slot ").append(i).append("] ")
                    .append(onCommands[i].getClass().getName()).append("  ")
                    .append(offCommands[i].getClass().getName()).append("\n");
        }

        return sb.toString();
    }
}
