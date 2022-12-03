package org.anonymous.swing;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/12/10
 */
public class ImageTest {

    public static void main(String[] args) {
        JLabel jl = new JLabel("image");
        // 相对路径以当前项目所在路径为准
        ImageIcon image = new ImageIcon("swing/src/main/java/org/anonymous/swing/IU.jpg");

        // 自定义大小, 如果不指定, 为原图大小
        image.setImage(image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

        jl.setIcon(image);
        JFrame jf = new JFrame("frame");
        jf.setSize(500, 500);
        jf.add(jl);

        jf.setVisible(true);

        jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
