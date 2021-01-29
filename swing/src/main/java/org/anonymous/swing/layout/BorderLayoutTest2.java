package org.anonymous.swing.layout;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextField;

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/14 15:01
 * 实现多组件展示: 打破 BorderLayout 的 五组件限制
 */
public class BorderLayoutTest2 {

    public static void main(String[] args) {
        Frame f = new Frame("Multi");
        f.setLayout(new BorderLayout(30, 5));

        f.add(new Button("南"), SOUTH);
        f.add(new Button("北"), NORTH);

        Panel p = new Panel();
        p.add(new TextField(20));
        p.add(new Button("Click"));

        // 将 Panel 添加到中间区域
        f.add(p);

        f.add(new Button("东"), EAST);
        f.add(new Button("西"), WEST);

        f.pack();
        f.setVisible(true);
    }

}
