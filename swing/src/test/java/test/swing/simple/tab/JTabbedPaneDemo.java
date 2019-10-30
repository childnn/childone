package test.swing.simple.tab;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/11 19:51
 * 选项卡
 */
public class JTabbedPaneDemo extends JPanel {

    // alt + VK_x: 键盘操作
    public JTabbedPaneDemo() {
        super(new GridLayout(1, 1));
        JTabbedPane tabbedPane = new JTabbedPane();
        JComponent tab1 = makeTextPanel("计算机名");

        tabbedPane.addTab("pc", null, tab1, "aaaaaaaa");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = makeTextPanel("硬件");
        tabbedPane.addTab("硬件", null, panel2, "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        JComponent panel3 = makeTextPanel("高级");
        tabbedPane.addTab("高级", null, panel3, "Still does nothing");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        JComponent panel4 = makeTextPanel("系统保护");
        panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("系统保护", null, panel4, "Does nothing at all");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

        add(tabbedPane);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("我的电脑-属性");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(700, 400, 300, 300);

        frame.add(new JTabbedPaneDemo(), BorderLayout.CENTER);

        //frame.pack();
        frame.setVisible(true);
    }

    private JComponent makeTextPanel(String content) {
        JPanel panel = new JPanel(false);
        JLabel label = new JLabel(content, JLabel.CENTER);
        label.setSize(300, 300);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(label);
        return panel;
    }

}
