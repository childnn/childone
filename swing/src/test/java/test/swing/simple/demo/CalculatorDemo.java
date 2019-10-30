package test.swing.simple.demo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 19:39
 * 计算器界面可以分成两部分，即显示区和键盘区。
 * 显示区可以使用文本框组件，键盘区则是由很多按钮组成，可以使用网格布局管理器。
 */
public class CalculatorDemo extends JFrame {

    private JPanel contentPane; // 内容面板
    private JTextField textField; // 文本框

    public CalculatorDemo(String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 500, 250, 200);

        contentPane = new JPanel(new BorderLayout(0, 0)); // 创建内容面板
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // 面板边框
        //contentPane.setLayout(new BorderLayout(0, 0)); // 设置内容面板为边界布局
        setContentPane(contentPane); // 应用内容面板

        setTextJPanel();
        setButtonJPanel();

        setVisible(true);

    }

    public static void main(String[] args) {
        new CalculatorDemo("Calculator");
    }

    private void setButtonJPanel() {
        // 4*4 网格布局的面板, 用于保存按钮
        JPanel btJP = new JPanel(new GridLayout(4, 4, 5, 5));
        contentPane.add(btJP, BorderLayout.CENTER); // 将面板放置在边界布局的中央

        String[] arr = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+"};
        for (String s : arr) {
            btJP.add(new JButton(s));
        }

        //add(btJP);
    }

    private void setTextJPanel() {
        JPanel textJP = new JPanel(); // 新建面板用于保存文本框
        contentPane.add(textJP, BorderLayout.NORTH); // 将面板放置在边界布局的北部

        textField = new JTextField(); // 文本框
        textField.setHorizontalAlignment(SwingConstants.RIGHT); // 文本右对齐
        textField.setColumns(18);
        textJP.add(textField); // 将文本框增加到面板中

        //add(textJP);
    }

}
