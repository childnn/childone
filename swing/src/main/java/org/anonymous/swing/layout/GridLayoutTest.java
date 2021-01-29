package org.anonymous.swing.layout;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.util.Arrays;
import java.util.List;

import static java.awt.BorderLayout.NORTH;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.awt.GridLayout 网格布局. 默认从左向右,从上到下依次添加到每个网格中.
 * 放置在 GridLayout 布局管理器中的各组件的大小由组件所处的区域来决定, 每个组件将自动占满整个区域.
 * 可以指定行数, 列数, 横向间距, 纵向间距.
 * @since 2021/1/14 15:08
 */
public class GridLayoutTest {

    public static void main(String[] args) {
        Frame f = new Frame("计算器");

        Panel p1 = new Panel();
        p1.add(new TextField(30));
        f.add(p1, NORTH); // Frame 使用默认的布局管理器: BorderLayout

        Panel p2 = new Panel();
        // Panel 使用 GridLayout 布局管理器
        p2.setLayout(new GridLayout(3, 5, 4, 4));

        List<String> names = Arrays.asList("0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "+", "-", "*", "/", ".");
        names.forEach(name -> p2.add(new Button(name)));
        f.add(p2);

        f.pack();
        f.setVisible(true);
    }


}
