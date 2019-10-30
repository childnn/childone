package test.swing.simple.text.area;

import javax.swing.*;
import java.awt.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 16:23
 * 文本域: 多行文本
 */
public class JTextAreaDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("Swing-text area"); // 窗口
        jf.setBounds(500, 500, 500, 500);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();// 面板
        JTextArea jta = new JTextArea("please write something", 10, 33);
        jta.setLineWrap(true); // 自动换行
        jta.setFont(new Font("楷体", Font.ITALIC, 16));
        jta.setForeground(Color.GREEN); // 文本域前景色: 字迹颜色
        jta.setBackground(Color.YELLOW); // 文本域背景色

        JScrollPane jsp = new JScrollPane(jta);// 将文本域放入滚动窗口
        Dimension size = jta.getPreferredSize(); // 获得文本域的首选大小
        jsp.setBounds(new Rectangle(new Point(100, 300), size));

        jp.add(jsp); // 将 JScrollPane 添加到 JPanel 容器中
        jf.add(jp); // 将 JPanel 容器添加到 JFrame 容器中

        jf.setVisible(true);
    }
}
