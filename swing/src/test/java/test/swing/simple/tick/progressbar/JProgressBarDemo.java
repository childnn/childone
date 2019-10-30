package test.swing.simple.tick.progressbar;

import javax.swing.*;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/9 11:15
 * 进度条
 */
public class JProgressBarDemo extends JFrame {
    public JProgressBarDemo(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 500, 500);

        JLabel jl = new JLabel("正在升级");

        JProgressBar jpb = new JProgressBar(0, 100);
        //jpb.setBorderPainted(false); // 显示进度条边框, 默认 true
        jpb.setOrientation(SwingConstants.VERTICAL); // 进度条方向

        JButton jb = new JButton("完成");
        jb.setEnabled(false); // 禁用按钮

        JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel jp2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel jp3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jp1.add(jl);
        jp2.add(jpb);
        jp3.add(jb);

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(3, 1));
        contentPane.add(jp1); // 标签
        contentPane.add(jp2); // 进度条
        contentPane.add(jp3); // 按钮

        jpb.setStringPainted(true); // 进度条是否显示字符串
        //jpb.setString("loading"); // 进度条显示的字符串: 默认显示进度百分比
        // 不确定模式
        //jpb.setIndeterminate(true);

        // 开启一个线程处理进度
        new NewThread(jpb, jb).start();

        // 按钮监听
        jb.addActionListener(e -> {
            dispose();
            System.exit(0);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new JProgressBarDemo("download");
    }

    private class NewThread extends Thread {

        private final JProgressBar progressBar;
        private final JButton bt;
        private final int[] progressValues = {6, 18, 27, 39, 51, 66, 81, 100};

        public NewThread(JProgressBar jpb, JButton jb) {
            this.progressBar = jpb;
            this.bt = jb;
        }

        @Override
        public void run() {
            System.err.println(Thread.currentThread().getId());
            for (int value : progressValues) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 设置进度条的值
                progressBar.setValue(value);
            }
            progressBar.setIndeterminate(false); // 非模糊状态
            progressBar.setStringPainted(true);
            progressBar.setString("finish");

            bt.setEnabled(true);
        }
    }

}
