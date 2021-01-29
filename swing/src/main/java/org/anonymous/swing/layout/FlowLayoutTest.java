package org.anonymous.swing.layout;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/14 14:36
 */
public class FlowLayoutTest {

    /**
     * 改变窗口大小, 看变化
     */
    public static void main(String[] args) {
        Frame f = new Frame("Flow");
        f.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

        for (int i = 0; i < 10; i++) {
            f.add(new Button("btn" + i));
        }

        // 以下两种方式选其一: 二者同时存在则后者生效
        // 设置窗口为最佳大小
        f.pack();
        // f.setBounds(400, 400, 50, 400); // 手动设置大小

        f.setVisible(true);
    }

}
