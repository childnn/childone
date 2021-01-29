package org.anonymous.simpleFrame;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

/**
 * @author child
 * 2019/5/2 12:00
 */
public class SimpleFrameTest0 {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Dimension screenSize = toolkit.getScreenSize(); //获取当前屏幕大小(面积)
                int width = screenSize.width;
                int height = screenSize.height;
                SimpleFrame frame = new SimpleFrame();
                //                frame.setSize(screenSize);
                frame.setSize(width / 2, height / 2);
                frame.setLocationByPlatform(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
