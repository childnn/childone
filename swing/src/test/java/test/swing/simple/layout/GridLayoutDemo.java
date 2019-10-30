package test.swing.simple.layout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.GridLayout;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 12:13
 * 网格布局管理器
 */
public class GridLayoutDemo {

    public static void main(String[] args) {
        JFrame jf = new JFrame("The Grid layout");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setBounds(300, 100, 500, 500);

        JPanel jp = new JPanel();// 面板
        // 指定面板的布局为 grid layout
        // 4 行 4 列, 间隙为 10
        jp.setLayout(new GridLayout(4, 4, 10, 10));
        String[] arr = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", ".", "=", "+"};
        for (String s : arr) {
            JButton jb = new JButton(s);
            jp.add(jb);
        }

        jf.add(jp);

        jf.setVisible(true);

    }
}
