package test.swing.simple.event.foucusevent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/3/8 21:14
 * 焦点事件监听器
 */
public class FocusListenerDemo extends JFrame {

    private JLabel label;
    private JButton bt;
    private JTextField textField;

    public FocusListenerDemo(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(500, 500, 400, 400);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        label = new JLabel("label");
        label.setFont(new Font("楷体", Font.BOLD, 23));
        contentPane.add(label, BorderLayout.SOUTH);

        textField = new JTextField("文本框");
        textField.setFont(new Font("黑体", Font.BOLD, 23));
        // java.awt.event.FocusAdapter
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                label.setText("获得焦点");
                label.setForeground(Color.GREEN);
            }

            @Override
            public void focusLost(FocusEvent e) {
                label.setText("失焦");
                label.setForeground(Color.RED);
            }
        });

        contentPane.add(textField);

        setVisible(true);
    }

    public static void main(String[] args) {
        new FocusListenerDemo("焦点事件监听器");
    }
}
