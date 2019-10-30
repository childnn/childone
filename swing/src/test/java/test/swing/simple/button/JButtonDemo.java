package test.swing.simple.button;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 15:33
 */
public class JButtonDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("Swing-button"); // 窗口
        jf.setSize(400, 200);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel(); // 面板
        JButton bt1 = new JButton("普通按钮");
        JButton bt2 = new JButton("带背景颜色按钮");
        JButton bt3 = new JButton("不可用按钮");
        JButton bt4 = new JButton("底部对齐按钮");

        jp.add(bt1);

        bt2.setBackground(Color.GREEN);
        jp.add(bt2);

        bt3.setEnabled(false);
        jp.add(bt3);

        bt4.setPreferredSize(new Dimension(160, 60));
        bt4.setVerticalAlignment(SwingConstants.BOTTOM); // 按钮垂直对齐方式
        jp.add(bt4);

        jf.add(jp);

        jf.setVisible(true);
    }

}
