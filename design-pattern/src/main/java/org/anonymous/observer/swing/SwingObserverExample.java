package org.anonymous.observer.swing;

import javax.swing.*;
import java.awt.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/29 14:38
 */
public class SwingObserverExample {
    private JFrame frame;

    public static void main(String[] args) {
        SwingObserverExample example = new SwingObserverExample();
        example.go();
    }

    /**
     * javax.swing.AbstractButton#fireActionPerformed(java.awt.event.ActionEvent)
     */
    private void go() {
        frame = new JFrame();
        frame.setSize(200, 100);
        frame.setLocationByPlatform(true);

        JButton button = new JButton("Should I do it?");
        button.setBounds(20, 10, 20, 10);
        // 两个倾听者(观察者), 一个天使, 一个魔鬼.
        button.addActionListener(e -> System.out.println("Don't do it, you might regret it!"));
        button.addActionListener(e -> System.out.println("Come on, do it!"));
        frame.getContentPane().add(BorderLayout.CENTER, button);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
