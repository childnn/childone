package org.anonymous.simpleFrame;

import javax.swing.JFrame;
import java.awt.EventQueue;

/**
 * @author child
 * 2019/5/2 11:41
 */
public class SimpleFrameTest {

    public static void main(String[] args) {
        //开启新的线程
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                SimpleFrame frame = new SimpleFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class SimpleFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public SimpleFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}