package org.anonymous.swing;

import java.awt.Button;
import java.awt.Frame;
import java.awt.ScrollPane;
import java.awt.TextField;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/14 14:20
 */
public class ScrollPaneTest {

    /**
     * ScrollPane 使用 BorderLayout 布局管理器, 而 BorderLayout 导致该容器只有一个组件被显示出来.
     * BorderLayout 在一个区域只能添加一个组件, 后来者会覆盖前者
     *
     * @see org.anonymous.swing.layout.BorderLayoutTest
     */
    public static void main(String[] args) {
        Frame f = new Frame("Title");
        ScrollPane sp = new ScrollPane(ScrollPane.SCROLLBARS_ALWAYS);
        sp.add(new TextField(20));
        sp.add(new Button("Click"));

        f.add(sp);
        f.setBounds(400, 400, 400, 120);
        f.setVisible(true);
    }

}
