package test.swing.read.layout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/16 20:45
 * @see JPanel JPanel 的布局管理器的默认布局是 FlowLayout 布局.
 * 当把面板加到框架时, 面板的大小位置还是受 BorderLayout 布局的管理.
 * 但面板内部的组件(通过 panel.add(someComponent) 加入) 是由面板的 FlowLayout
 * 布局来管理的.
 *
 * @see BoxLayout
 */
public class Panel1 {

    public static void main(String[] args) {
        Panel1 gui = new Panel1();
        gui.goBox();
    }

    private void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        // 让面板变成深灰色以便观察.
        panel.setBackground(Color.DARK_GRAY);

        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    private void go1() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        // 让面板变成深灰色以便观察.
        panel.setBackground(Color.DARK_GRAY);

        JButton button = new JButton("shock me");
        // 把 按钮加到面板上.
        panel.add(button);
        frame.getContentPane().add(BorderLayout.EAST, panel);

        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    private void go2() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        // 让面板变成深灰色以便观察.
        panel.setBackground(Color.DARK_GRAY);

        JButton button = new JButton("shock me");
        JButton buttonTwo = new JButton("bliss");
        // JButton buttonThree = new JButton("huh?");
        // 把 按钮加到面板上.
        panel.add(button);
        panel.add(buttonTwo);
        // panel.add(buttonThree);

        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    public void goBox() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);

        // 把布局管理器换掉.
        // 其构造函数需要知道要管理哪个组件以及使用哪个轴: x 水平, y 垂直, line 中心
        panel.setLayout(new BoxLayout(panel, BoxLayout. Y_AXIS));

        JButton button = new JButton("shock me");
        JButton buttonTwo = new JButton("bliss");
        panel.add(button);
        panel.add(buttonTwo);
        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.setSize(250, 250);
        frame.setVisible(true);
    }


}
