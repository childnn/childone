package test.swing.simple.frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see JFrameDemo
 * @since 2020/3/8 9:49
 * Swing 的一个中间层容器，它能容纳组件并将组件组合在一起，但它本身必须添加到其他容器中使用.
 * {@link JPanel}
 */
public class JPanelDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("第二个 Swing 程序");
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jf.setBounds(300, 100, 400, 200);
        JPanel jp = new JPanel(); // 创建一个 JPanel 对象
        JLabel jl = new JLabel("这是放在 JPanel 上的标签");// 创建一个标签

        //jl.setBackground(Color.CYAN);
        //jl.setBorder(BasicBorders.getTextFieldBorder());
        //jl.setHorizontalTextPosition(SwingConstants.RIGHT);
        //jl.setVerticalAlignment(SwingConstants.BOTTOM);

        jp.setBackground(Color.GREEN);
        jp.add(jl); // 将标签添加到面板
        jf.add(jp); // 将面板添加到窗口

    }


    //int x;
    //int y;
    //
    //@ConstructorProperties({"jack", "rose"})
    //public JPanelDemo(int x, int y) {
    //    this.x = x;
    //    this.y = y;
    //}
}
