package test.swing.simple.layout;

import javax.swing.*;
import java.awt.CardLayout;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 11:56
 * 卡片布局管理器
 */
public class CardLayoutDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("The fifth Swing");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setBounds(100, 300, 600, 700);

        JPanel jp1 = new JPanel(); // 面板一
        JPanel jp2 = new JPanel(); // 面板二
        jp1.add(new JButton("登录"));
        jp1.add(new JButton("注册"));
        jp1.add(new JButton("找回密码"));
        jp2.add(new JTextField("用户名", 30));
        jp2.add(new JTextField("密码", 30));
        jp2.add(new JTextField("验证码", 30));

        // 卡片式布局面板
        JPanel cards = new JPanel(new CardLayout());
        // 向 卡片式布局面板中添加 卡片面板
        cards.add("card1", jp1);
        cards.add("card2", jp2);

        CardLayout cl = ((CardLayout) cards.getLayout());
        cl.show(cards, "card1");  // 调用 show 方法显示 卡片 1/2

        jf.add(cards);

        jf.setVisible(true);

    }
}
