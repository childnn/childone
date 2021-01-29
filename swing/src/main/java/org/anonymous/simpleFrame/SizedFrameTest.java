package org.anonymous.simpleFrame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * @author child
 * 2019/5/2 12:11
 */
public class SizedFrameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new SizedFrame();
            frame.setTitle("SizedFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class SizedFrame extends JFrame {
    public SizedFrame() {
        //get screen dimensions
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        //set frame width, height and let platform pick screen location
        setSize(width / 2, height / 2);
        setLocationByPlatform(true);

        //set frame icon
        Image image = new ImageIcon("IU.jpg").getImage();
        setIconImage(image);
    }
}