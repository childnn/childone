package org.anonymous.awt.btn;

import org.anonymous.util.AwtUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
 * @since 2021/12/15
 * 为按钮添加单击事件
 */
public class TestButton {

    private Frame f;
    private Button b1;

    public static void main(String[] args) {
        TestButton tb = new TestButton();
        tb.init();
    }
    // private Button b2;
    // private Button b3;

    private void init() {
        f = new Frame("Button event");
        b1 = new Button("按钮单击事件");
        b1.addActionListener(e -> {
            Random rd = new Random();
            int r = rd.nextInt(255);
            int g = rd.nextInt(255);
            int b = rd.nextInt(255);

            Color c1 = new Color(r, g, b);
            Color c2 = new Color(g, b, r);

            //
            b1.setBackground(c1);

            f.setBackground(c2);
        });

        f.setLayout(new FlowLayout(FlowLayout.CENTER));
        f.add(b1);
        f.setBounds(500, 300, 200, 120);
        f.setVisible(true);

        AwtUtil.closeWindow(f);
    }


}
