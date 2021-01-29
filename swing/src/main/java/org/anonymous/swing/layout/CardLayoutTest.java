package org.anonymous.swing.layout;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionListener;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.awt.CardLayout 以时间而非空间来管理其中的组件, 它将加入容器的所有组件看成一叠卡片, 每次只有最上面的那个
 * Component 可见.
 * @see java.awt.CardLayout#hgap horizontal 卡片与容器左右边界的间距
 * @see java.awt.CardLayout#vgap vertical 卡片与容器上下边界的间距
 * @see java.awt.CardLayout#first(java.awt.Container) 显示 target 容器中的第一张卡片
 * @see java.awt.CardLayout#last(java.awt.Container) 显示 target 容器中的最后一张卡片
 * @see java.awt.CardLayout#previous(java.awt.Container) 显示 target 容器中的前一张卡片
 * @see java.awt.CardLayout#next(java.awt.Container) 显示 target 容器中的后一张卡片
 * @see java.awt.CardLayout#show(java.awt.Container, String) 显示 target 容器中指定名字的卡片
 * @since 2021/1/14 17:30
 */
public class CardLayoutTest {

    Frame f = new Frame("CardLayout");
    String[] names = {"first", "second", "third", "forth", "fifth"};
    Panel pl = new Panel();

    public static void main(String[] args) {
        new CardLayoutTest().init();
    }

    void init() {
        CardLayout cl = new CardLayout();
        pl.setLayout(cl);

        for (String name : names) {
            pl.add(name, new Button(name));
        }

        Panel p = new Panel();

        // 控制显示上一张的按钮
        Button previous = new Button("previous");
        previous.addActionListener(listener(cl));
        // 下一张
        Button next = new Button("next");
        next.addActionListener(listener(cl));
        // 第一张
        Button first = new Button("first");
        first.addActionListener(listener(cl));
        // 最后一张
        Button last = new Button("last");
        last.addActionListener(listener(cl));

        // 控制根据 Card 名显示的按钮
        Button third = new Button("third");
        third.addActionListener(listener(cl));

        p.add(previous);
        p.add(next);
        p.add(first);
        p.add(last);
        p.add(third);

        f.add(pl);
        f.add(p, BorderLayout.SOUTH);

        f.pack();
        f.setVisible(true);
    }

    private ActionListener listener(CardLayout cl) {
        return e -> {
            String cmd = e.getActionCommand();

            switch (cmd) {
                case "previous":
                    cl.previous(pl);
                    break;
                case "next":
                    cl.next(pl);
                    break;
                case "first":
                    cl.first(pl);
                    break;
                case "last":
                    cl.last(pl);
                    break;
                case "third":
                    cl.show(pl, "third");
                    break;
                default:
                    throw new IllegalArgumentException(String.format("unknown command %s", cmd));
            }
        };
    }

}
