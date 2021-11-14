package org.anonymous.template.method.swing;

import java.applet.Applet;
import java.awt.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/8 14:48
 */
public class MyApplet extends Applet {
    private String message;

    /**
     * init 钩子用来进行 applet 的初始化动作, 它会在
     * applet 一开始的时候被调用一次.
     */
    @Override
    public void init() {
        message = "Hello World, I'm alive!";
        // repaint() 方法是 Applet 类的一个具体方法, 可让
        // applet 的上层组件知道这个 applet 需要重绘.
        repaint();
    }

    /**
     * 这个 start 钩子可以在 applet 正要被显示在网页上时,
     * 让 applet 做一些动作.
     */
    @Override
    public void start() {
        message = "Now I'm starting up...";
        repaint();
    }

    /**
     * 如果用户跳到别的网页, 这个 stop 钩子会
     * 被调用, 然后 applet 就可以在这里做一些事情来停止它的行动.
     */
    @Override
    public void stop() {
        message = "Oh, now I'm being stopped...";
        repaint();
    }

    /**
     * 当这个 applet 即将被销毁(例如, 关闭浏览器)时, destroy 钩子就会
     * 被调用. 我们可以在这里显示一些东西, 但这么做好像没什么意义?
     */
    @Override
    public void destroy() {
        // applet 正在被销毁.
    }

    /**
     * 与 JFrame 类似, Applet 也将这个方法当作钩子.
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        g.drawString(message, 5, 15);
    }
}
