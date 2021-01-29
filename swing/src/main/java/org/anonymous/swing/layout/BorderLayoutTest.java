package org.anonymous.swing.layout;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/14 14:42
 * 每个组件都尽量占据整个区域, 所以中间按钮比较大
 * BorderLayout 最多只能放置 5 个组件, 可以少于 5 个.
 * 如果某个区域没有放置组件, 该区域不会空白, 旁边的组件会自动占据该区域, 从而保证窗口有较好的外观.
 * --
 * 虽然 BorderLayout 最多只支持 5 个组件, 但因为容器也是一个组件, 所以可以先向 Panel 中添加多个组件,
 * 再把 Panel 添加到 BorderLayout 布局管理器中, 从而让 BorderLayout 布局管理器的实际组件远超出 5 个.
 */
public class BorderLayoutTest {

    public static void main(String[] args) {
        Frame f = new Frame("Border");

        f.setLayout(new BorderLayout(30, 5));

        f.add(new Button("南"), SOUTH);
        f.add(new Button("北"), NORTH);
        f.add(new Button("中")); // 默认 CENTER // 注释此行并不能达到自动布局的目的?
        f.add(new Button("东"), EAST);
        f.add(new Button("西"), WEST);

        // 自动设置大小
        f.pack();

        f.setVisible(true);

    }

}
