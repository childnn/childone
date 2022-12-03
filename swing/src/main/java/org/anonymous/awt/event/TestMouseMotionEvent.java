package org.anonymous.awt.event;

import org.anonymous.util.AwtUtil;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.awt.event.MouseMotionListener
 * @since 2021/12/15
 */
public class TestMouseMotionEvent {

    Button b1;
    Button b2;
    Button b3;

    public static void main(String[] args) {
        TestMouseMotionEvent tmm = new TestMouseMotionEvent();
        tmm.init();
    }

    private void init() {
        Frame f = new Frame("mouse");
        f.setLayout(new FlowLayout());
        f.setBounds(500, 300, 500, 300);
        f.setVisible(true);
        AwtUtil.closeWindow(f);

        b1 = new Button("left");
        b2 = new Button("middle");
        b3 = new Button("right");

        Label l = new Label("监听鼠标");
        f.add(b1);
        f.add(b2);
        f.add(b3);

        f.add(l);

        f.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int button = e.getButton();
                System.out.println("button = " + button);
                switch (button) {
                    case MouseEvent.BUTTON1:
                        l.setText("左键：" + button);
                        break;
                    case MouseEvent.BUTTON2:
                        l.setText("中键：" + button);
                        break;
                    case MouseEvent.BUTTON3:
                        l.setText("右键：" + button);
                        break;
                    default:
                        throw new RuntimeException("无效的鼠标按键：" + button);
                }
            }
        });
    }

}
