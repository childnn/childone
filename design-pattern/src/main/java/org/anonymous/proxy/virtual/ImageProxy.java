package org.anonymous.proxy.virtual;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/11 11:46
 */
public class ImageProxy implements Icon {
    private ImageIcon imageIcon; // 此 imageIcon 是我们希望在加载后显示出来的真正的图像.
    private URL imageURL;
    private Thread retrievalThread;
    private boolean retrieving = false;

    // 我们将图像的 URL 传入构造器中, 这是我们希望显示的图像所在的位置.
    public ImageProxy(URL imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public void paintIcon(final Component c, Graphics g, int x, int y) {
        if (null != imageIcon) {
            imageIcon.paintIcon(c, g, x, y); // 如果已有 icon, 就画出自己.
        } else {
            // 图片未加载完成, 显示 "加载中".
            g.drawString("Loading CD cover, please wait...", x + 300, y + 190);
            if (!retrieving) {
                retrieving = true;
                // 在这里加载真正的 icon 图像.
                // 加载图像和 image icon 是同步的(synchronous), 也就是说,
                // 只有在加载完毕后, iamgeIcon 构造才会返回. 这样, 我们的程序会耗在这里,
                // 动弹不得, 也没法显示消息, 所以要把加载变成异步的(asynchronous).
                retrievalThread = new Thread(() -> {
                    try {
                        // 这里的代码会在屏幕上画出一个 icon 图像(通过委托给 image icon).
                        // 然而, 如果我们没有完整创建的 imageIcon, 那就自己创建一个.
                        imageIcon = new ImageIcon(imageURL, "CD Cover");
                        c.repaint();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                retrievalThread.start();
            }
        }
    }

    // 图像加载完毕前, 返回默认的宽和高.
    // 图像加载完毕后, 转给 imageIcon 处理.
    @Override
    public int getIconWidth() {
        if (null != imageIcon) {
            return imageIcon.getIconWidth();
        } else {
            return 800;
        }
    }

    @Override
    public int getIconHeight() {
        if (null != imageIcon) {
            return imageIcon.getIconHeight();
        } else {
            return 600;
        }
    }
}
