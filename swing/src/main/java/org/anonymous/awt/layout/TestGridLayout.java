package org.anonymous.awt.layout;

import org.anonymous.util.AwtUtil;

import java.awt.*;

import static java.awt.BorderLayout.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.awt.GridBagLayout, 网状布局, 它将组件配置在纵横交错分隔开的网状格子当中,
 * 从左到右, 从上到下, 分割开的格子大小相同. 放置在 GridLayout 中的组件大小所处区域
 * 的大小决定.
 * @since 2021/12/02
 */
public class TestGridLayout {

    public static void main(String[] args) {
        Frame f = new Frame("一个容器");
        AwtUtil.closeWindow(f);
        f.setVisible(true);
        f.setBounds(500, 400, 300, 500);

        // 定义按钮
        Button east = new Button("EAST");
        Button south = new Button("SOUTH");
        Button west = new Button("WEST");
        Button north = new Button("NORTH");
        Button center = new Button("CENTER");

        // 为容器设置布局管理器
        f.setLayout(new GridLayout(10, 10));

        // 添加按钮到容器
        f.add(east, EAST);
        f.add(south, SOUTH);
        f.add(west, WEST);
        f.add(north, NORTH);
        f.add(center, CENTER);
    }

}
