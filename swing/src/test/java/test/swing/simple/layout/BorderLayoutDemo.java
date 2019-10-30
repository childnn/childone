package test.swing.simple.layout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;


/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see Component#setSize   设置组件大小
 * @see Component#setLocation  设置组件位置. 参数为左上角的坐标(当前屏幕的最左上角坐标为 (0, 0))
 * @see Component#setBounds  设置组件位置和大小. 以上两个方法实际上就是调用此方法.
 * <p>
 * 边框布局管理器
 * @since 2020/3/8 11:09
 */
public class BorderLayoutDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("The third Swing");
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setBounds(100, 200, 1600, 600);
        //jf.setSize(400, 500);
        jf.setLayout(new BorderLayout(50, 10)); // 为 frame 窗口设置布局为 BorderLayout. 参数为 水平/垂直间隙

        JButton upBt = new JButton("上");
        upBt.setBackground(Color.GREEN);
        JButton downBt = new JButton("下");
        JButton leftBt = new JButton("左");
        JButton rightBt = new JButton("右");
        JButton centerBt = new JButton("中");

        jf.add(upBt, BorderLayout.NORTH);
        jf.add(downBt, BorderLayout.SOUTH);
        jf.add(leftBt, BorderLayout.WEST);
        jf.add(rightBt, BorderLayout.EAST);
        jf.add(centerBt, BorderLayout.CENTER);

        jf.setVisible(true);

    }
}
