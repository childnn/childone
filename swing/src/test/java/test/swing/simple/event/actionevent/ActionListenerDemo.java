package test.swing.simple.event.actionevent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.util.Random;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 20:12
 * 动作事件监听器: 如单击事件
 */
public class ActionListenerDemo extends JFrame {
    int clicks = 0;
    private JLabel label;
    private JButton bt;

    public ActionListenerDemo(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 500, 500);
        JPanel contentPane = new JPanel(new BorderLayout());// 内容面板
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        label = new JLabel("label");
        label.setFont(new Font("楷体", Font.BOLD, 23));
        contentPane.add(label, BorderLayout.SOUTH);

        bt = new JButton("普通按钮");
        bt.setFont(new Font("黑体", Font.BOLD, 23));
        // 监听
        bt.addActionListener(e -> {
            JButton source = ((JButton) e.getSource());
            //System.err.println(source);
            Random random = new Random();
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);

            source.setBackground(new Color(r, g, b));
            label.setText(String.format("按钮被单击了 %d 次", ++clicks));
        });
        contentPane.add(bt);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ActionListenerDemo("事件");
    }
}
