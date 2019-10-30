package test.swing.read.text;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/16 23:01
 * @see JTextField 文本框.
 * @see JTextArea 文本域.
 *
 */
public class TextAreal implements ActionListener {

    private JTextArea text; // 文本域: 可设置高度, 字宽.

    public static void main(String[] args) {
        TextAreal gui = new TextAreal();
        gui.go();
    }

    private void go() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton button = new JButton("Just Click It");
        button.addActionListener(this);
        // 参数一: 行数(高度), 参数二: 列数(字宽, 字符个数)
        text = new JTextArea(10, 20);
        // 启用自动换行.
        text.setLineWrap(true);

        // 将 text 赋值给新创建的 JScrollPane.
        JScrollPane scroller = new JScrollPane(text);
        // 指定只是用垂直滚动条.
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        // 禁用 水平滚动条.
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // 这很重要, 加入的是带有文本域的滚动条, 而不是文本域.
        panel.add(scroller);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, button);

        frame.setSize(350, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 在按下按钮时, 插入一个换行字符, 不然不会换行.
        text.append("button clicked \n");
    }
}
