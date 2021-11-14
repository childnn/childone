package org.anonymous.command;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/4 20:26
 */
public class LightOnCommand implements Command {
    private Light light;

    /**
     * 这个 execute() 方法调用接收对象(我们正在控制的电灯)
     * 的 on() 方法.
     */
    @Override
    public void execute() {
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }

    /**
     * 构造方法传入某个电灯(比如客厅的电灯), 以便让这个命令控制,
     * 然后记录在实例变量中. 一旦调用 execute(), 就由这个电灯对象
     * 成为接收者, 负责接收请求.
     * @param light
     */
    public LightOnCommand(Light light) {
        this.light = light;
    }

}
