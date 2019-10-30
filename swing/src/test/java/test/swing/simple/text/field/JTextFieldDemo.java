package test.swing.simple.text.field;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Font;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 15:43
 * 文本框: 单行
 */
public class JTextFieldDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("Swing-text field"); // 窗口
        jf.setBounds(100, 300, 500, 200);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel(); // 面板
        JTextField jtf1 = new JTextField("普通文本框");
        JTextField jtf2 = new JTextField(28);
        jtf2.setFont(new Font("楷体", Font.BOLD, 16));
        jtf2.setText("指定长度和字体的文本框");

        JTextField jtf3 = new JTextField("居中对齐", 30);
        jtf3.setHorizontalAlignment(JTextField.CENTER);

        jp.add(jtf1);
        jp.add(jtf2);
        jp.add(jtf3);

        jf.add(jp);
        jf.setVisible(true);
    }
}
