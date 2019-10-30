package test.swing.read.layout;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/16 17:51
 * @see LayoutManager # 布局管理器
 * 三大布局管理器
 * @see BorderLayout # 把背景组件分割成 5 个区域. 每个被管理的区域只能放一个组件.
 *   由此管理员安置的组件通常不会取得默认的大小. 这是框架默认的布局管理器.
 *   注: 如果在 {@link BorderLayout#EAST} 或 {@link BorderLayout#WEST}, 宽度可以自己决定, 但是高度会受布局管理器控制.
 *    如果是在 {@link BorderLayout#NORTH} 或 {@link BorderLayout#SOUTH}, 高度可以自己决定, 但是宽度会受布局管理器控制.
 *    中间主键的大小要看扣除周围之后还剩下写什么.
 *    东西会取得预设的宽度, 南北会取得预设的高度.
 *
 * @see FlowLayout # 每个组件都会依照理想的大小呈现, 并且会从左到右依照加入的顺序以可能会换行的方式排列.
 *   因此在组件放不下的时候会被放到下一行. 这hi是面板默认的布局管理器.
 * @see javax.swing.BoxLayout # 像 FlowLayout 一样让每个组件使用默认的大小, 并且按照加入的顺序来排列.
 *   但 BoxLayout 是以垂直的方向来排列(也可以水平, 但通常我们只在乎垂直方式). 不像 FlowLayout 会自动地换行,
 *   它让你插入某种类似换行的机制来强制组件从新的一行开始排列.
 */
public class Button1 {

    public static void main(String[] args) {
        Button1 gui = new Button1();
        gui.goCenter();
    }

    private void go() {
        JFrame frame = new JFrame();
        JButton button = new JButton("click me");
        // 指定区域.
        frame.getContentPane().add(BorderLayout.EAST, button);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    private void go1() {
        JFrame frame = new JFrame();
        JButton button = new JButton("There is no spoon...");
        // 指定区域.
        frame.getContentPane().add(BorderLayout.NORTH, button);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    private void go2() {
        JFrame frame = new JFrame();
        JButton button = new JButton("click this!");
        // 更大的字体会强迫框架留更多的高度给按钮.
        Font bigFont = new Font("serif", Font.BOLD, 28);
        button.setFont(bigFont);
        // 指定区域.
        frame.getContentPane().add(BorderLayout.NORTH, button);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    private void goCenter() {
        JFrame frame = new JFrame();

        JButton east = new JButton("East");
        JButton west = new JButton("West");
        JButton north = new JButton("North");
        JButton south = new JButton("South");
        JButton center = new JButton("Center");

        frame.getContentPane().add(BorderLayout.EAST, east);
        frame.getContentPane().add(BorderLayout.WEST, west);
        frame.getContentPane().add(BorderLayout.NORTH, north);
        frame.getContentPane().add(BorderLayout.SOUTH, south);
        frame.getContentPane().add(BorderLayout.CENTER, center);

        frame.setSize(300, 300);
        frame.setVisible(true);
    }

}
