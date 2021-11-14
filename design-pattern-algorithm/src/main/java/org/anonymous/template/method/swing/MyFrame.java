package org.anonymous.template.method.swing;

import javax.swing.*;
import java.awt.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/8 14:39
 * 我们扩展了 JFrame, 它包含一个 update() 方法, 这个方法控制更新屏幕的算法.
 * 我们可以通过覆盖 paint() 钩子方法和这个算法挂上钩.
 */
public class MyFrame extends JFrame {
    public MyFrame(String title) throws HeadlessException {
        super(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setSize(300, 300);
        this.setVisible(true);
    }

    /**
     * JFrame 的更新算法被称为 paint(). 在默认状态下, paint() 是不做事的...
     * 它只是一个 钩子 hook. 我们覆盖 paint(), 告诉 JFrame 在窗口上画出一条消息.
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        String msg = "I rule!!!";
        g.drawString(msg, 100, 100);
    }

    public static void main(String[] args) {
        MyFrame frame = new MyFrame("Head First Design Patterns");
    }
}
