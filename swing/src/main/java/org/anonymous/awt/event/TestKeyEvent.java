package org.anonymous.awt.event;

import org.anonymous.util.AwtUtil;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/12/15
 * 键盘输入事件
 */
public class TestKeyEvent {

    Frame f;

    public static void main(String[] args) {
        TestKeyEvent tke = new TestKeyEvent();
        tke.init();
    }
    // Label label;

    private void init() {
        f = new Frame();
        f.setBackground(Color.CYAN);
        f.setVisible(true);
        AwtUtil.closeWindow(f);
        f.setBounds(300, 300, 500, 300);

        f.setLayout(new FlowLayout());
        Label label = new Label("label");
        f.add(label);

        f.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                char keyChar = e.getKeyChar();
                // int keyLocation = e.getKeyLocation();

                String msg = String.format("Code: %s, Char: `%s`%n", keyCode, keyChar);

                System.out.print(msg);
                label.setText(msg);
            }

        });
    }

}
