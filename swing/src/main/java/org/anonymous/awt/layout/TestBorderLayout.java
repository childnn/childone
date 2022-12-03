package org.anonymous.awt.layout;

import org.anonymous.util.AwtUtil;

import java.awt.*;

import static java.awt.BorderLayout.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/12/02
 * {@link java.awt.BorderLayout} 将容器空间分为 east/south/west/north/center
 * 5 个区域, 每个区域只能放一个组件和容器组件. {@link java.awt.BorderLayout} 是
 * {@link java.awt.Frame} 和 {@link java.awt.Dialog} 容器的默认管理器. 如果在同一个区域
 * 中放入多个 {@link java.awt.Component}, 后放入的组件会将原来的覆盖, 放置在各个区域中的组件和大小
 * 根据所处区域的大小而变化.
 */
public class TestBorderLayout {

    public static void main(String[] args) {
        Frame f = new Frame("一个容器");
        AwtUtil.closeWindow(f);
        f.setVisible(true);
        f.setBounds(500, 400, 300, 200);

        // 定义按钮
        Button east = new Button("EAST");
        Button south = new Button("SOUTH");
        Button west = new Button("WEST");
        Button north = new Button("NORTH");
        Button center = new Button("CENTER");

        // 为容器设置布局管理器
        f.setLayout(new BorderLayout(5, 10));

        // 添加按钮到容器
        f.add(east, EAST);
        f.add(south, SOUTH);
        f.add(west, WEST);
        f.add(north, NORTH);
        f.add(center, CENTER);
    }

}
