package org.anonymous.command;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/4 21:40
 */
public class RemoteControlWithUndo {
    /**
     * 这个时候, 遥控器要处理 7 个开与关的命令,
     * 使用相应数组记录这些命令.
     */
    private Command[] onCommands;
    private Command[] offCommands;

    /**
     * 前一个命令将被记录在这里.
     */
    private Command undoCommand;

    /**
     * 在构造器中, 只需实例化并初始化这两个开与关的数组.
     */
    public RemoteControlWithUndo() {
        onCommands = new Command[7];
        offCommands = new Command[7];

        Command noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
        /*
         * 一开始, 并没有所谓的 "前一个命令", 所以将它设置为
         * NoCommand 的对象.
         */
        undoCommand = noCommand;
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
        // 当按下按钮, 我们取得这个命令并优先执行它, 然后
        // 将它记录在 undoCommand 实例变量中. 不管是 "开" 或
        // "关" 命令, 我们的处理方法都是一样的.
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPushed(Integer slot) {
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }

    /**
     * 当按下撤销按钮, 我们调用 undoCommand 实例变量的 undo() 方法,
     * 就可以倒转前一个命令.
     */
    public void undoButtonWasPushed() {
        undoCommand.undo();
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
