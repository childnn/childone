package test.swing.simple.label;

import javax.swing.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 15:19
 */
public class JLabelDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("Swing-label"); // 窗口
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setBounds(400, 400, 500, 600);

        JPanel jp = new JPanel(); // 面板
        JLabel lb1 = new JLabel("普通标签"); // 标签
        JLabel lb2 = new JLabel();
        lb2.setText("调用 setText 方法");

        ImageIcon img = new ImageIcon(); // 创建一个图标
        // 创建既含有文本又含有图标的 JLabel 对象
        JLabel lb3 = new JLabel("图片标签", img, JLabel.CENTER);

        jp.add(lb1);
        jp.add(lb2);
        jp.add(lb3);

        jf.add(jp);

        jf.setVisible(true);
    }
}
