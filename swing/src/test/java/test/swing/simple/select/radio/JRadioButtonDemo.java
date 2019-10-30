package test.swing.simple.select.radio;

import javax.swing.*;
import java.awt.Font;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 17:27
 * 单选框
 */
public class JRadioButtonDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("Swing-radio"); // 窗口
        jf.setBounds(100, 300, 400, 400);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel(); // 面板
        JLabel jl = new JLabel("现在是哪个季节");
        jl.setFont(new Font("楷体", Font.BOLD, 32));

        JRadioButton spring = new JRadioButton("Spring", true);
        JRadioButton summer = new JRadioButton("Summer");
        JRadioButton autumn = new JRadioButton("Autumn");
        JRadioButton winter = new JRadioButton("Winter");

        jp.add(jl);
        jp.add(spring);
        jp.add(summer);
        jp.add(autumn);
        jp.add(winter);

        // 使用同一个 button group
        ButtonGroup btGroup = new ButtonGroup();
        btGroup.add(spring);
        btGroup.add(summer);
        btGroup.add(autumn);
        btGroup.add(winter);

        jf.add(jp);
        jf.setVisible(true);
    }
}
