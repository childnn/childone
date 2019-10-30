package test.swing.read.gui;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/13 0:08
 */
public class SimpleGui1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("simple");
        JButton button = new JButton("click me");
        // 在 window 关闭时把程序结束掉.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 把 button 加到 frame 的 pane 上.
        frame.getContentPane().add(button);
        // 设定 frame 的大小.
        frame.setSize(300, 300);
        // 设置 frame 可见.
        frame.setVisible(true);
    }
}
