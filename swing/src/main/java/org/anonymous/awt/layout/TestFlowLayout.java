package org.anonymous.awt.layout;

import org.anonymous.util.AwtUtil;

import java.awt.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/12/02
 * {@link FlowLayout} 布局管理器, 流布局管理器, 即组件从左到右按顺序配置在
 * {@link Container} 中, 如果到达右边界, 则会折回到下一行中, 这就像水流向一个方向 从左到右
 * 流动, 遇到边界折回换行一样. {@link Panel} 和 {@link java.applet.Applet} 容器都是默认采用
 * {@link FlowLayout} 布局管理器
 */
public class TestFlowLayout {

    public static void main(String[] args) {
        // 定一个 frame 容器
        Frame f = new Frame("一个容器");
        // 按钮
        Button left = new Button("left");
        Button center = new Button("center");
        Button right = new Button("right");

        // 为 frame 设置流布局管理器
        f.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // 将按钮添加到容器
        // 必须按顺序
        f.add(left);
        f.add(center);
        f.add(right);

        // 可以改变 width 值, 看各个按钮的摆放效果
        f.setBounds(500, 400, 50, 200);
        // f.pack();

        f.setVisible(true);

        AwtUtil.closeWindow(f);
    }

}
