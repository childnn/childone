package org.anonymous.command.test;

import org.anonymous.command.Light;
import org.anonymous.command.LightOnCommand;
import org.anonymous.command.SimpleRemoteControl;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/4 20:35
 * 请求调用者 --> 发送命令 --> 请求接收者.
 */
public class RemoteControlTest { // 命令模式的客户.
    public static void main(String[] args) {
        // 遥控器就是调用者, 会传入一个命令对象, 可以用来发出请求.
        SimpleRemoteControl remote = new SimpleRemoteControl();
        // 创建一个电灯对象, 此对象也就是请求的接收者.
        Light light = new Light();
        // 在这里创建一个命令, 然后将接收者传给它.
        LightOnCommand lightOn = new LightOnCommand(light);

        // 把命令传给调用者.
        remote.setCommand(lightOn);
        // 然后模拟按下按钮.
        remote.buttonWasPressed();
    }
}
