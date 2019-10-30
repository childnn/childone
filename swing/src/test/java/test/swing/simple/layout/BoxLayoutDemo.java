package test.swing.simple.layout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 12:55
 * 盒布局管理器
 */
public class BoxLayoutDemo {

    public static void main(String[] args) {
        JFrame jf = new JFrame("The sixth Swing");
        jf.setBounds(100, 200, 400, 200);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Box hb = Box.createHorizontalBox(); // 创建横向 box 容器
        hb.add(Box.createVerticalStrut(200)); // 添加高度为 200 的垂直框架
        hb.add(new JButton("west"));
        hb.add(Box.createHorizontalStrut(140)); // 添加长度为 140 的水平框架
        hb.add(new JButton("east"));
        hb.add(Box.createHorizontalGlue()); // 添加水平胶水

        Box vb = Box.createVerticalBox(); // 创建纵向 box 容器
        // 添加宽 100， 高 20 的固定区域
        vb.add(Box.createRigidArea(new Dimension(100, 20)));
        vb.add(new JButton("north"));
        vb.add(Box.createVerticalGlue()); // 添加垂直组件
        vb.add(new JButton("south"));
        vb.add(Box.createVerticalStrut(40)); // 添加长度为 40 的垂直框架

        // 向水平 box 添加嵌套的纵向 box
        hb.add(vb);

        jf.add(hb);
        jf.setVisible(true);
    }
}
