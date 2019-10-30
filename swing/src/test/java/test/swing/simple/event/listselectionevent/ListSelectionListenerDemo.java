package test.swing.simple.event.listselectionevent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import java.util.Vector;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 21:33
 */
public class ListSelectionListenerDemo extends JFrame {

    public ListSelectionListenerDemo(String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 500, 500);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JLabel label = new JLabel("label");
        contentPane.add(label, BorderLayout.SOUTH);

        JScrollPane jsp = new JScrollPane();
        contentPane.add(jsp, BorderLayout.CENTER);

        JList<String> list = new JList<>();
        jsp.setViewportView(list);
        list.setListData(new Vector<>(Arrays.asList("java", "php", "c", "c++", "python")));
        //list.setVisibleRowCount(2);
        list.addListSelectionListener(e -> {
            @SuppressWarnings("unchecked")
            JList<String> source = (JList<String>) e.getSource();
            label.setText(source.getSelectedValue());
            label.setForeground(Color.GREEN);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ListSelectionListenerDemo("列表选择监听");
    }

}
