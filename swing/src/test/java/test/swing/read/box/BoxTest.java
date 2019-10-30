package test.swing.read.box;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/22 11:59
 * 无法使用 JUNIT 测试.
 */
public class BoxTest {

    public static void main(String[] args) {
        JFrame frame = new JFrame("test1");
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        JButton button = new JButton("tesuji");
        JButton buttonTwo = new JButton("watari");
        panel.add(buttonTwo);
        frame.getContentPane().add(BorderLayout.NORTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER, button);

        // 参数一: x 轴离屏幕边缘的距离;
        // 参数二: y 轴离屏幕边缘的距离;
        // 参数三: 宽度.
        // 参数四: 高度.
        frame.setBounds(50, 250, 500, 300);
        // frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class BoxTest1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test2");
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        JButton button = new JButton("tesuji");
        JButton buttonTwo = new JButton("watari");
        panel.add(buttonTwo);
        frame.getContentPane().add(BorderLayout.CENTER, button);
        frame.getContentPane().add(BorderLayout.EAST, panel);

        frame.setBounds(50, 250, 500, 300);
        // frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class BoxTest2 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test3");
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        JButton button = new JButton("tesuji");
        JButton buttonTwo = new JButton("watari");
        panel.add(buttonTwo);
        // frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.EAST, button);

        frame.setBounds(50, 250, 500, 300);
        // frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class BoxTest3 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test4");
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        JButton button = new JButton("tesuji");
        JButton buttonTwo = new JButton("watari");
        panel.add(button);
        frame.getContentPane().add(BorderLayout.NORTH, buttonTwo);
        frame.getContentPane().add(BorderLayout.EAST, panel);

        frame.setBounds(50, 250, 500, 300);
        // frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class BoxTest4 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test5");
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        JButton button = new JButton("tesuji");
        JButton buttonTwo = new JButton("watari");
        panel.add(buttonTwo);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, button);

        frame.setBounds(50, 250, 500, 300);
        // frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

