package test.swing.simple.demo;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 22:08
 */
public class ConstellationDemo0 {

    private static final String ADD = "新增";
    private static final String DELETE = "删除";
    private JPanel panel = new JPanel(/*new BorderLayout()*/);
    private JComboBox<String> cmb = new JComboBox<>(); // 下拉框
    private JLabel label = new JLabel("添加新星座: ");
    private JLabel showInfo = new JLabel(); // 用于显示信息
    private JTextField jtf = new JTextField("", 23); // 用于输入信息
    private JButton btAdd = new JButton(ADD);
    private JButton btDel = new JButton(DELETE);

    public ConstellationDemo0() {
        JFrame jf = new JFrame("选择你的星座");
        jf.setBounds(300, 300, 300, 400);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //jf.setContentPane(panel);
        //panel.setBorder(new EmptyBorder(5, 5, 5, 5));

        cmb.addItem("--请选择--");
        cmb.addItem("巨蟹座");
        cmb.addItem("白羊座");
        cmb.addItem("双鱼座");
        cmb.addItemListener(e -> {
            //@SuppressWarnings("unchecked")
            //JComboBox<String> source = ((JComboBox<String>) e.getSource());
            //System.out.println(source);
            panel.add(showInfo, BorderLayout.SOUTH);
            if (cmb.getSelectedIndex() > 0) {
                Object item = e.getItem();
                //System.err.println(item);
                showInfo.setText(String.format("您选择的星座是: %s", item));
                //panel.add(showInfo, BorderLayout.SOUTH);
                showInfo.setVisible(true);
            } else {
                //panel.remove(showInfo);
                showInfo.setVisible(false);
            }
        });

        btAdd.addActionListener(new MyButtonActionListener());
        btDel.addActionListener(new MyButtonActionListener());

        panel.add(cmb/*, BorderLayout.NORTH*/);
        panel.add(jtf/*, BorderLayout.CENTER*/);
        panel.add(btAdd/*, BorderLayout.EAST*/);
        panel.add(btDel/*, BorderLayout.WEST*/);

        jf.add(panel);
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new ConstellationDemo0();
    }

    private class MyButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (ADD.equals(command)) {
                String text = jtf.getText();
                String msg;
                //System.out.println(text.length());
                if (text.length() != 0) {
                    cmb.addItem(text);
                    msg = String.format("%s 新增成功", text);
                } else {
                    msg = "请输入要添加的星座";
                }
                showInfo.setText(msg);
                panel.add(showInfo/*, BorderLayout.SOUTH*/);
                showInfo.setVisible(true);
            }

            if (DELETE.equals(command)) {
                String msg;
                if (cmb.getSelectedIndex() > 0) {
                    Object item = cmb.getSelectedItem();
                    cmb.removeItem(item);
                    msg = String.format("%s 删除成功.", item);
                } else {
                    msg = "请选择要删除的星座";
                }
                showInfo.setText(msg);
                showInfo.setVisible(true);
                panel.add(showInfo/*, BorderLayout.SOUTH*/);
            }
        }
    }
}
