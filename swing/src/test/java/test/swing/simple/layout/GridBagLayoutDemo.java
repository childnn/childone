package test.swing.simple.layout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 12:34
 * 网格包布局管理器
 */
public class GridBagLayoutDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame("拨号盘");
        jf.setBounds(100, 200, 500, 500);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridBagLayout gbagLayout = new GridBagLayout();
        jf.setLayout(gbagLayout); // 使用 GridBag layout 布局管理器

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH; // 组件填充显示区域
        constraints.weightx = 0.0; // 恢复默认值
        constraints.gridwidth = GridBagConstraints.REMAINDER; // 结束行
        JTextField jtf = new JTextField("123456789");

        gbagLayout.setConstraints(jtf, constraints);
        jf.add(jtf);

        constraints.weightx = 0.5; // 指定组件的分配区域
        constraints.weighty = 0.2;

        constraints.gridwidth = 1;
        makeBt("7", jf, gbagLayout, constraints); // 调用方法, 添加按钮组件
        makeBt("8", jf, gbagLayout, constraints);
        constraints.gridwidth = GridBagConstraints.REMAINDER; // 结束行
        makeBt("9", jf, gbagLayout, constraints);

        constraints.gridwidth = 1; // 重新设置 grid width 属性
        makeBt("4", jf, gbagLayout, constraints);
        makeBt("5", jf, gbagLayout, constraints);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        makeBt("6", jf, gbagLayout, constraints);

        constraints.gridwidth = 1;
        makeBt("1", jf, gbagLayout, constraints);
        makeBt("2", jf, gbagLayout, constraints);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        makeBt("3", jf, gbagLayout, constraints);

        constraints.gridwidth = 1;
        makeBt("返回", jf, gbagLayout, constraints);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        makeBt("拨号", jf, gbagLayout, constraints);

        constraints.gridwidth = 1;

        jf.setVisible(true);
    }

    private static void makeBt(String title, JFrame jf, GridBagLayout gbagLayout, GridBagConstraints constraints) {
        JButton jb = new JButton(title);
        gbagLayout.setConstraints(jb, constraints);
        jf.add(jb);
    }

}
