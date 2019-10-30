package test.swing.simple.menu.tool;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/10 13:29
 * 工具栏
 */
public class JToolBarDemo extends JPanel implements ActionListener {

    private static final String NEW_LINE = "\n";
    private static final String OPEN = "OPEN";
    private static final String SAVE = "SAVE";
    private static final String NEW = "NEW";
    private JTextArea textArea;

    public JToolBarDemo() {
        super(new BorderLayout());
        JToolBar toolBar = new JToolBar();
        addButtons(toolBar);
        // 文本域
        textArea = new JTextArea(15, 30);
        textArea.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        setPreferredSize(new Dimension(450, 110));
        add(toolBar, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame jf = new JFrame("工具栏"); // 窗口
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JToolBarDemo();
        contentPane.setOpaque(true);

        jf.setContentPane(contentPane);

        jf.pack();
        jf.setVisible(true);
    }

    // 设置工具栏按钮
    private void addButtons(JToolBar toolBar) {
        JButton button;
        button = makeNavigationButton("new1", NEW, "新建一个文件", "新建");
        toolBar.add(button);
        button = makeNavigationButton("open1", OPEN, "打开一个文件", "打开");
        toolBar.add(button);
        button = makeNavigationButton("save1", SAVE, "保存当前文件", "保存");
        toolBar.add(button);
    }

    private JButton makeNavigationButton(String imgName, String actionCmd, String toolTipText, String altText) {
        URL imgURL = getClass().getResource(imgName + ".jpg");
        // 按钮
        JButton bt = new JButton();
        // 设置按钮命令
        bt.setActionCommand(actionCmd);
        // 设置提示信息: 鼠标悬浮信息
        bt.setToolTipText(toolTipText);
        // 监听按钮
        bt.addActionListener(this);

        if (imgURL != null) {
            bt.setIcon(new ImageIcon(imgURL));
        } else {
            bt.setText(altText);
            System.err.println(String.format("Resource [%s] not found", imgName));
        }
        return bt;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String text = source.getText();
        System.out.println(text);
    }

}
