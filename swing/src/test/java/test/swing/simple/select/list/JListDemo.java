package test.swing.simple.select.list;

import javax.swing.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 18:48
 * 列表框: ctrl/shift 多选
 */
public class JListDemo {

    public static void main(String[] args) {
        JFrame jf = new JFrame("Swing-JList"); // 窗口
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.setBounds(300, 300, 300, 300);

        JPanel jp = new JPanel();// 面板

        JLabel jl = new JLabel("证件类型: ");
        jp.add(jl);

        JList<String> jList = new JList<>(new String[]{"身份证", "军官证", "驾驶证"});
        jp.add(jList);

        jf.add(jp);
        jf.setVisible(true);
    }
}
