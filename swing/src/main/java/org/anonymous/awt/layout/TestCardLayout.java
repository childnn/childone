package org.anonymous.awt.layout;

import org.anonymous.util.AwtUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see CardLayout#first(Container)
 * @see CardLayout#last(Container)
 * @see CardLayout#next(Container)
 * @see CardLayout#show(Container, String)
 * @since 2021/12/07
 * {@link CardLayout} 卡片布局管理, 它将放置于容器中的组件看成是一叠卡片,
 * 每次只有最上面的哪个组件才可见. 这就像一叠放置整齐的扑克牌一样, 只有最上面的
 * 那一张才能被看到.
 */
public class TestCardLayout implements ActionListener {

    static final String[] name = {
            "A", "B", "C", "D", "E", "F", "G"
    };
    private final String next = "next";
    private final String previous = "previous";

    private CardLayout cl;
    private Panel p1;


    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource(); // btn
        String cmd = e.getActionCommand();
        if (previous.equals(cmd)) {
            cl.previous(p1);
        } else if (next.equals(cmd)) {
            cl.next(p1);
        }
    }

    public static void main(String[] args) {
        new TestCardLayout().init();
    }

    private void init() {
        // Label[] cards = new Label[name.length];

        Frame f = new Frame("some cards");
        AwtUtil.closeWindow(f);
        f.setVisible(true);

        // 设置 frame 为流动布局管理器
        f.setLayout(new FlowLayout(FlowLayout.CENTER));

        // 新建 卡片布局管理器
        cl = new CardLayout();
        p1 = new Panel(cl);

        // 把 卡片加入到 p1
        for (String s : name) {
            p1.add(s, new Label(s));
        }

        Panel p2 = new Panel();

        Button previous = new Button(this.previous);
        Button next = new Button(this.next);

        // 为按钮添加事件
        previous.addActionListener(this);
        next.addActionListener(this);

        // 将按钮添加到 p2
        p2.add(previous);
        p2.add(next);

        // 将 p1, p2 添加到 frame
        f.add(p1);
        f.add(p2);

        f.setBounds(300, 400, 300, 300);
    }

}
