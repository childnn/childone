package test.swing.simple.layout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 11:38
 * 流式布局管理器
 * 改变窗口大小, 观察按钮的位置变化
 */
public class FlowLayoutDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("The fourth Swing");
        jf.setBounds(100, 300, 300, 600);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel(new FlowLayout(FlowLayout.LEADING, 50, 10)); // 创建面板
        //jp.setSize(400, 500);
        //jp.setBorder(BasicBorders.getButtonBorder());
        for (int i = 1; i <= 9; i++) {
            Button b = new Button("" + i);
            //b.setSize(50, 60);
            jp.add(b);
        }
        //Button bt1 = new Button("1");
        //Button bt2 = new Button("2");
        //Button bt3 = new Button("3");
        //Button bt4 = new Button("4");
        //Button bt5 = new Button("5");
        //Button bt6 = new Button("6");
        //Button bt7 = new Button("7");
        //Button bt8 = new Button("8");
        //Button bt9 = new Button("9");

        // 向 JPanel 中添加 FlowLayout 布局管理器, 将组件间的横向和纵向间隙都设置为 20px
        //jp.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 10));
        jp.setBackground(Color.GREEN);

        // 添加面板到容器
        jf.add(jp);

        jf.setVisible(true);

    }
}
