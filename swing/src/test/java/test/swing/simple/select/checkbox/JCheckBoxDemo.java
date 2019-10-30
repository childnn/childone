package test.swing.simple.select.checkbox;

import javax.swing.*;
import java.awt.Font;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 16:45
 * 复选框
 */
public class JCheckBoxDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("Swing-checkbox");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setBounds(500, 500, 300, 400);

        JPanel jp = new JPanel();// 面板

        JLabel jl = new JLabel("Please select something");
        jl.setFont(new Font("行书", Font.BOLD, 23));
        jp.add(jl);

        String[] arr = {"C", "C++", "C#", "Java", "Python", "PHP", "Perl"};
        for (String s : arr) {
            if ("C".equalsIgnoreCase(s)) {
                jp.add(new JCheckBox(s, true));
            } else {
                jp.add(new JCheckBox(s));
            }
        }

        jf.add(jp);
        jf.setVisible(true);
    }
}
