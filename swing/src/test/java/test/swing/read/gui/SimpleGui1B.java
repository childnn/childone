package test.swing.read.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/13 0:39
 * 实现 {@link ActionListener} 表示 当前类是个 ActionListener,
 * 事件只会通知有实现 ActionListener 的类.
 */
public class SimpleGui1B implements ActionListener {
    private JButton button;

    public static void main(String[] args) {
        SimpleGui1B gui = new SimpleGui1B();
        gui.go();
    }

    private void go() {
        JFrame frame = new JFrame("simple gui1B");
        button = new JButton("click me");

        button.addActionListener(this); // 向按钮注册.

        frame.getContentPane().add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    /**
     * 实现 {@link ActionListener} 的方法, 这是真正处理事件的方法!
     * @param e 按钮会以 ActionEvent 对象作为参数来调用此方法.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        button.setText("I've been clicked!");
    }
}
