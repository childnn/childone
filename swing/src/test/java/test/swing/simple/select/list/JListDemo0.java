package test.swing.simple.select.list;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 18:54
 * 单选列表框
 */
public class JListDemo0 extends JFrame {

    public JListDemo0(String title) {
        super(title);
        //setTitle();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 300, 300);

        JPanel jp = new JPanel(); // 面板
        jp.setBorder(new EmptyBorder(5, 5, 5, 5)); // 设置面板边框
        jp.setLayout(new BorderLayout(0, 0)); // 设置内容面板为边界布局

        setContentPane(jp); // 应用内容面板

        JScrollPane jsp = new JScrollPane();// 滚动面板
        jp.add(jsp, BorderLayout.CENTER); // 将面板增加到边界布局中央

        JList<String> jList = new JList<>();
        jList.setListData(new String[]{"a", "b", "c", "d"});
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选
        jp.add(jList);

        setVisible(true);
    }

    public static void main(String[] args) {
        JListDemo0 jf = new JListDemo0("单选列表");
        //jf.setVisible(true);
    }
}
