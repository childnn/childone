package test.swing.simple.frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Container;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see JPanelDemo
 * @since 2020/3/7 23:35
 * Swing 顶层容器之一 {@link JFrame} 窗口
 */
public class JFrameDemo extends JFrame {

    public JFrameDemo(String title) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        setTitle(title);
        setSize(200, 300);
        Container c = getContentPane();
        c.add(new JLabel("这是一个标签", SwingConstants.CENTER));
    }

    public static void main(String[] args) {
        new JFrameDemo("Swing Demo");
    }

}
