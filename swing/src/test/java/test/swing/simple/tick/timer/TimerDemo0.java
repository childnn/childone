package test.swing.simple.tick.timer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/9 14:52
 */
public class TimerDemo0 implements ActionListener, ChangeListener {

    private JFrame jf;
    private JProgressBar jpb;
    private JLabel jl;
    private Timer timer;
    private JButton jb;

    public TimerDemo0(String title) {
        jf = new JFrame(title);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //jf.setBounds(800, 500, 500, 500);
        jf.setLocation(800, 500);

        Container contentPane = jf.getContentPane();
        //contentPane.setBounds(300, 300, 500, 500);

        jl = new JLabel(" ", SwingConstants.CENTER); // 显示进度条信息的文本标签
        jpb = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100); // 进度条
        jpb.setStringPainted(true); // 显示进度信息
        //jpb.setString("这是啥"); // 设置进度条上显示的信息: 默认是进度百分数
        jpb.setString(null);
        jpb.setBorderPainted(true); // 默认 true, 进度条边框
        // 进度条改变时监听
        jpb.addChangeListener(this);
        jpb.setPreferredSize(new Dimension(300, 30));
        jpb.setBackground(Color.GREEN);

        // 启动按钮
        jb = new JButton("install");
        jb.setBackground(Color.PINK);
        //jb.setSize(50, 50);
        jb.addActionListener(this); // 监听按钮
        // 将按钮添加到面板
        JPanel jp = new JPanel();
        jp.add(jb);

        contentPane.add(jp, BorderLayout.NORTH);
        contentPane.add(jpb, BorderLayout.CENTER);
        contentPane.add(jl, BorderLayout.SOUTH);

        jf.pack(); // preferred size
        jf.setVisible(true);

        timer = new Timer(1000, this); // 计时器
    }

    public static void main(String[] args) {
        new TimerDemo0("Download");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        // 点击按钮, 启动计时器
        if (Objects.equals(source, jb)) {
            timer.start();
        }
        // 计时器启动后的操作
        if (Objects.equals(source, timer)) {
            int value = jpb.getValue();
            if (value < 100) {
                jpb.setValue(++value);
            } else {
                timer.stop();
                jf.dispose();
            }
        }
    }

    // 监听进度条改变
    @Override
    public void stateChanged(ChangeEvent e) {
        int value = jpb.getValue();
        Object source = e.getSource();
        if (source == jpb) {
            jl.setText(String.format("已完成进度: %s%%", value));
            jl.setForeground(Color.RED);
        }
    }

}
