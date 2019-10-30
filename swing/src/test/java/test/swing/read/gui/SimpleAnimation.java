package test.swing.read.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/16 8:56
 */
public class SimpleAnimation {
    /**
     * 在主要的 GUI 中创建两个实例变量用来代表图形的坐标.
     */
    private int x = 70;
    private int y = 70;

    public static void main(String[] args) {
        SimpleAnimation gui = new SimpleAnimation();
        gui.go();
    }

    private void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建 frame 上的 widget.
        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(drawPanel);
        frame.setSize(300, 300);
        frame.setVisible(true);

        // 重复 130 次.
        for (int i = 0; i < 130; i++) {
            // 递增坐标值.
            x++;
            y++;

            // 要求重新绘制面板.
            drawPanel.repaint();

            try {
                // 加上延迟可以放慢, 不然一下就会跑完.
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private class MyDrawPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            // 先把颜色设定为 白色, 然后填满整个方块区域.
            // 也就是从 0,0 位置开始以白色填入面板长宽大小的区域.
            // 可以尝试把以下两行注释, 查看效果.
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.GREEN);
            g.fillOval(x, y, 40, 40);
        }
    }

}
