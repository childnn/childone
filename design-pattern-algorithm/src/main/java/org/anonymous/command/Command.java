package org.anonymous.command;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/4 20:25
 * 命令接口.
 */
public interface Command {
    void execute();

    void undo();
}
